package com.github.brunomndantas.flashscore.api.serviceInterface.config;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionId;
import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchId;
import com.github.brunomndantas.flashscore.api.logic.domain.player.Player;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerId;
import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionId;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonId;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportId;
import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamId;
import com.github.brunomndantas.repository4j.IRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RepositoryConfigTests {

    @Autowired
    public IRepository<SportId, Sport> sportRepository;

    @Autowired
    public IRepository<RegionId, Region> regionRepository;

    @Autowired
    public IRepository<CompetitionId, Competition> competitionRepository;

    @Autowired
    public IRepository<SeasonId, Season> seasonRepository;

    @Autowired
    public IRepository<MatchId, Match> matchRepository;

    @Autowired
    public IRepository<TeamId, Team> teamRepository;

    @Autowired
    private IRepository<PlayerId, Player> playerRepository;


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
