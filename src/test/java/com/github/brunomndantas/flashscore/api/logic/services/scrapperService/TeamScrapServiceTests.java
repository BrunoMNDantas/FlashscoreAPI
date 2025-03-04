package com.github.brunomndantas.flashscore.api.logic.services.scrapperService;

import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapper;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapperException;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.IEntityScrapper;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.EntityScrapService;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.Report;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.TeamScrapService;
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
public class TeamScrapServiceTests {

    private static final TaskPool TASK_POOL = new TaskPool(1);

    private static final Season SEASON_A = new Season();
    private static final Season SEASON_B = new Season();

    private static final Match MATCH_A = new Match();
    private static final Match MATCH_B = new Match();
    private static final Match MATCH_C = new Match();
    private static final Match MATCH_D = new Match();

    private static final Team TEAM_A = new Team();
    private static final Team TEAM_B = new Team();
    private static final Team TEAM_C = new Team();
    private static final Team TEAM_D = new Team();
    private static final Team TEAM_E = new Team();
    private static final Team TEAM_F = new Team();
    private static final Team TEAM_G = new Team();
    private static final Team TEAM_H = new Team();

    private static final IRepository<TeamKey, Team> REPOSITORY = new MemoryRepository<>(Team::getKey);
    private static final IEntityScrapper<TeamKey, Team> SCRAPPER = new EntityScrapper<>(REPOSITORY, TASK_POOL);


    @BeforeAll
    public static void init() throws RepositoryException {
        SEASON_A.setKey(new SeasonKey("A", "A", "A", "A"));
        SEASON_B.setKey(new SeasonKey("A", "A", "A", "B"));

        MATCH_A.setKey(new MatchKey("A"));
        MATCH_B.setKey(new MatchKey("B"));
        MATCH_C.setKey(new MatchKey("C"));
        MATCH_D.setKey(new MatchKey("D"));

        TEAM_A.setKey(new TeamKey("A", "A"));
        TEAM_B.setKey(new TeamKey("B", "B"));
        TEAM_C.setKey(new TeamKey("C", "C"));
        TEAM_D.setKey(new TeamKey("D", "D"));
        TEAM_E.setKey(new TeamKey("E", "E"));
        TEAM_F.setKey(new TeamKey("F", "F"));
        TEAM_G.setKey(new TeamKey("G", "G"));
        TEAM_H.setKey(new TeamKey("H", "H"));

        SEASON_A.setMatchesKeys(Arrays.asList(MATCH_A.getKey(), MATCH_B.getKey()));
        SEASON_B.setMatchesKeys(Arrays.asList(MATCH_C.getKey(), MATCH_D.getKey()));

        MATCH_A.setHomeTeamKey(TEAM_A.getKey());
        MATCH_A.setAwayTeamKey(TEAM_B.getKey());
        MATCH_B.setHomeTeamKey(TEAM_C.getKey());
        MATCH_B.setAwayTeamKey(TEAM_D.getKey());
        MATCH_C.setHomeTeamKey(TEAM_E.getKey());
        MATCH_C.setAwayTeamKey(TEAM_F.getKey());
        MATCH_D.setHomeTeamKey(TEAM_G.getKey());
        MATCH_D.setAwayTeamKey(TEAM_H.getKey());

        REPOSITORY.insert(TEAM_A);
        REPOSITORY.insert(TEAM_B);
        REPOSITORY.insert(TEAM_C);
        REPOSITORY.insert(TEAM_D);
        REPOSITORY.insert(TEAM_E);
        REPOSITORY.insert(TEAM_F);
        REPOSITORY.insert(TEAM_G);
        REPOSITORY.insert(TEAM_H);
    }

    @AfterAll
    public static void finish() {
        TASK_POOL.close();
    }


    @Test
    public void shouldRegisterAllTeams() throws EntityScrapperException {
        TeamScrapService service = new TeamScrapService(SCRAPPER, EntityScrapService.ALL);
        testService(service, Arrays.asList(TEAM_A, TEAM_B, TEAM_C, TEAM_D, TEAM_E, TEAM_F, TEAM_G, TEAM_H));
    }

    @Test
    public void shouldRegisterNoTeams() throws EntityScrapperException {
        TeamScrapService service = new TeamScrapService(SCRAPPER, 0);
        testService(service, Arrays.asList());
    }

    @Test
    public void shouldRegisterRightAmountOfTeams() throws EntityScrapperException {
        TeamScrapService service = new TeamScrapService(SCRAPPER, 1);
        testService(service, Arrays.asList(TEAM_A, TEAM_E));
    }

    private void testService(TeamScrapService service, Collection<Team> entities) throws EntityScrapperException {
        Report report = new Report();
        report.getSeasonReport().addSucceededLoad(SEASON_A.getKey(), SEASON_A);
        report.getSeasonReport().addSucceededLoad(SEASON_B.getKey(), SEASON_B);
        report.getMatchReport().addSucceededLoad(MATCH_A.getKey(), MATCH_A);
        report.getMatchReport().addSucceededLoad(MATCH_B.getKey(), MATCH_B);
        report.getMatchReport().addSucceededLoad(MATCH_C.getKey(), MATCH_C);
        report.getMatchReport().addSucceededLoad(MATCH_D.getKey(), MATCH_D);

        service.scrap(report);

        Assertions.assertEquals(entities.size(), report.getTeamReport().getEntitiesToLoad().size());
        Assertions.assertEquals(entities.size(), report.getTeamReport().getSucceededLoads().size());

        for(Team entity : entities) {
            Assertions.assertTrue(report.getTeamReport().getEntitiesToLoad().contains(entity.getKey()));
            Assertions.assertTrue(report.getTeamReport().getSucceededLoads().containsValue(entity));
        }
    }

}