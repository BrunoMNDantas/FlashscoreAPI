package com.github.brunomndantas.flashscore.api.dataAccess.utils;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Function;

@SpringBootTest
public class FlashscoreURLsTests {

    @Test
    public void shouldReturnValidSportURL() {
        SportKey key = new SportKey("handball");
        testUrl(key, FlashscoreURLs::getSportURL, FlashscoreURLs::getSportKey);
    }

    @Test
    public void shouldReturnValidRegionURL() {
        RegionKey key = new RegionKey("basketball", "usa");
        testUrl(key, FlashscoreURLs::getRegionURL, FlashscoreURLs::getRegionKey);
    }

    @Test
    public void shouldReturnValidCompetitionURL() {
        CompetitionKey key = new CompetitionKey("hockey", "germany", "del");
        testUrl(key, FlashscoreURLs::getCompetitionURL, FlashscoreURLs::getCompetitionKey);
    }

    @Test
    public void shouldReturnValidSeasonURL() {
        SeasonKey key = new SeasonKey("baseball", "south-korea", "kbo", "2018");
        testUrl(key, FlashscoreURLs::getSeasonURL, FlashscoreURLs::getSeasonKey);
    }

    @Test
    public void shouldReturnValidPastMatchesURL() {
        SeasonKey key = new SeasonKey("baseball", "south-korea", "kbo", "2018");
        testUrl(key, FlashscoreURLs::getSeasonPastMatchesURL, FlashscoreURLs::getSeasonKey);
    }

    @Test
    public void shouldReturnValidSeasonTodayMatchesURL() {
        SeasonKey key = new SeasonKey("baseball", "south-korea", "kbo", "2018");
        testUrl(key, FlashscoreURLs::getSeasonTodayMatchesURL, FlashscoreURLs::getSeasonKey);
    }

    @Test
    public void shouldReturnValidSeasonFutureMatchesURL() {
        SeasonKey key = new SeasonKey("baseball", "south-korea", "kbo", "2018");
        testUrl(key, FlashscoreURLs::getSeasonFutureMatchesURL, FlashscoreURLs::getSeasonKey);
    }

    @Test
    public void shouldReturnValidMatchURL() {
        MatchKey key = new MatchKey("bg70qYsH");
        testUrl(key, FlashscoreURLs::getMatchURL, FlashscoreURLs::getMatchKey);
    }

    @Test
    public void shouldReturnValidMatchPlayersURL() {
        MatchKey key = new MatchKey("bg70qYsH");
        testUrl(key, FlashscoreURLs::getMatchPlayersURL, FlashscoreURLs::getMatchKey);
    }

    @Test
    public void shouldReturnValidTeamURL() {
        TeamKey key = new TeamKey("zaragoza-hurricanes", "nsq7mjSs");
        testUrl(key, FlashscoreURLs::getTeamURL, FlashscoreURLs::getTeamKey);
    }

    @Test
    public void shouldReturnValidTeamSquadURL() {
        TeamKey key = new TeamKey("zaragoza-hurricanes", "nsq7mjSs");
        testUrl(key, FlashscoreURLs::getTeamSquadURL, FlashscoreURLs::getTeamKey);
    }

    @Test
    public void shouldReturnValidPlayerURL() {
        PlayerKey key = new PlayerKey("figo-luis", "W0IbygPl");
        testUrl(key, FlashscoreURLs::getPlayerURL, FlashscoreURLs::getPlayerKey);
    }

    protected <K> void testUrl(K key, Function<K,String> urlSupplier, Function<String,K> keySupplier) {
        String urlResult = urlSupplier.apply(key);
        K keyResult = keySupplier.apply(urlResult);

        Assertions.assertEquals(key, keyResult);
    }

}