package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SeasonScrapperRepositoryTests extends ScrapperRepositoryTests<SeasonKey, Season> {

    @Override
    protected ScrapperRepository<SeasonKey, Season> createRepository() {
        return createRepository(DRIVER_POOL);
    }

    @Override
    protected ScrapperRepository<SeasonKey, Season> createRepository(IDriverPool driverPool) {
        return new SeasonScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Override
    protected SeasonKey getExistentKey() {
        return new SeasonKey("table-tennis", "others-men", "singapore-smash", "2023");
    }

    @Override
    protected SeasonKey getNonExistentKey() {
        return new SeasonKey("table-tennis", "others-men", "singapore-smash", "1930");
    }


    @Test
    public void shouldScrapData() throws Exception {
        SeasonKey key = new SeasonKey("hockey", "germany", "del", "2023-2024");
        ScrapperRepository<SeasonKey, Season> repository = createRepository(DRIVER_POOL);

        Season season = repository.get(key);

        Assertions.assertEquals(key.getSportId(), season.getKey().getSportId());
        Assertions.assertEquals(key.getRegionId(), season.getKey().getRegionId());
        Assertions.assertEquals(key.getCompetitionId(), season.getKey().getCompetitionId());
        Assertions.assertEquals(key.getSeasonId(), season.getKey().getSeasonId());
        Assertions.assertEquals("2023-2024", season.getName());
        Assertions.assertNotNull(season.getMatchesKeys());
        Assertions.assertTrue(season.getMatchesKeys().size() > 300);

        for(MatchKey matchKey: season.getMatchesKeys()) {
            Assertions.assertNotNull(matchKey);
            Assertions.assertNotNull(matchKey.getMatchId());
            Assertions.assertFalse(matchKey.getMatchId().trim().isEmpty());
        }

        Assertions.assertTrue(season.getMatchesKeys().stream().anyMatch(mKey -> mKey.getMatchId().equals("bZd70Ik6")));
    }

}