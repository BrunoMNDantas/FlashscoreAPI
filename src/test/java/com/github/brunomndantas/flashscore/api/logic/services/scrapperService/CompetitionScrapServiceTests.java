package com.github.brunomndantas.flashscore.api.logic.services.scrapperService;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapper;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapperException;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.IEntityScrapper;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.CompetitionScrapService;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.EntityScrapService;
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
public class CompetitionScrapServiceTests {

    private static final TaskPool TASK_POOL = new TaskPool(1);

    private static final Region REGION_A = new Region();
    private static final Region REGION_B = new Region();

    private static final Competition COMPETITION_A = new Competition();
    private static final Competition COMPETITION_B = new Competition();
    private static final Competition COMPETITION_C = new Competition();
    private static final Competition COMPETITION_D = new Competition();

    private static final IRepository<CompetitionKey, Competition> REPOSITORY = new MemoryRepository<>(Competition::getKey);
    private static final IEntityScrapper<CompetitionKey, Competition> SCRAPPER = new EntityScrapper<>(REPOSITORY, TASK_POOL);


    @BeforeAll
    public static void init() throws RepositoryException {
        REGION_A.setKey(new RegionKey("A", "A"));
        REGION_B.setKey(new RegionKey("A", "B"));

        COMPETITION_A.setKey(new CompetitionKey("A", "A", "A"));
        COMPETITION_B.setKey(new CompetitionKey("A", "A", "B"));
        COMPETITION_C.setKey(new CompetitionKey("A", "B", "C"));
        COMPETITION_D.setKey(new CompetitionKey("A", "B", "D"));

        REGION_A.setCompetitionsKeys(Arrays.asList(COMPETITION_A.getKey(), COMPETITION_B.getKey()));
        REGION_B.setCompetitionsKeys(Arrays.asList(COMPETITION_C.getKey(), COMPETITION_D.getKey()));

        REPOSITORY.insert(COMPETITION_A);
        REPOSITORY.insert(COMPETITION_B);
        REPOSITORY.insert(COMPETITION_C);
        REPOSITORY.insert(COMPETITION_D);
    }

    @AfterAll
    public static void finish() {
        TASK_POOL.close();
    }


    @Test
    public void shouldRegisterAllCompetitions() throws EntityScrapperException {
        CompetitionScrapService service = new CompetitionScrapService(SCRAPPER, EntityScrapService.ALL);
        testService(service, Arrays.asList(COMPETITION_A, COMPETITION_B, COMPETITION_C, COMPETITION_D));
    }

    @Test
    public void shouldRegisterNoCompetitions() throws EntityScrapperException {
        CompetitionScrapService service = new CompetitionScrapService(SCRAPPER, 0);
        testService(service, Arrays.asList());
    }

    @Test
    public void shouldRegisterRightAmountOfCompetitions() throws EntityScrapperException {
        CompetitionScrapService service = new CompetitionScrapService(SCRAPPER, 1);
        testService(service, Arrays.asList(COMPETITION_A, COMPETITION_C));
    }

    private void testService(CompetitionScrapService service, Collection<Competition> entities) throws EntityScrapperException {
        Report report = new Report();
        report.getRegionReport().addSucceededLoad(REGION_A.getKey(), REGION_A);
        report.getRegionReport().addSucceededLoad(REGION_B.getKey(), REGION_B);

        service.scrap(report);

        Assertions.assertEquals(entities.size(), report.getCompetitionReport().getEntitiesToLoad().size());
        Assertions.assertEquals(entities.size(), report.getCompetitionReport().getSucceededLoads().size());

        for(Competition entity : entities) {
            Assertions.assertTrue(report.getCompetitionReport().getEntitiesToLoad().contains(entity.getKey()));
            Assertions.assertTrue(report.getCompetitionReport().getSucceededLoads().containsValue(entity));
        }
    }

}