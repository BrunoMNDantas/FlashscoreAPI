package com.github.brunomndantas.flashscore.api.logic.services.scrapperService;

import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapper;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapperException;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.IEntityScrapper;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.EntityScrapService;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.RegionScrapService;
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
public class RegionScrapServiceTests {

    private static final TaskPool TASK_POOL = new TaskPool(1);

    private static final Sport SPORT_A = new Sport();
    private static final Sport SPORT_B = new Sport();

    private static final Region REGION_A = new Region();
    private static final Region REGION_B = new Region();
    private static final Region REGION_C = new Region();
    private static final Region REGION_D = new Region();

    private static final IRepository<RegionKey, Region> REPOSITORY = new MemoryRepository<>(Region::getKey);
    private static final IEntityScrapper<RegionKey, Region> SCRAPPER = new EntityScrapper<>(REPOSITORY, TASK_POOL);


    @BeforeAll
    public static void init() throws RepositoryException {
        SPORT_A.setKey(new SportKey("A"));
        SPORT_B.setKey(new SportKey("B"));

        REGION_A.setKey(new RegionKey("A", "A"));
        REGION_B.setKey(new RegionKey("A", "B"));
        REGION_C.setKey(new RegionKey("B", "C"));
        REGION_D.setKey(new RegionKey("B", "D"));

        SPORT_A.setRegionsKeys(Arrays.asList(REGION_A.getKey(), REGION_B.getKey()));
        SPORT_B.setRegionsKeys(Arrays.asList(REGION_C.getKey(), REGION_D.getKey()));

        REPOSITORY.insert(REGION_A);
        REPOSITORY.insert(REGION_B);
        REPOSITORY.insert(REGION_C);
        REPOSITORY.insert(REGION_D);
    }

    @AfterAll
    public static void finish() {
        TASK_POOL.close();
    }


    @Test
    public void shouldRegisterAllRegions() throws EntityScrapperException {
        RegionScrapService service = new RegionScrapService(SCRAPPER, EntityScrapService.ALL);
        testService(service, Arrays.asList(REGION_A, REGION_B, REGION_C, REGION_D));
    }

    @Test
    public void shouldRegisterNoRegions() throws EntityScrapperException {
        RegionScrapService service = new RegionScrapService(SCRAPPER, 0);
        testService(service, Arrays.asList());
    }

    @Test
    public void shouldRegisterRightAmountOfRegions() throws EntityScrapperException {
        RegionScrapService service = new RegionScrapService(SCRAPPER, 1);
        testService(service, Arrays.asList(REGION_A, REGION_C));
    }

    private void testService(RegionScrapService service, Collection<Region> entities) throws EntityScrapperException {
        Report report = new Report();
        report.getSportReport().addSucceededLoad(SPORT_A.getKey(), SPORT_A);
        report.getSportReport().addSucceededLoad(SPORT_B.getKey(), SPORT_B);

        service.scrap(report);

        Assertions.assertEquals(entities.size(), report.getRegionReport().getEntitiesToLoad().size());
        Assertions.assertEquals(entities.size(), report.getRegionReport().getSucceededLoads().size());

        for(Region entity : entities) {
            Assertions.assertTrue(report.getRegionReport().getEntitiesToLoad().contains(entity.getKey()));
            Assertions.assertTrue(report.getRegionReport().getSucceededLoads().containsValue(entity));
        }
    }

}