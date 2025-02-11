package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonId;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SeasonScrapperRepositoryTests extends ScrapperRepositoryTests<SeasonId, Season> {

    @Override
    protected ScrapperRepository<SeasonId, Season> createRepository() {
        return createRepository(DRIVER_POOL);
    }

    @Override
    protected ScrapperRepository<SeasonId, Season> createRepository(IDriverPool driverPool) {
        return new SeasonScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Override
    protected SeasonId getExistentKey() {
        return new SeasonId("table-tennis", "others-men", "singapore-smash", "2023");
    }

    @Override
    protected SeasonId getNonExistentKey() {
        return new SeasonId("table-tennis", "others-men", "singapore-smash", "1930");
    }


    @Test
    public void shouldScrapData() throws Exception {
        SeasonId key = new SeasonId("hockey", "germany", "del", "2023-2024");
        ScrapperRepository<SeasonId, Season> repository = createRepository(DRIVER_POOL);

        Season season = repository.get(key);

        Assertions.assertEquals(key.getSportId(), season.getId().getSportId());
        Assertions.assertEquals(key.getRegionId(), season.getId().getRegionId());
        Assertions.assertEquals(key.getCompetitionId(), season.getId().getCompetitionId());
        Assertions.assertEquals(key.getId(), season.getId().getId());
        Assertions.assertEquals("2023-2024", season.getName());
        Assertions.assertNotNull(season.getMatchesIds());
        Assertions.assertTrue(season.getMatchesIds().size() > 300);

        for(String matchId: season.getMatchesIds()) {
            Assertions.assertNotNull(matchId);
            Assertions.assertFalse(matchId.trim().isEmpty());
        }

        Assertions.assertTrue(season.getMatchesIds().contains("bZd70Ik6"));
    }

}