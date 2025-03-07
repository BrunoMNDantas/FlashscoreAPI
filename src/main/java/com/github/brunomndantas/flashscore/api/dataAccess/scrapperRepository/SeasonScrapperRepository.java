package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreSelectors;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreUtils;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.LinkedList;

public class SeasonScrapperRepository extends ScrapperRepository<SeasonKey, Season> {

    public SeasonScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected String getUrlOfEntity(SeasonKey seasonKey) {
        return FlashscoreURLs.getSeasonURL(seasonKey);
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
        WebElement element = driver.findElement(FlashscoreSelectors.SEASONS_YEARS_SELECTOR);
        String text = element.getText();
        String year = text.contains("/") ? text.split("/")[0] : text;
        return Integer.parseInt(year);
    }

    protected int scrapEndYear(WebDriver driver) {
        WebElement element = driver.findElement(FlashscoreSelectors.SEASONS_YEARS_SELECTOR);
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
        String url = FlashscoreURLs.getSeasonPastMatchesURL(seasonKey);
        driver.get(url);
        super.waitPageToBeLoaded(driver);

        FlashscoreUtils.loadAllMatches(driver);

        Collection<WebElement> matchesLinks = driver.findElements(FlashscoreSelectors.PAST_MATCHES_SELECTOR);
        return matchesLinks
                .stream()
                .map(element -> element.getAttribute("href"))
                .map(FlashscoreURLs::getMatchKey)
                .toList();
    }

    protected Collection<MatchKey> scrapTodayMatchesKeys(WebDriver driver, SeasonKey seasonKey) {
        String url = FlashscoreURLs.getSeasonTodayMatchesURL(seasonKey);
        driver.get(url);
        super.waitPageToBeLoaded(driver);

        Collection<WebElement> matchesLinks = driver.findElements(FlashscoreSelectors.TODAY_MATCHES_SELECTOR);
        return matchesLinks
                .stream()
                .map(element -> element.getAttribute("href"))
                .map(FlashscoreURLs::getMatchKey)
                .toList();
    }

    protected Collection<MatchKey> scrapFutureMatchesKeys(WebDriver driver, SeasonKey seasonKey) {
        String url = FlashscoreURLs.getSeasonFutureMatchesURL(seasonKey);
        driver.get(url);
        super.waitPageToBeLoaded(driver);

        FlashscoreUtils.loadAllMatches(driver);

        Collection<WebElement> matchesLinks = driver.findElements(FlashscoreSelectors.FUTURE_MATCHES_SELECTOR);
        return matchesLinks
                .stream()
                .map(element -> element.getAttribute("href"))
                .map(FlashscoreURLs::getMatchKey)
                .toList();
    }

}