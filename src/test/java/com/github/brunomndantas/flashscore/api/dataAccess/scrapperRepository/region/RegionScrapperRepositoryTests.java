package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.region;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.ScrapperRepository;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.ScrapperRepositoryTests;
import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegionScrapperRepositoryTests extends ScrapperRepositoryTests<RegionKey, Region, RegionPage> {

    @Override
    protected ScrapperRepository<RegionKey, Region, RegionPage> createRepository() {
        return createRepository(driverPool);
    }

    @Override
    protected ScrapperRepository<RegionKey, Region, RegionPage> createRepository(IDriverPool driverPool) {
        return new RegionScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Override
    protected RegionKey getExistentKey() {
        return new RegionKey("football", "spain");
    }

    @Override
    protected RegionKey getNonExistentKey() {
        return new RegionKey("football", "non_existent_region");
    }


    @Test
    public void shouldScrapData() throws Exception {
        RegionKey key = new RegionKey("handball", "spain");
        ScrapperRepository<RegionKey,Region,RegionPage> repository = createRepository(driverPool);

        Region region = repository.get(key);

        Assertions.assertNull(getConstraintViolation(region));

        Assertions.assertEquals(key, region.getKey());
        Assertions.assertEquals("Spain", region.getName());
        Assertions.assertTrue(region.getCompetitionsKeys().size() > 6);
    }

}