package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreSelectors;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.*;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.transversal.Config;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class MatchScrapperRepository extends ScrapperRepository<MatchKey, Match> {

    public static final String FIRST_HALF_LABEL = "1st Half";
    public static final String SECOND_HALF_LABEL = "2nd Half";
    public static final String EXTRA_TIME_LABEL = "Extra Time";
    public static final String PENALTIES_LABEL = "Penalties";

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");


    public MatchScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected String getUrlOfEntity(MatchKey matchKey) {
        return FlashscoreURLs.getMatchURL(matchKey);
    }

    @Override
    protected Match scrapEntity(WebDriver driver, MatchKey matchKey) throws RepositoryException {
        Match match = new Match();

        match.setKey(matchKey);
        match.setHomeTeamKey(scrapHomeTeamKey(driver));
        match.setAwayTeamKey(scrapAwayTeamKey(driver));
        match.setHomeTeamGoals(scrapHomeTeamGoals(driver));
        match.setAwayTeamGoals(scrapAwayTeamGoals(driver));
        match.setDate(scrapDate(driver));
        match.setEvents(scrapEvents(driver));

        loadPlayersURL(matchKey, driver);

        match.setHomeCoachPlayerKey(scrapHomeCoachPlayerKey(driver));
        match.setAwayCoachPlayerKey(scrapAwayCoachPlayerKey(driver));
        match.setHomeLineupPlayersKeys(scrapHomeLineupPlayersKeys(driver));
        match.setAwayLineupPlayersKeys(scrapAwayLineupPlayersKeys(driver));
        match.setHomeBenchPlayersKeys(scrapHomeBenchPlayersKeys(driver));
        match.setAwayBenchPlayersKeys(scrapAwayBenchPlayersKeys(driver));

        return match;
    }

    protected TeamKey scrapHomeTeamKey(WebDriver driver) {
        WebElement element = driver.findElement(FlashscoreSelectors.MATCH_HOME_TEAM_SELECTOR);
        return FlashscoreURLs.getTeamKey(element.getAttribute("href"));
    }

    protected TeamKey scrapAwayTeamKey(WebDriver driver) {
        WebElement element = driver.findElement(FlashscoreSelectors.MATCH_AWAY_TEAM_SELECTOR);
        return FlashscoreURLs.getTeamKey(element.getAttribute("href"));
    }

    protected int scrapHomeTeamGoals(WebDriver driver ) {
        return getGoalsOfElement(driver, FlashscoreSelectors.MATCH_HOME_TEAM_GOALS_SELECTOR);
    }

    protected int scrapAwayTeamGoals(WebDriver driver ) {
        return getGoalsOfElement(driver, FlashscoreSelectors.MATCH_AWAY_TEAM_GOALS_SELECTOR);
    }

    private int getGoalsOfElement(WebDriver driver, By selector) {
        if(!driver.findElements(selector).isEmpty()) {
            WebElement element = driver.findElement(selector);
            String text = element.getText();
            return Integer.parseInt(text);
        } else {
            return -1;
        }
    }

    protected Date scrapDate(WebDriver driver) throws RepositoryException {
        WebElement element = driver.findElement(FlashscoreSelectors.MATCH_DATE_SELECTOR);
        String text = element.getText();
        try {
            return DATE_FORMAT.parse(text);
        } catch (ParseException e) {
            throw new RepositoryException("Error parsing date", e);
        }
    }

    protected Collection<Event> scrapEvents(WebDriver driver) {
        Collection<Event> events = new LinkedList<>();

        events.addAll(scrapEvents(driver, FIRST_HALF_LABEL, Time.Period.FIRST_HALF));
        events.addAll(scrapEvents(driver, SECOND_HALF_LABEL, Time.Period.SECOND_HALF));
        events.addAll(scrapEvents(driver, EXTRA_TIME_LABEL, Time.Period.EXTRA_TIME));
        events.addAll(scrapEvents(driver, PENALTIES_LABEL, Time.Period.PENALTIES));

        return events;
    }

    protected Collection<WebElement> getEventsElements(WebDriver driver, String part) {
        Collection<WebElement> elements = driver.findElements(FlashscoreSelectors.MATCH_EVENTS_SELECTOR);
        Collection<WebElement> events = new LinkedList<>();

        boolean partFound = false;
        for(WebElement element : elements) {
            if(!element.getAttribute("class").contains(FlashscoreSelectors.MATCH_EVENT_CLASS)) {
                //Is Divider
                if(partFound) {
                    break;
                } else if(element.getText().toLowerCase().contains(part.toLowerCase())) {
                    partFound = true;
                }
            } else if(partFound) {
                events.add(element);
            }
        }

        return events;
    }

    protected Collection<Event> scrapEvents(WebDriver driver, String partLabel, Time.Period period) {
        return getEventsElements(driver, partLabel)
                .stream()
                .map(element -> scrapEvent(driver, element, period))
                .toList();
    }

    protected Event scrapEvent(WebDriver driver, WebElement element, Time.Period period) {
        if(!element.findElements(FlashscoreSelectors.MATCH_EVENT_PENALTY_SELECTOR).isEmpty()) {
            return scrapPenalty(driver, element, period);
        } else if(!element.findElements(FlashscoreSelectors.MATCH_EVENT_CARD_SELECTOR).isEmpty()) {
            return scrapCard(driver, element, period);
        } else if(!element.findElements(FlashscoreSelectors.MATCH_EVENT_SUBSTITUTION_SELECTOR).isEmpty()) {
            return scrapSubstitution(driver, element, period);
        } else if(!element.findElements(FlashscoreSelectors.MATCH_EVENT_GOAL_SELECTOR).isEmpty()) {
            return scrapGoal(driver, element, period);
        }

        return null;
    }

    protected Goal scrapGoal(WebDriver driver, WebElement element, Time.Period period) {
        Goal event = new Goal();

        event.setTime(new Time(period, scrapEventMinute(element), scrapEventExtraMinute(element)));
        event.setTeamKey(scrapEventTeamKey(driver, element));
        event.setPlayerKey(scrapGoalPlayerKey(element));
        event.setAssistPlayerKey(scrapGoalAssistPlayerKey(element));

        return event;
    }

    protected int scrapEventMinute(WebElement element) {
        WebElement minuteElement = element.findElement(FlashscoreSelectors.MATCH_EVENT_MINUTE_SELECTOR);
        String text = minuteElement.getText();
        text = text.replace("'", "").trim();
        text = text.contains("+") ? text.split("\\+")[0] : text;
        return Integer.parseInt(text);
    }

    protected int scrapEventExtraMinute(WebElement element) {
        WebElement minuteElement = element.findElement(FlashscoreSelectors.MATCH_EVENT_MINUTE_SELECTOR);
        String text = minuteElement.getText();
        text = text.replace("'", "").trim();
        text = text.contains("+") ? text.split("\\+")[1] : "0";
        return Integer.parseInt(text);
    }

    protected TeamKey scrapEventTeamKey(WebDriver driver, WebElement element) {
        String classAttr = element.getAttribute("class");

        if(classAttr.contains(FlashscoreSelectors.MATCH_EVENT_HOME_CLASS)) {
            return scrapHomeTeamKey(driver);
        }else if(classAttr.contains(FlashscoreSelectors.MATCH_EVENT_AWAY_CLASS)) {
            return scrapAwayTeamKey(driver);
        }

        return null;
    }

    protected PlayerKey scrapGoalPlayerKey(WebElement element) {
        WebElement playerElement = element.findElement(FlashscoreSelectors.MATCH_EVENT_GOAL_PLAYER_SELECTOR);
        return FlashscoreURLs.getPlayerKey(playerElement.getAttribute("href"));
    }

    protected PlayerKey scrapGoalAssistPlayerKey(WebElement element) {
        if(!element.findElements(FlashscoreSelectors.MATCH_EVENT_GOAL_ASSIST_SELECTOR).isEmpty()) {
            WebElement playerElement = element.findElement(FlashscoreSelectors.MATCH_EVENT_GOAL_ASSIST_SELECTOR);
            return FlashscoreURLs.getPlayerKey(playerElement.getAttribute("href"));
        }

        return null;
    }

    protected Card scrapCard(WebDriver driver, WebElement element, Time.Period period) {
        Card event = new Card();

        event.setTime(new Time(period, scrapEventMinute(element), scrapEventExtraMinute(element)));
        event.setTeamKey(scrapEventTeamKey(driver, element));
        event.setColor(scrapCardColor(element));
        event.setPlayerKey(scrapCardPlayerKey(element));

        return event;
    }

    protected Card.Color scrapCardColor(WebElement element) {
        WebElement cardElement = element.findElement(FlashscoreSelectors.MATCH_EVENT_CARD_SELECTOR);
        String classAttr = cardElement.getAttribute("class");

        if(classAttr.contains(FlashscoreSelectors.MATCH_EVENT_RED_CARD_CLASS)) {
            return Card.Color.RED;
        } else if(classAttr.contains(FlashscoreSelectors.MATCH_EVENT_YELLOW_CARD_CLASS)) {
            return Card.Color.YELLOW;
        } else {
            //SECOND YELLOW
            return Card.Color.YELLOW;
        }
    }

    protected PlayerKey scrapCardPlayerKey(WebElement element) {
        WebElement playerElement = element.findElement(FlashscoreSelectors.MATCH_EVENT_CARD_PLAYER_SELECTOR);
        return FlashscoreURLs.getPlayerKey(playerElement.getAttribute("href"));
    }

    protected Substitution scrapSubstitution(WebDriver driver, WebElement element, Time.Period period) {
        Substitution event = new Substitution();

        event.setTime(new Time(period, scrapEventMinute(element), scrapEventExtraMinute(element)));
        event.setTeamKey(scrapEventTeamKey(driver, element));
        event.setInPlayerKey(scrapSubstitutionInPlayerKey(element));
        event.setOutPlayerKey(scrapSubstitutionOutPlayerKey(element));

        return event;
    }

    protected PlayerKey scrapSubstitutionInPlayerKey(WebElement element) {
        WebElement playerElement = element.findElement(FlashscoreSelectors.MATCH_EVENT_SUBSTITUTION_IN_PLAYER_SELECTOR);
        return FlashscoreURLs.getPlayerKey(playerElement.getAttribute("href"));
    }

    protected PlayerKey scrapSubstitutionOutPlayerKey(WebElement element) {
        WebElement playerElement = element.findElement(FlashscoreSelectors.MATCH_EVENT_SUBSTITUTION_OUT_PLAYER_SELECTOR);
        return FlashscoreURLs.getPlayerKey(playerElement.getAttribute("href"));
    }

    protected Penalty scrapPenalty(WebDriver driver, WebElement element, Time.Period period) {
        Penalty event = new Penalty();

        event.setTime(new Time(period, scrapEventMinute(element), scrapEventExtraMinute(element)));
        event.setTeamKey(scrapEventTeamKey(driver, element));
        event.setMissed(scrapPenaltyMissed(element));
        event.setPlayerKey(scrapPenaltyPlayerKey(element));

        return event;
    }

    protected boolean scrapPenaltyMissed(WebElement element) {
        String text = element.getText();
        return text.contains("Penalty missed");
    }

    protected PlayerKey scrapPenaltyPlayerKey(WebElement element) {
        WebElement playerElement = element.findElement(FlashscoreSelectors.MATCH_EVENT_PENALTY_PLAYER_SELECTOR);
        return FlashscoreURLs.getPlayerKey(playerElement.getAttribute("href"));
    }

    private void loadPlayersURL(MatchKey matchKey, WebDriver driver) {
        if(!driver.findElements(FlashscoreSelectors.MATCH_LINEUP_BUTTON_SELECTOR).isEmpty()) {
            String url = FlashscoreURLs.getMatchPlayersURL(matchKey);
            driver.get(url);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(Config.MEDIUM_WAIT).getSeconds());
            ExpectedCondition<WebElement> condition = ExpectedConditions.presenceOfElementLocated(FlashscoreSelectors.MATCH_HOME_LINEUP_SELECTOR);
            wait.until(condition);
        } else {
            super.waitPageToBeLoaded(driver);
        }
    }

    protected PlayerKey scrapHomeCoachPlayerKey(WebDriver driver) {
        if(!driver.findElements(FlashscoreSelectors.MATCH_HOME_COACH_SELECTOR).isEmpty()) {
            WebElement element = driver.findElement(FlashscoreSelectors.MATCH_HOME_COACH_SELECTOR);
            return FlashscoreURLs.getPlayerKey(element.getAttribute("href"));
        }

        return null;
    }

    protected PlayerKey scrapAwayCoachPlayerKey(WebDriver driver) {
        if(!driver.findElements(FlashscoreSelectors.MATCH_AWAY_COACH_SELECTOR).isEmpty()) {
            WebElement element = driver.findElement(FlashscoreSelectors.MATCH_AWAY_COACH_SELECTOR);
            return FlashscoreURLs.getPlayerKey(element.getAttribute("href"));
        }

        return null;
    }

    protected Collection<PlayerKey> scrapHomeLineupPlayersKeys(WebDriver driver) {
        Collection<WebElement> elements = driver.findElements(FlashscoreSelectors.MATCH_HOME_LINEUP_SELECTOR);
        return elements
                .stream()
                .map(element -> element.getAttribute("href"))
                .map(FlashscoreURLs::getPlayerKey)
                .collect(Collectors.toSet());
    }

    protected Collection<PlayerKey> scrapAwayLineupPlayersKeys(WebDriver driver) {
        Collection<WebElement> elements = driver.findElements(FlashscoreSelectors.MATCH_AWAY_LINEUP_SELECTOR);
        return elements
                .stream()
                .map(element -> element.getAttribute("href"))
                .map(FlashscoreURLs::getPlayerKey)
                .collect(Collectors.toSet());
    }

    protected Collection<PlayerKey> scrapHomeBenchPlayersKeys(WebDriver driver) {
        Collection<WebElement> elements = driver.findElements(FlashscoreSelectors.MATCH_HOME_BENCH_SELECTOR);
        return elements
                .stream()
                .map(element -> element.getAttribute("href"))
                .map(FlashscoreURLs::getPlayerKey)
                .collect(Collectors.toSet());
    }

    protected Collection<PlayerKey> scrapAwayBenchPlayersKeys(WebDriver driver) {
        Collection<WebElement> elements = driver.findElements(FlashscoreSelectors.MATCH_AWAY_BENCH_SELECTOR);
        return elements
                .stream()
                .map(element -> element.getAttribute("href"))
                .map(FlashscoreURLs::getPlayerKey)
                .collect(Collectors.toSet());
    }

}