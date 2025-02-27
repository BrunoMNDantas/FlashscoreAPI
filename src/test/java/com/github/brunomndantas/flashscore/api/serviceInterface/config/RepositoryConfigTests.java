package com.github.brunomndantas.flashscore.api.serviceInterface.config;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.player.Player;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.repository4j.IRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RepositoryConfigTests {

    @Autowired
    public IRepository<SportKey, Sport> sportRepository;

    @Autowired
    public IRepository<RegionKey, Region> regionRepository;

    @Autowired
    public IRepository<CompetitionKey, Competition> competitionRepository;

    @Autowired
    public IRepository<SeasonKey, Season> seasonRepository;

    @Autowired
    public IRepository<MatchKey, Match> matchRepository;

    @Autowired
    public IRepository<TeamKey, Team> teamRepository;

    @Autowired
    private IRepository<PlayerKey, Player> playerRepository;


    @Test
    public void shouldCreateSportRepository() {
        Assertions.assertNotNull(sportRepository);
    }

    @Test
    public void shouldCreateRegionRepository() {
        Assertions.assertNotNull(regionRepository);
    }

    @Test
    public void shouldCreateCompetitionRepository() {
        Assertions.assertNotNull(competitionRepository);
    }

    @Test
    public void shouldCreateSeasonRepository() {
        Assertions.assertNotNull(seasonRepository);
    }

    @Test
    public void shouldCreateMatchRepository() {
        Assertions.assertNotNull(matchRepository);
    }

    @Test
    public void shouldCreateTeamRepository() {
        Assertions.assertNotNull(teamRepository);
    }

    @Test
    public void shouldCreatePlayerRepository() {
        Assertions.assertNotNull(playerRepository);
    }

}
