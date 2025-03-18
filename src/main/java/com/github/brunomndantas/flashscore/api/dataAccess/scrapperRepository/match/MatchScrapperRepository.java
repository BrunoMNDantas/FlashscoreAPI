package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.match;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.ScrapperRepository;
import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.openqa.selenium.WebDriver;

import java.util.LinkedList;

public class MatchScrapperRepository extends ScrapperRepository<MatchKey, Match, MatchPage> {

    public MatchScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected MatchPage getPage(WebDriver driver, MatchKey key) {
        return new MatchPage(driver, key);
    }

    @Override
    protected Match scrapEntity(WebDriver driver, MatchPage page, MatchKey matchKey) {
        Match match = new Match();

        match.setKey(matchKey);
        match.setHomeTeamKey(page.getHomeTeamKey());
        match.setAwayTeamKey(page.getAwayTeamKey());
        match.setHomeTeamGoals(page.getHomeTeamGoals());
        match.setAwayTeamGoals(page.getAwayTeamGoals());
        match.setDate(page.getDate());

        match.setEvents(new LinkedList<>());
        match.getEvents().addAll(page.getFirstHalfEvents());
        match.getEvents().addAll(page.getSecondHalfEvents());
        match.getEvents().addAll(page.getExtraTimeEvents());
        match.getEvents().addAll(page.getPenaltiesEvents());

        PlayersPage playersPage = new PlayersPage(driver, matchKey);
        playersPage.load();

        match.setHomeCoachPlayerKey(playersPage.getHomeCoachKey());
        match.setAwayCoachPlayerKey(playersPage.getAwayCoachKey());
        match.setHomeLineupPlayersKeys(playersPage.getHomeLineupPlayersKeys());
        match.setAwayLineupPlayersKeys(playersPage.getAwayLineupPlayersKeys());
        match.setHomeBenchPlayersKeys(playersPage.getHomeBenchPlayers());
        match.setAwayBenchPlayersKeys(playersPage.getAwayBenchPlayers());

        return match;
    }

}