package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonId;
import com.github.brunomndantas.flashscore.api.transversal.Utils;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.LinkedList;

import static com.github.brunomndantas.flashscore.api.dataAccess.FlashscoreConstants.*;

public class SeasonScrapperRepository extends ScrapperRepository<SeasonId, Season> {

    public SeasonScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected String getUrlOfEntity(SeasonId seasonId) {
        return buildSeasonUrl(SEASON_URL, seasonId);
    }

    @Override
    protected Season scrapEntity(WebDriver driver, SeasonId seasonId) {
        Season season = new Season();

        season.setId(seasonId);
        season.setName(scrapName(seasonId));
        season.setMatchesIds(scrapMatchesIds(driver, seasonId));

        return season;
    }

    protected String scrapName(SeasonId seasonId) {
        return seasonId.getId();
    }

    protected Collection<String> scrapMatchesIds(WebDriver driver, SeasonId seasonId) {
        Collection<String> todayMatchesIds = scrapTodayMatchesIds(driver, seasonId);
        Collection<String> pastMatchesIds = scrapPastMatchesIds(driver, seasonId);
        Collection<String> futureMatchesIds = scrapFutureMatchesIds(driver, seasonId);

        Collection<String> matchesIds = new LinkedList<>(pastMatchesIds);

        todayMatchesIds
                .stream()
                .filter(id -> !matchesIds.contains(id))
                .forEach(matchesIds::add);

        futureMatchesIds
                .stream()
                .filter(id -> !matchesIds.contains(id))
                .forEach(matchesIds::add);

        return matchesIds;
    }

    protected Collection<String> scrapPastMatchesIds(WebDriver driver, SeasonId seasonId) {
        String url = buildSeasonUrl(PAST_MATCHES_URL, seasonId);
        driver.get(url);
        super.waitPageToBeLoaded(driver);

        loadAllMatches(driver);

        Collection<WebElement> matchesLinks = driver.findElements(PAST_MATCHES_SELECTOR);
        return matchesLinks
                .stream()
                .map(this::getMatchIdOfElement)
                .toList();
    }

    protected Collection<String> scrapTodayMatchesIds(WebDriver driver, SeasonId seasonId) {
        String url = buildSeasonUrl(TODAY_MATCHES_URL, seasonId);
        driver.get(url);
        super.waitPageToBeLoaded(driver);

        Collection<WebElement> matchesLinks = driver.findElements(TODAY_MATCHES_SELECTOR);
        return matchesLinks
                .stream()
                .map(this::getMatchIdOfElement)
                .toList();
    }

    protected Collection<String> scrapFutureMatchesIds(WebDriver driver, SeasonId seasonId) {
        String url = buildSeasonUrl(FUTURE_MATCHES_URL, seasonId);
        driver.get(url);
        super.waitPageToBeLoaded(driver);

        loadAllMatches(driver);

        Collection<WebElement> matchesLinks = driver.findElements(FUTURE_MATCHES_SELECTOR);
        return matchesLinks
                .stream()
                .map(this::getMatchIdOfElement)
                .toList();
    }

    protected String getMatchIdOfElement(WebElement element) {
        String href = element.getAttribute("href");
        href = href.split("match")[1];
        href = href.split("/")[1];
        return href;
    }

    protected String buildSeasonUrl(String url, SeasonId seasonId) {
        return url
                .replace(SPORT_ID_PLACEHOLDER, seasonId.getSportId())
                .replace(REGION_ID_PLACEHOLDER, seasonId.getRegionId())
                .replace(COMPETITION_ID_PLACEHOLDER, seasonId.getCompetitionId())
                .replace(SEASON_ID_PLACEHOLDER, seasonId.getId());
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

            Utils.sleep(quickWait);
        }
    }

}