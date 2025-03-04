package com.github.brunomndantas.flashscore.api.logic.services.scrapperService;

import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapper;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapperException;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.IEntityScrapper;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.EntityScrapService;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.Report;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.SportScrapService;
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
import java.util.LinkedList;

@SpringBootTest
public class SportScrapServiceTests {

    private static final TaskPool TASK_POOL = new TaskPool(1);

    private static final Collection<String> SPORTS_IDS = new LinkedList<>();

    private static final Sport SPORT_A = new Sport();
    private static final Sport SPORT_B = new Sport();

    private static final IRepository<SportKey, Sport> REPOSITORY = new MemoryRepository<>(Sport::getKey);
    private static final IEntityScrapper<SportKey, Sport> SCRAPPER = new EntityScrapper<>(REPOSITORY, TASK_POOL);


    @BeforeAll
    public static void init() throws RepositoryException {
        SPORTS_IDS.addAll(Arrays.asList("A", "B"));

        SPORT_A.setKey(new SportKey("A"));
        SPORT_B.setKey(new SportKey("B"));

        REPOSITORY.insert(SPORT_A);
        REPOSITORY.insert(SPORT_B);
    }

    @AfterAll
    public static void finish() {
        TASK_POOL.close();
    }


    @Test
    public void shouldRegisterAllSports() throws EntityScrapperException {
        SportScrapService service = new SportScrapService(SCRAPPER, SPORTS_IDS, EntityScrapService.ALL);
        testService(service, Arrays.asList(SPORT_A, SPORT_B));
    }

    @Test
    public void shouldRegisterNoSports() throws EntityScrapperException {
        SportScrapService service = new SportScrapService(SCRAPPER, SPORTS_IDS, 0);
        testService(service, Arrays.asList());
    }

    @Test
    public void shouldRegisterRightAmountOfSports() throws EntityScrapperException {
        SportScrapService service = new SportScrapService(SCRAPPER, SPORTS_IDS, 1);
        testService(service, Arrays.asList(SPORT_A));
    }

    private void testService(SportScrapService service, Collection<Sport> entities) throws EntityScrapperException {
        Report report = new Report();

        service.scrap(report);

        Assertions.assertEquals(entities.size(), report.getSportReport().getEntitiesToLoad().size());
        Assertions.assertEquals(entities.size(), report.getSportReport().getSucceededLoads().size());

        for(Sport entity : entities) {
            Assertions.assertTrue(report.getSportReport().getEntitiesToLoad().contains(entity.getKey()));
            Assertions.assertTrue(report.getSportReport().getSucceededLoads().containsValue(entity));
        }
    }

}