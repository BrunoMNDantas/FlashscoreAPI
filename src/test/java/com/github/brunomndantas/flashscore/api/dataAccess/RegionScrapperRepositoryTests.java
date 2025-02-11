package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionId;
import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionId;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

@SpringBootTest
public class RegionScrapperRepositoryTests extends ScrapperRepositoryTests<RegionId, Region> {

    @Override
    protected ScrapperRepository<RegionId, Region> createRepository() {
        return createRepository(driverPool);
    }

    @Override
    protected ScrapperRepository<RegionId, Region> createRepository(IDriverPool driverPool) {
        return new RegionScrapperRepository(driverPool, screenshotsDirectory);
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
        ScrapperRepository<RegionId,Region> repository = createRepository(driverPool);

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