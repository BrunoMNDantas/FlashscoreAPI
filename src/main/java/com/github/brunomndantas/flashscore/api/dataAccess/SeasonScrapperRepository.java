package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.LinkedList;

import static com.github.brunomndantas.flashscore.api.dataAccess.FlashscoreConstants.*;

public class SeasonScrapperRepository extends ScrapperRepository<SeasonKey, Season> {

    public SeasonScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected String getUrlOfEntity(SeasonKey seasonKey) {
        return buildSeasonUrl(SEASON_URL, seasonKey);
    }

    @Override
    protected Season scrapEntity(WebDriver driver, SeasonKey seasonKey) {
        Season season = new Season();

        season.setKey(seasonKey);
        season.setInitYear(scrapInitYear(driver));
        season.setEndYear(scrapEndYear(driver));
        season.setMatchesKeys(scrapMatchesKeys(driver, seasonKey));

        return season;
    }

    protected int scrapInitYear(WebDriver driver) {
        WebElement element = driver.findElement(SEASONS_YEARS_SELECTOR);
        String text = element.getText();
        String year = text.contains("/") ? text.split("/")[0] : text;
        return Integer.parseInt(year);
    }

    protected int scrapEndYear(WebDriver driver) {
        WebElement element = driver.findElement(SEASONS_YEARS_SELECTOR);
        String text = element.getText();
        String year = text.contains("/") ? text.split("/")[1] : text;
        return Integer.parseInt(year);
    }

    protected Collection<MatchKey> scrapMatchesKeys(WebDriver driver, SeasonKey seasonKey) {
        Collection<MatchKey> todayMatchesKeys = scrapTodayMatchesKeys(driver, seasonKey);
        Collection<MatchKey> pastMatchesKeys = scrapPastMatchesKeys(driver, seasonKey);
        Collection<MatchKey> futureMatchesKeys = scrapFutureMatchesKeys(driver, seasonKey);

        Collection<MatchKey> matchesKeys = new LinkedList<>(pastMatchesKeys);

        todayMatchesKeys
                .stream()
                .filter(key -> !matchesKeys.contains(key))
                .forEach(matchesKeys::add);

        futureMatchesKeys
                .stream()
                .filter(key -> !matchesKeys.contains(key))
                .forEach(matchesKeys::add);

        return matchesKeys;
    }

    protected Collection<MatchKey> scrapPastMatchesKeys(WebDriver driver, SeasonKey seasonKey) {
        String url = buildSeasonUrl(PAST_MATCHES_URL, seasonKey);
        driver.get(url);
        super.waitPageToBeLoaded(driver);

        FlashscoreUtils.loadAllMatches(driver);

        Collection<WebElement> matchesLinks = driver.findElements(PAST_MATCHES_SELECTOR);
        return matchesLinks
                .stream()
                .map(this::getMatchKeyOfElement)
                .toList();
    }

    protected Collection<MatchKey> scrapTodayMatchesKeys(WebDriver driver, SeasonKey seasonKey) {
        String url = buildSeasonUrl(TODAY_MATCHES_URL, seasonKey);
        driver.get(url);
        super.waitPageToBeLoaded(driver);

        Collection<WebElement> matchesLinks = driver.findElements(TODAY_MATCHES_SELECTOR);
        return matchesLinks
                .stream()
                .map(this::getMatchKeyOfElement)
                .toList();
    }

    protected Collection<MatchKey> scrapFutureMatchesKeys(WebDriver driver, SeasonKey seasonKey) {
        String url = buildSeasonUrl(FUTURE_MATCHES_URL, seasonKey);
        driver.get(url);
        super.waitPageToBeLoaded(driver);

        FlashscoreUtils.loadAllMatches(driver);

        Collection<WebElement> matchesLinks = driver.findElements(FUTURE_MATCHES_SELECTOR);
        return matchesLinks
                .stream()
                .map(this::getMatchKeyOfElement)
                .toList();
    }

    protected MatchKey getMatchKeyOfElement(WebElement element) {
        String href = element.getAttribute("href");
        href = StringUtils.splitByWholeSeparatorPreserveAllTokens(href, "match", 2)[1];
        href = href.split("/")[1];
        return new MatchKey(href);
    }

    protected String buildSeasonUrl(String url, SeasonKey seasonKey) {
        return url
                .replace(SPORT_ID_PLACEHOLDER, seasonKey.getSportId())
                .replace(REGION_ID_PLACEHOLDER, seasonKey.getRegionId())
                .replace(COMPETITION_ID_PLACEHOLDER, seasonKey.getCompetitionId())
                .replace(SEASON_ID_PLACEHOLDER, seasonKey.getSeasonId());
    }

}