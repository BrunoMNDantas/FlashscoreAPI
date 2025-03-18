package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.season;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.ScrapperRepository;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.openqa.selenium.WebDriver;

import java.util.Collection;
import java.util.LinkedList;

public class SeasonScrapperRepository extends ScrapperRepository<SeasonKey, Season, SeasonPage> {

    public SeasonScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected SeasonPage getPage(WebDriver driver, SeasonKey key) {
        return new SeasonPage(driver, key);
    }

    @Override
    protected Season scrapEntity(WebDriver driver, SeasonPage page, SeasonKey seasonKey) {
        Season season = new Season();

        season.setKey(seasonKey);
        season.setInitYear(page.getInitYear());
        season.setEndYear(page.getEndYear());
        season.setMatchesKeys(scrapMatchesKeys(driver, seasonKey));

        return season;
    }

    protected Collection<MatchKey> scrapMatchesKeys(WebDriver driver, SeasonKey seasonKey) {
        PastMatchesPage pastMatchesPage = new PastMatchesPage(driver, seasonKey);
        TodayMatchesPage todayMatchesPage = new TodayMatchesPage(driver, seasonKey);
        FutureMatchesPage futureMatchesPage = new FutureMatchesPage(driver, seasonKey);

        Collection<MatchKey> matchesKeys = new LinkedList<>();

        pastMatchesPage.load();
        matchesKeys.addAll(pastMatchesPage.getMatchesKeys());

        todayMatchesPage.load();
        todayMatchesPage
                .getMatchesKeys()
                .stream()
                .filter(key -> !matchesKeys.contains(key))
                .forEach(matchesKeys::add);

        futureMatchesPage.load();
        futureMatchesPage
                .getMatchesKeys()
                .stream()
                .filter(key -> !matchesKeys.contains(key))
                .forEach(matchesKeys::add);

        return matchesKeys;
    }

}