package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionId;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPool;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class SportScrapperRepositoryTests extends ScrapperRepositoryTests<String, Sport> {

    protected static final IDriverPool DRIVER_POOL = new DriverPool(DRIVER_SUPPLIER, 1);


    @AfterAll
    public static void dispose() throws Exception {
        DRIVER_POOL.close();
    }


    @Override
    protected ScrapperRepository<String, Sport> createRepository() {
        return createRepository(DRIVER_POOL);
    }

    @Override
    protected ScrapperRepository<String, Sport> createRepository(IDriverPool driverPool) {
        return new SportScrapperRepository(driverPool, SCREEN_SHOOTS_DIRECTORY);
    }

    @Override
    protected String getExistentKey() {
        return "football";
    }

    @Override
    protected String getNonExistentKey() {
        return "non_existent_key";
    }


    @Test
    public void shouldScrapData() throws Exception {
        String key = "beach-soccer";
        ScrapperRepository<String,Sport> repository = createRepository(DRIVER_POOL);

        Sport sport = repository.get(key);

        Assertions.assertEquals(key, sport.getId());
        Assertions.assertEquals("BEACH SOCCER", sport.getName());
        Assertions.assertNotNull(sport.getRegionsIds());
        Assertions.assertTrue(sport.getRegionsIds().size() > 8);

        for(RegionId regionId: sport.getRegionsIds()) {
            Assertions.assertNotNull(regionId);
            Assertions.assertEquals(key, regionId.getSportId());
            Assertions.assertNotNull(regionId.getId());
            Assertions.assertFalse(regionId.getId().trim().isEmpty());
        }

        Collection<String> regionsIds = sport.getRegionsIds().stream().map(RegionId::getId).toList();
        Assertions.assertTrue(regionsIds.contains("portugal"));
    }

}