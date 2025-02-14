package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.transversal.Config;
import com.github.brunomndantas.flashscore.api.transversal.Utils;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
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
        season.setName(scrapName(seasonKey));
        season.setMatchesKeys(scrapMatchesKeys(driver, seasonKey));

        return season;
    }

    protected String scrapName(SeasonKey seasonKey) {
        return seasonKey.getSeasonId();
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

        loadAllMatches(driver);

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

        loadAllMatches(driver);

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

    protected void loadAllMatches(WebDriver driver) {
        Collection<WebElement> loadMoreElements;
        WebElement loadMoreElement;

        while(true) {
            loadMoreElements = driver.findElements(LOAD_MORE_MATCHES_LABEL_SELECTOR);

            if(loadMoreElements.isEmpty()) {
                break;
            }

            loadMoreElement = loadMoreElements.stream().findFirst().get();
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", loadMoreElement);
            loadMoreElement.click();

            Utils.sleep(Config.QUICK_WAIT);
        }
    }

}