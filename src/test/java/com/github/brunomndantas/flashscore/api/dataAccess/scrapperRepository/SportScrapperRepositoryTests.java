package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository;

import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

@SpringBootTest
public class SportScrapperRepositoryTests extends ScrapperRepositoryTests<SportKey, Sport> {

    @Override
    protected ScrapperRepository<SportKey, Sport> createRepository() {
        return createRepository(DRIVER_POOL);
    }

    @Override
    protected ScrapperRepository<SportKey, Sport> createRepository(IDriverPool driverPool) {
        return new SportScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Override
    protected SportKey getExistentKey() {
        return new SportKey("football");
    }

    @Override
    protected SportKey getNonExistentKey() {
        return new SportKey("non_existent_key");
    }


    @Test
    public void shouldScrapData() throws Exception {
        SportKey key = new SportKey("beach-soccer");
        ScrapperRepository<SportKey,Sport> repository = createRepository(DRIVER_POOL);

        Sport sport = repository.get(key);

        Assertions.assertNull(getConstraintViolation(sport));

        Assertions.assertEquals(key, sport.getKey());
        Assertions.assertEquals("Beach Soccer", sport.getName());
        Assertions.assertTrue(sport.getRegionsKeys().size() > 8);

        for(RegionKey regionKey : sport.getRegionsKeys()) {
            Assertions.assertEquals(key.getSportId(), regionKey.getSportId());
        }

        Collection<String> regionsIds = sport.getRegionsKeys().stream().map(RegionKey::getRegionId).toList();
        Assertions.assertTrue(regionsIds.contains("portugal"));
    }

}