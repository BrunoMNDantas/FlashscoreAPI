package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionId;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportId;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

@SpringBootTest
public class SportScrapperRepositoryTests extends ScrapperRepositoryTests<SportId, Sport> {

    @Override
    protected ScrapperRepository<SportId, Sport> createRepository() {
        return createRepository(DRIVER_POOL);
    }

    @Override
    protected ScrapperRepository<SportId, Sport> createRepository(IDriverPool driverPool) {
        return new SportScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Override
    protected SportId getExistentKey() {
        return new SportId("football");
    }

    @Override
    protected SportId getNonExistentKey() {
        return new SportId("non_existent_key");
    }


    @Test
    public void shouldScrapData() throws Exception {
        SportId key = new SportId("beach-soccer");
        ScrapperRepository<SportId,Sport> repository = createRepository(DRIVER_POOL);

        Sport sport = repository.get(key);

        Assertions.assertEquals(key.getId(), sport.getId().getId());
        Assertions.assertEquals("Beach Soccer", sport.getName());
        Assertions.assertNotNull(sport.getRegionsIds());
        Assertions.assertTrue(sport.getRegionsIds().size() > 8);

        for(RegionId regionId: sport.getRegionsIds()) {
            Assertions.assertNotNull(regionId);
            Assertions.assertEquals(key.getId(), regionId.getSportId());
            Assertions.assertNotNull(regionId.getId());
            Assertions.assertFalse(regionId.getId().trim().isEmpty());
        }

        Collection<String> regionsIds = sport.getRegionsIds().stream().map(RegionId::getId).toList();
        Assertions.assertTrue(regionsIds.contains("portugal"));
    }

}