package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.dataAccess.utils.FlashscoreSelectors;
import com.github.brunomndantas.flashscore.api.dataAccess.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MatchScrapperRepository extends ScrapperRepository<MatchKey, Match> {

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

}