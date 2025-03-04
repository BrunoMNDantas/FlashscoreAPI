package com.github.brunomndantas.flashscore.api.logic.services.scrapperService;

import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.player.Player;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapper;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapperException;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.IEntityScrapper;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.EntityScrapService;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.PlayerScrapService;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.Report;
import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import com.github.brunomndantas.repository4j.memory.MemoryRepository;
import com.github.brunomndantas.tpl4j.task.pool.TaskPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;

@SpringBootTest
public class PlayerScrapServiceTests {

    private static final TaskPool TASK_POOL = new TaskPool(1);

    private static final Season SEASON_A = new Season();
    private static final Season SEASON_B = new Season();

    private static final Match MATCH_A = new Match();
    private static final Match MATCH_B = new Match();

    private static final Team TEAM_A = new Team();
    private static final Team TEAM_B = new Team();
    private static final Team TEAM_C = new Team();
    private static final Team TEAM_D = new Team();

    private static final Player COACH_A = new Player();
    private static final Player COACH_B = new Player();
    private static final Player COACH_C = new Player();
    private static final Player COACH_D = new Player();

    private static final Player PLAYER_A = new Player();
    private static final Player PLAYER_B = new Player();
    private static final Player PLAYER_C = new Player();
    private static final Player PLAYER_D = new Player();
    private static final Player PLAYER_E = new Player();
    private static final Player PLAYER_F = new Player();
    private static final Player PLAYER_G = new Player();
    private static final Player PLAYER_H = new Player();

    private static final IRepository<PlayerKey, Player> REPOSITORY = new MemoryRepository<>(Player::getKey);
    private static final IEntityScrapper<PlayerKey, Player> SCRAPPER = new EntityScrapper<>(REPOSITORY, TASK_POOL);


    @BeforeAll
    public static void init() throws RepositoryException {
        SEASON_A.setKey(new SeasonKey("A", "A", "A", "A"));
        SEASON_B.setKey(new SeasonKey("B", "B", "B", "B"));

        MATCH_A.setKey(new MatchKey("A"));
        MATCH_B.setKey(new MatchKey("B"));

        TEAM_A.setKey(new TeamKey("A", "A"));
        TEAM_B.setKey(new TeamKey("B", "B"));
        TEAM_C.setKey(new TeamKey("C", "C"));
        TEAM_D.setKey(new TeamKey("D", "D"));

        COACH_A.setKey(new PlayerKey("C", "A"));
        COACH_B.setKey(new PlayerKey("C", "B"));
        COACH_C.setKey(new PlayerKey("C", "C"));
        COACH_D.setKey(new PlayerKey("C", "D"));

        PLAYER_A.setKey(new PlayerKey("P", "A"));
        PLAYER_B.setKey(new PlayerKey("P", "B"));
        PLAYER_C.setKey(new PlayerKey("P", "C"));
        PLAYER_D.setKey(new PlayerKey("P", "D"));
        PLAYER_E.setKey(new PlayerKey("P", "E"));
        PLAYER_F.setKey(new PlayerKey("P", "F"));
        PLAYER_G.setKey(new PlayerKey("P", "G"));
        PLAYER_H.setKey(new PlayerKey("P", "H"));

        SEASON_A.setMatchesKeys(Arrays.asList(MATCH_A.getKey()));
        SEASON_B.setMatchesKeys(Arrays.asList(MATCH_B.getKey()));

        MATCH_A.setHomeCoachPlayerKey(COACH_A.getKey());
        MATCH_A.setAwayCoachPlayerKey(COACH_B.getKey());
        MATCH_B.setHomeCoachPlayerKey(COACH_C.getKey());
        MATCH_B.setAwayCoachPlayerKey(COACH_D.getKey());
        MATCH_A.setHomeLineupPlayersKeys(Arrays.asList(PLAYER_A.getKey()));
        MATCH_A.setAwayLineupPlayersKeys(Arrays.asList(PLAYER_B.getKey()));
        MATCH_B.setHomeLineupPlayersKeys(Arrays.asList(PLAYER_C.getKey()));
        MATCH_B.setAwayLineupPlayersKeys(Arrays.asList(PLAYER_D.getKey()));
        MATCH_A.setHomeBenchPlayersKeys(Arrays.asList(PLAYER_E.getKey()));
        MATCH_A.setAwayBenchPlayersKeys(Arrays.asList(PLAYER_F.getKey()));
        MATCH_B.setHomeBenchPlayersKeys(Arrays.asList(PLAYER_G.getKey()));
        MATCH_B.setAwayBenchPlayersKeys(Arrays.asList(PLAYER_H.getKey()));

        TEAM_A.setCoachKey(COACH_A.getKey());
        TEAM_B.setCoachKey(COACH_B.getKey());
        TEAM_C.setCoachKey(COACH_C.getKey());
        TEAM_D.setCoachKey(COACH_D.getKey());
        TEAM_A.setPlayersKeys(Arrays.asList(PLAYER_A.getKey()));
        TEAM_B.setPlayersKeys(Arrays.asList(PLAYER_B.getKey()));
        TEAM_C.setPlayersKeys(Arrays.asList(PLAYER_C.getKey()));
        TEAM_D.setPlayersKeys(Arrays.asList(PLAYER_D.getKey()));

        REPOSITORY.insert(COACH_A);
        REPOSITORY.insert(COACH_B);
        REPOSITORY.insert(COACH_C);
        REPOSITORY.insert(COACH_D);

        REPOSITORY.insert(PLAYER_A);
        REPOSITORY.insert(PLAYER_B);
        REPOSITORY.insert(PLAYER_C);
        REPOSITORY.insert(PLAYER_D);
        REPOSITORY.insert(PLAYER_E);
        REPOSITORY.insert(PLAYER_F);
        REPOSITORY.insert(PLAYER_G);
        REPOSITORY.insert(PLAYER_H);
    }

    @AfterAll
    public static void finish() {
        TASK_POOL.close();
    }


    @Test
    public void shouldRegisterAllPlayersPerMatches() throws EntityScrapperException {
        Report report = new Report();
        report.getSeasonReport().addSucceededLoad(SEASON_A.getKey(), SEASON_A);
        report.getSeasonReport().addSucceededLoad(SEASON_B.getKey(), SEASON_B);
        report.getMatchReport().addSucceededLoad(MATCH_A.getKey(), MATCH_A);
        report.getMatchReport().addSucceededLoad(MATCH_B.getKey(), MATCH_B);

        PlayerScrapService service = new PlayerScrapService(SCRAPPER, EntityScrapService.ALL, 0);

        testService(service, report, Arrays.asList(
            COACH_A, COACH_B, COACH_C, COACH_D,
            PLAYER_A, PLAYER_B, PLAYER_C, PLAYER_D,
            PLAYER_E, PLAYER_F, PLAYER_G, PLAYER_H
        ));
    }

    @Test
    public void shouldRegisterAllPlayersPerTeams() throws EntityScrapperException {
        Report report = new Report();
        report.getTeamReport().addSucceededLoad(TEAM_A.getKey(), TEAM_A);
        report.getTeamReport().addSucceededLoad(TEAM_B.getKey(), TEAM_B);
        report.getTeamReport().addSucceededLoad(TEAM_C.getKey(), TEAM_C);
        report.getTeamReport().addSucceededLoad(TEAM_D.getKey(), TEAM_D);

        PlayerScrapService service = new PlayerScrapService(SCRAPPER, 0, EntityScrapService.ALL);

        testService(service, report, Arrays.asList(
                COACH_A, COACH_B, COACH_C, COACH_D,
                PLAYER_A, PLAYER_B, PLAYER_C, PLAYER_D
        ));
    }

    @Test
    public void shouldRegisterNoPlayersPerMatches() throws EntityScrapperException {
        Report report = new Report();
        report.getSeasonReport().addSucceededLoad(SEASON_A.getKey(), SEASON_A);
        report.getSeasonReport().addSucceededLoad(SEASON_B.getKey(), SEASON_B);
        report.getMatchReport().addSucceededLoad(MATCH_A.getKey(), MATCH_A);
        report.getMatchReport().addSucceededLoad(MATCH_B.getKey(), MATCH_B);

        PlayerScrapService service = new PlayerScrapService(SCRAPPER, 0, EntityScrapService.ALL);

        testService(service, report, Arrays.asList());
    }

    @Test
    public void shouldRegisterNoPlayersPerTeams() throws EntityScrapperException {
        Report report = new Report();
        report.getTeamReport().addSucceededLoad(TEAM_A.getKey(), TEAM_A);
        report.getTeamReport().addSucceededLoad(TEAM_B.getKey(), TEAM_B);
        report.getTeamReport().addSucceededLoad(TEAM_C.getKey(), TEAM_C);
        report.getTeamReport().addSucceededLoad(TEAM_D.getKey(), TEAM_D);

        PlayerScrapService service = new PlayerScrapService(SCRAPPER, EntityScrapService.ALL, 0);

        testService(service, report, Arrays.asList());
    }

    @Test
    public void shouldRegisterRightAmountOfPlayersPerMatches() throws EntityScrapperException {
        Report report = new Report();
        report.getSeasonReport().addSucceededLoad(SEASON_A.getKey(), SEASON_A);
        report.getSeasonReport().addSucceededLoad(SEASON_B.getKey(), SEASON_B);
        report.getMatchReport().addSucceededLoad(MATCH_A.getKey(), MATCH_A);
        report.getMatchReport().addSucceededLoad(MATCH_B.getKey(), MATCH_B);

        PlayerScrapService service = new PlayerScrapService(SCRAPPER, 1, EntityScrapService.ALL);

        testService(service, report, Arrays.asList(COACH_A, COACH_C));
    }

    @Test
    public void shouldRegisterRightAmountOfPlayersPerTeams() throws EntityScrapperException {
        Report report = new Report();
        report.getTeamReport().addSucceededLoad(TEAM_A.getKey(), TEAM_A);
        report.getTeamReport().addSucceededLoad(TEAM_B.getKey(), TEAM_B);
        report.getTeamReport().addSucceededLoad(TEAM_C.getKey(), TEAM_C);
        report.getTeamReport().addSucceededLoad(TEAM_D.getKey(), TEAM_D);

        PlayerScrapService service = new PlayerScrapService(SCRAPPER, EntityScrapService.ALL, 1);

        testService(service, report, Arrays.asList(
                COACH_A, COACH_B, COACH_C, COACH_D
        ));
    }

    private void testService(PlayerScrapService service, Report report, Collection<Player> entities) throws EntityScrapperException {
        service.scrap(report);

        Assertions.assertEquals(entities.size(), report.getPlayerReport().getEntitiesToLoad().size());
        Assertions.assertEquals(entities.size(), report.getPlayerReport().getSucceededLoads().size());

        for(Player entity : entities) {
            Assertions.assertTrue(report.getPlayerReport().getEntitiesToLoad().contains(entity.getKey()));
            Assertions.assertTrue(report.getPlayerReport().getSucceededLoads().containsValue(entity));
        }
    }

}