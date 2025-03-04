package com.github.brunomndantas.flashscore.api.logic.services.scrapperService;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapper;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapperException;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.IEntityScrapper;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.EntityScrapService;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.Report;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.SeasonScrapService;
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
public class SeasonScrapServiceTests {

    private static final TaskPool TASK_POOL = new TaskPool(1);

    private static final Competition COMPETITION_A = new Competition();
    private static final Competition COMPETITION_B = new Competition();

    private static final Season SEASON_A = new Season();
    private static final Season SEASON_B = new Season();
    private static final Season SEASON_C = new Season();
    private static final Season SEASON_D = new Season();

    private static final IRepository<SeasonKey, Season> REPOSITORY = new MemoryRepository<>(Season::getKey);
    private static final IEntityScrapper<SeasonKey, Season> SCRAPPER = new EntityScrapper<>(REPOSITORY, TASK_POOL);


    @BeforeAll
    public static void init() throws RepositoryException {
        COMPETITION_A.setKey(new CompetitionKey("A", "A", "A"));
        COMPETITION_B.setKey(new CompetitionKey("A", "A", "B"));

        SEASON_A.setKey(new SeasonKey("A", "A", "A", "A"));
        SEASON_B.setKey(new SeasonKey("A", "A", "A", "B"));
        SEASON_C.setKey(new SeasonKey("A", "A", "B", "C"));
        SEASON_D.setKey(new SeasonKey("A", "A", "B", "D"));

        COMPETITION_A.setSeasonsKeys(Arrays.asList(SEASON_A.getKey(), SEASON_B.getKey()));
        COMPETITION_B.setSeasonsKeys(Arrays.asList(SEASON_C.getKey(), SEASON_D.getKey()));

        REPOSITORY.insert(SEASON_A);
        REPOSITORY.insert(SEASON_B);
        REPOSITORY.insert(SEASON_C);
        REPOSITORY.insert(SEASON_D);
    }

    @AfterAll
    public static void finish() {
        TASK_POOL.close();
    }


    @Test
    public void shouldRegisterAllSeasons() throws EntityScrapperException {
        SeasonScrapService service = new SeasonScrapService(SCRAPPER, EntityScrapService.ALL);
        testService(service, Arrays.asList(SEASON_A, SEASON_B, SEASON_C, SEASON_D));
    }

    @Test
    public void shouldRegisterNoSeasons() throws EntityScrapperException {
        SeasonScrapService service = new SeasonScrapService(SCRAPPER, 0);
        testService(service, Arrays.asList());
    }

    @Test
    public void shouldRegisterRightAmountOfSeasons() throws EntityScrapperException {
        SeasonScrapService service = new SeasonScrapService(SCRAPPER, 1);
        testService(service, Arrays.asList(SEASON_A, SEASON_C));
    }

    private void testService(SeasonScrapService service, Collection<Season> entities) throws EntityScrapperException {
        Report report = new Report();
        report.getCompetitionReport().addSucceededLoad(COMPETITION_A.getKey(), COMPETITION_A);
        report.getCompetitionReport().addSucceededLoad(COMPETITION_B.getKey(), COMPETITION_B);

        service.scrap(report);

        Assertions.assertEquals(entities.size(), report.getSeasonReport().getEntitiesToLoad().size());
        Assertions.assertEquals(entities.size(), report.getSeasonReport().getSucceededLoads().size());

        for(Season entity : entities) {
            Assertions.assertTrue(report.getSeasonReport().getEntitiesToLoad().contains(entity.getKey()));
            Assertions.assertTrue(report.getSeasonReport().getSucceededLoads().containsValue(entity));
        }
    }

}