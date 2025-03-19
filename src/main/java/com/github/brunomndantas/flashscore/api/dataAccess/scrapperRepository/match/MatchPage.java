package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.match;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.FlashscorePage;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.*;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

public class MatchPage extends FlashscorePage {

    public static final By HOME_TEAM_SELECTOR = By.xpath("//*[contains(@class, 'duelParticipant__home')]//a[contains(@class, 'participant__participantName')]");
    public static final By AWAY_TEAM_SELECTOR = By.xpath("//*[contains(@class, 'duelParticipant__away')]//a[contains(@class, 'participant__participantName')]");
    public static final By HOME_TEAM_GOALS_SELECTOR = By.xpath("//*[contains(@class, 'detailScore__wrapper')]/span[not(contains(@class, 'divider'))][1]");
    public static final By AWAY_TEAM_GOALS_SELECTOR = By.xpath("//*[contains(@class, 'detailScore__wrapper')]/span[not(contains(@class, 'divider'))][2]");
    public static final By DATE_SELECTOR = By.xpath("//*[@class='duelParticipant__startTime']");
    public static final By EVENTS_SELECTOR = By.xpath("//*[contains(@class,'smv__verticalSections')]/div");
    public static final String EVENT_CLASS = "smv__participantRow";
    public static final String EVENT_HOME_CLASS = "smv__homeParticipant";
    public static final String EVENT_AWAY_CLASS = "smv__awayParticipant";
    public static final By EVENT_MINUTE_SELECTOR = By.xpath(".//*[@class='smv__timeBox']");
    public static final By EVENT_GOAL_SELECTOR = By.xpath(".//*[@data-testid='wcl-icon-soccer']");
    public static final By EVENT_CARD_SELECTOR = By.xpath(".//*[contains(@class,'card-ico')]");
    public static final By EVENT_SUBSTITUTION_SELECTOR = By.xpath(".//*[contains(@class,'substitution')]");
    public static final By EVENT_PENALTY_SELECTOR = By.xpath(".//*[contains(text(),'Penalty')]");
    public static final By EVENT_GOAL_PLAYER_SELECTOR = By.xpath(".//*[contains(@class,'smv__incident')]/a");
    public static final By EVENT_GOAL_ASSIST_SELECTOR = By.xpath(".//*[contains(@class,'smv__assist')]/a");
    public static final By EVENT_CARD_PLAYER_SELECTOR = By.xpath(".//*[@class='smv__playerName']");
    public static final String EVENT_YELLOW_CARD_CLASS = "yellowCard-ico";
    public static final String EVENT_RED_CARD_CLASS = "redCard-ico";
    public static final By EVENT_SUBSTITUTION_IN_PLAYER_SELECTOR = By.xpath(".//*[@class='smv__incident']/a");
    public static final By EVENT_SUBSTITUTION_OUT_PLAYER_SELECTOR = By.xpath(".//*[contains(@class, 'smv__subDown')]");
    public static final By EVENT_PENALTY_PLAYER_SELECTOR = By.xpath(".//*[contains(@class, 'smv__playerName')]");

    public static final String FIRST_HALF_LABEL = "1st Half";
    public static final String SECOND_HALF_LABEL = "2nd Half";
    public static final String EXTRA_TIME_LABEL = "Extra Time";
    public static final String PENALTIES_LABEL = "Penalties";

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");


    private MatchKey matchKey;


    public MatchPage(WebDriver driver, MatchKey matchKey) {
        super(driver, FlashscoreURLs.getMatchURL(matchKey));
        this.matchKey = matchKey;
    }


    public TeamKey getHomeTeamKey() {
        return getValue(HOME_TEAM_SELECTOR, null, element -> FlashscoreURLs.getTeamKey(element.getAttribute("href")));
    }

    public TeamKey getAwayTeamKey() {
        return getValue(AWAY_TEAM_SELECTOR, null, element -> FlashscoreURLs.getTeamKey(element.getAttribute("href")));
    }

    public int getHomeTeamGoals() {
        return getValue(HOME_TEAM_GOALS_SELECTOR, -1, element -> {
            String text = element.getText();
            return Integer.parseInt(text);
        });
    }

    public int getAwayTeamGoals() {
        return getValue(AWAY_TEAM_GOALS_SELECTOR, -1, element -> {
            String text = element.getText();
            return Integer.parseInt(text);
        });
    }

    public Date getDate() {
        return getValue(DATE_SELECTOR, null, element -> {
            String text = element.getText();
            try {
                return DATE_FORMAT.parse(text);
            } catch (ParseException e) {
                throw new RuntimeException("Error parsing date", e);
            }
        });
    }

    public Collection<WebElement> getFirstHalfEventsElements() {
        return getEventsElements(FIRST_HALF_LABEL);
    }

    public Collection<Event> getFirstHalfEvents() {
        return getEvents(getFirstHalfEventsElements(), Time.Period.FIRST_HALF);
    }

    public Collection<WebElement> getSecondHalfEventsElements() {
        return getEventsElements(SECOND_HALF_LABEL);
    }

    public Collection<Event> getSecondHalfEvents() {
        return getEvents(getSecondHalfEventsElements(), Time.Period.SECOND_HALF);
    }

    public Collection<WebElement> getExtraTimeEventsElements() {
        return getEventsElements(EXTRA_TIME_LABEL);
    }

    public Collection<Event> getExtraTimeEvents() {
        return getEvents(getExtraTimeEventsElements(), Time.Period.EXTRA_TIME);
    }

    public Collection<WebElement> getPenaltiesEventsElements() {
        return getEventsElements(PENALTIES_LABEL);
    }

    public Collection<Event> getPenaltiesEvents() {
        return getEvents(getPenaltiesEventsElements(), Time.Period.PENALTIES);
    }

    protected Collection<WebElement> getEventsElements(String part) {
        Collection<WebElement> elements = driver.findElements(EVENTS_SELECTOR);
        Collection<WebElement> events = new LinkedList<>();

        boolean partFound = false;
        for(WebElement element : elements) {
            if(!element.getAttribute("class").contains(EVENT_CLASS)) {
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

    protected Collection<Event> getEvents(Collection<WebElement> elements, Time.Period period) {
        return elements
                .stream()
                .map(element -> getEvent(element, period))
                .toList();
    }

    protected Event getEvent(WebElement element, Time.Period period) {
        if(!element.findElements(EVENT_PENALTY_SELECTOR).isEmpty()) {
            return getPenalty(element, period);
        } else if(!element.findElements(EVENT_CARD_SELECTOR).isEmpty()) {
            return getCard(element, period);
        } else if(!element.findElements(EVENT_SUBSTITUTION_SELECTOR).isEmpty()) {
            return getSubstitution(element, period);
        } else if(!element.findElements(EVENT_GOAL_SELECTOR).isEmpty()) {
            return getGoal(element, period);
        }

        return null;
    }

    protected Goal getGoal(WebElement element, Time.Period period) {
        Goal event = new Goal();

        event.setTime(new Time(period, getEventMinute(element), getEventExtraMinute(element)));
        event.setTeamKey(getEventTeamKey(element));
        event.setPlayerKey(getGoalPlayerKey(element));
        event.setAssistPlayerKey(getGoalAssistPlayerKey(element));

        return event;
    }

    protected int getEventMinute(WebElement element) {
        return getValue(element, EVENT_MINUTE_SELECTOR, -1, minuteElement -> {
            String text = minuteElement.getText();
            text = text.replace("'", "").trim();
            text = text.contains("+") ? text.split("\\+")[0] : text;
            return Integer.parseInt(text);
        });
    }

    protected int getEventExtraMinute(WebElement element) {
        return getValue(element, EVENT_MINUTE_SELECTOR, -1, minuteElement -> {
            String text = minuteElement.getText();
            text = text.replace("'", "").trim();
            text = text.contains("+") ? text.split("\\+")[1] : "0";
            return Integer.parseInt(text);
        });
    }

    protected TeamKey getEventTeamKey(WebElement element) {
        String classAttr = element.getAttribute("class");

        if(classAttr.contains(EVENT_HOME_CLASS)) {
            return getHomeTeamKey();
        }else if(classAttr.contains(EVENT_AWAY_CLASS)) {
            return getAwayTeamKey();
        }

        return null;
    }

    protected PlayerKey getGoalPlayerKey(WebElement element) {
        return getValue(element, EVENT_GOAL_PLAYER_SELECTOR, null, playerElement -> {
            String href = playerElement.getAttribute("href");
            return FlashscoreURLs.getPlayerKey(href);
        });
    }

    protected PlayerKey getGoalAssistPlayerKey(WebElement element) {
        return getValue(element, EVENT_GOAL_ASSIST_SELECTOR, null, playerElement -> {
            String href = playerElement.getAttribute("href");
            return FlashscoreURLs.getPlayerKey(href);
        });
    }

    protected Card getCard(WebElement element, Time.Period period) {
        Card event = new Card();

        event.setTime(new Time(period, getEventMinute(element), getEventExtraMinute(element)));
        event.setTeamKey(getEventTeamKey(element));
        event.setColor(getCardColor(element));
        event.setPlayerKey(getCardPlayerKey(element));

        return event;
    }

    protected Card.Color getCardColor(WebElement element) {
        return getValue(element, EVENT_CARD_SELECTOR, null, cardElement -> {
            String classAttr = cardElement.getAttribute("class");

            if(classAttr.contains(EVENT_RED_CARD_CLASS)) {
                return Card.Color.RED;
            } else if(classAttr.contains(EVENT_YELLOW_CARD_CLASS)) {
                return Card.Color.YELLOW;
            } else {
                //SECOND YELLOW
                return Card.Color.YELLOW;
            }
        });
    }

    protected PlayerKey getCardPlayerKey(WebElement element) {
        return getValue(element, EVENT_CARD_PLAYER_SELECTOR, null, playerElement -> {
            String href = playerElement.getAttribute("href");
            return FlashscoreURLs.getPlayerKey(href);
        });
    }

    protected Substitution getSubstitution(WebElement element, Time.Period period) {
        Substitution event = new Substitution();

        event.setTime(new Time(period, getEventMinute(element), getEventExtraMinute(element)));
        event.setTeamKey(getEventTeamKey(element));
        event.setInPlayerKey(getSubstitutionInPlayerKey(element));
        event.setOutPlayerKey(getSubstitutionOutPlayerKey(element));

        return event;
    }

    protected PlayerKey getSubstitutionInPlayerKey(WebElement element) {
        return getValue(element, EVENT_SUBSTITUTION_IN_PLAYER_SELECTOR, null, playerEvent -> {
            String href = playerEvent.getAttribute("href");
            return FlashscoreURLs.getPlayerKey(href);
        });
    }

    protected PlayerKey getSubstitutionOutPlayerKey(WebElement element) {
        return getValue(element, EVENT_SUBSTITUTION_OUT_PLAYER_SELECTOR, null, playerEvent -> {
            String href = playerEvent.getAttribute("href");
            return FlashscoreURLs.getPlayerKey(href);
        });
    }

    protected Penalty getPenalty(WebElement element, Time.Period period) {
        Penalty event = new Penalty();

        event.setTime(new Time(period, getEventMinute(element), getEventExtraMinute(element)));
        event.setTeamKey(getEventTeamKey(element));
        event.setMissed(getPenaltyMissed(element));
        event.setPlayerKey(getPenaltyPlayerKey(element));

        return event;
    }

    protected boolean getPenaltyMissed(WebElement element) {
        String text = element.getText();
        return text.contains("Penalty missed");
    }

    protected PlayerKey getPenaltyPlayerKey(WebElement element) {
        return getValue(element, EVENT_PENALTY_PLAYER_SELECTOR, null, playerElement -> {
            String href = playerElement.getAttribute("href");
            return FlashscoreURLs.getPlayerKey(href);
        });
    }

}