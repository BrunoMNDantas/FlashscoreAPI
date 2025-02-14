package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.brunomndantas.flashscore.api.dataAccess.FlashscoreConstants.*;

public class MatchScrapperRepository extends ScrapperRepository<MatchKey, Match> {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");


    public MatchScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected String getUrlOfEntity(MatchKey matchKey) {
        return MATCH_URL.replace(MATCH_ID_PLACEHOLDER, matchKey.getMatchId());
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

        return match;
    }

    protected String scrapHomeTeamKey(WebDriver driver) {
        WebElement element = driver.findElement(MATCH_HOME_TEAM_SELECTOR);
        return getTeamKeyOfElement(element);
    }

    protected String scrapAwayTeamKey(WebDriver driver) {
        WebElement element = driver.findElement(MATCH_AWAY_TEAM_SELECTOR);
        return getTeamKeyOfElement(element);
    }

    protected String getTeamKeyOfElement(WebElement element) {
        String href = element.getAttribute("href");
        href = StringUtils.splitByWholeSeparatorPreserveAllTokens(href, "team", 2)[1];
        href = href.split("/")[1] + "/" + href.split("/")[2];
        return href;
    }

    protected int scrapHomeTeamGoals(WebDriver driver ) {
        return getGoalsOfElement(driver, MATCH_HOME_TEAM_GOALS_SELECTOR);
    }

    protected int scrapAwayTeamGoals(WebDriver driver ) {
        return getGoalsOfElement(driver, MATCH_AWAY_TEAM_GOALS_SELECTOR);
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
        WebElement element = driver.findElement(MATCH_DATE_SELECTOR);
        String text = element.getText();
        try {
            return DATE_FORMAT.parse(text);
        } catch (ParseException e) {
            throw new RepositoryException("Error parsing date", e);
        }
    }

}