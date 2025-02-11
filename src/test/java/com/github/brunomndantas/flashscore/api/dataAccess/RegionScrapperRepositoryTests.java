package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionId;
import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionId;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPool;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class RegionScrapperRepositoryTests extends ScrapperRepositoryTests<RegionId, Region> {

    protected static final IDriverPool DRIVER_POOL = new DriverPool(DRIVER_SUPPLIER, 1);


    @AfterAll
    public static void dispose() throws Exception {
        DRIVER_POOL.close();
    }


    @Override
    protected ScrapperRepository<RegionId, Region> createRepository() {
        return createRepository(DRIVER_POOL);
    }

    @Override
    protected ScrapperRepository<RegionId, Region> createRepository(IDriverPool driverPool) {
        return new RegionScrapperRepository(driverPool, SCREEN_SHOOTS_DIRECTORY);
    }

    @Override
    protected RegionId getExistentKey() {
        return new RegionId("football", "spain");
    }

    @Override
    protected RegionId getNonExistentKey() {
        return new RegionId("football", "non_existent_region");
    }


    @Test
    public void shouldScrapData() throws Exception {
        RegionId key = new RegionId("handball", "spain");
        ScrapperRepository<RegionId,Region> repository = createRepository(DRIVER_POOL);

        Region region = repository.get(key);

        Assertions.assertEquals(key.getSportId(), region.getId().getSportId());
        Assertions.assertEquals(key.getId(), region.getId().getId());
        Assertions.assertEquals("SPAIN", region.getName());
        Assertions.assertNotNull(region.getCompetitionsIds());
        Assertions.assertTrue(region.getCompetitionsIds().size() > 6);

        for(CompetitionId competitionId: region.getCompetitionsIds()) {
            Assertions.assertNotNull(competitionId);
            Assertions.assertEquals(key.getSportId(), competitionId.getSportId());
            Assertions.assertEquals(key.getId(), competitionId.getRegionId());
            Assertions.assertNotNull(competitionId.getId());
            Assertions.assertFalse(competitionId.getId().trim().isEmpty());
        }

        Collection<String> competitionsIds = region.getCompetitionsIds().stream().map(CompetitionId::getId).toList();
        Assertions.assertTrue(competitionsIds.contains("liga-asobal"));
    }

}