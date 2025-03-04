package com.github.brunomndantas.flashscore.api.logic.services.scrapperService;

import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapper;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapperException;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.IEntityScrapper;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.EntityScrapService;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.MatchScrapService;
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
public class MatchScrapServiceTests {

    private static final TaskPool TASK_POOL = new TaskPool(1);

    private static final Season SEASON_A = new Season();
    private static final Season SEASON_B = new Season();

    private static final Match MATCH_A = new Match();
    private static final Match MATCH_B = new Match();
    private static final Match MATCH_C = new Match();
    private static final Match MATCH_D = new Match();

    private static final IRepository<MatchKey, Match> REPOSITORY = new MemoryRepository<>(Match::getKey);
    private static final IEntityScrapper<MatchKey, Match> SCRAPPER = new EntityScrapper<>(REPOSITORY, TASK_POOL);


    @BeforeAll
    public static void init() throws RepositoryException {
        SEASON_A.setKey(new SeasonKey("A", "A", "A", "A"));
        SEASON_B.setKey(new SeasonKey("A", "A", "A", "B"));

        MATCH_A.setKey(new MatchKey("A"));
        MATCH_B.setKey(new MatchKey("B"));
        MATCH_C.setKey(new MatchKey("C"));
        MATCH_D.setKey(new MatchKey("D"));

        SEASON_A.setMatchesKeys(Arrays.asList(MATCH_A.getKey(), MATCH_B.getKey()));
        SEASON_B.setMatchesKeys(Arrays.asList(MATCH_C.getKey(), MATCH_D.getKey()));

        REPOSITORY.insert(MATCH_A);
        REPOSITORY.insert(MATCH_B);
        REPOSITORY.insert(MATCH_C);
        REPOSITORY.insert(MATCH_D);
    }

    @AfterAll
    public static void finish() {
        TASK_POOL.close();
    }


    @Test
    public void shouldRegisterAllMatches() throws EntityScrapperException {
        MatchScrapService service = new MatchScrapService(SCRAPPER, EntityScrapService.ALL);
        testService(service, Arrays.asList(MATCH_A, MATCH_B, MATCH_C, MATCH_D));
    }

    @Test
    public void shouldRegisterNoMatches() throws EntityScrapperException {
        MatchScrapService service = new MatchScrapService(SCRAPPER, 0);
        testService(service, Arrays.asList());
    }

    @Test
    public void shouldRegisterRightAmountOfMatches() throws EntityScrapperException {
        MatchScrapService service = new MatchScrapService(SCRAPPER, 1);
        testService(service, Arrays.asList(MATCH_A, MATCH_C));
    }

    private void testService(MatchScrapService service, Collection<Match> entities) throws EntityScrapperException {
        Report report = new Report();
        report.getSeasonReport().addSucceededLoad(SEASON_A.getKey(), SEASON_A);
        report.getSeasonReport().addSucceededLoad(SEASON_B.getKey(), SEASON_B);

        service.scrap(report);

        Assertions.assertEquals(entities.size(), report.getMatchReport().getEntitiesToLoad().size());
        Assertions.assertEquals(entities.size(), report.getMatchReport().getSucceededLoads().size());

        for(Match entity : entities) {
            Assertions.assertTrue(report.getMatchReport().getEntitiesToLoad().contains(entity.getKey()));
            Assertions.assertTrue(report.getMatchReport().getSucceededLoads().containsValue(entity));
        }
    }

}