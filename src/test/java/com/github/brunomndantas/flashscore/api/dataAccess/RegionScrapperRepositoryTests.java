package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

@SpringBootTest
public class RegionScrapperRepositoryTests extends ScrapperRepositoryTests<RegionKey, Region> {

    @Override
    protected ScrapperRepository<RegionKey, Region> createRepository() {
        return createRepository(DRIVER_POOL);
    }

    @Override
    protected ScrapperRepository<RegionKey, Region> createRepository(IDriverPool driverPool) {
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
        ScrapperRepository<RegionKey,Region> repository = createRepository(DRIVER_POOL);

        Region region = repository.get(key);

        Assertions.assertEquals(key.getSportId(), region.getKey().getSportId());
        Assertions.assertEquals(key.getRegionId(), region.getKey().getRegionId());
        Assertions.assertEquals("SPAIN", region.getName());
        Assertions.assertNotNull(region.getCompetitionsKeys());
        Assertions.assertTrue(region.getCompetitionsKeys().size() > 6);

        for(CompetitionKey competitionKey : region.getCompetitionsKeys()) {
            Assertions.assertNotNull(competitionKey);
            Assertions.assertEquals(key.getSportId(), competitionKey.getSportId());
            Assertions.assertEquals(key.getRegionId(), competitionKey.getRegionId());
            Assertions.assertNotNull(competitionKey.getCompetitionId());
            Assertions.assertFalse(competitionKey.getCompetitionId().trim().isEmpty());
        }

        Collection<String> competitionsIds = region.getCompetitionsKeys().stream().map(CompetitionKey::getCompetitionId).toList();
        Assertions.assertTrue(competitionsIds.contains("liga-asobal"));
    }

}