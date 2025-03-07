package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.ScrapperRepository;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.SeasonScrapperRepository;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

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

        Assertions.assertNull(getConstraintViolation(season));

        Assertions.assertEquals(key, season.getKey());
        Assertions.assertEquals(2023, season.getInitYear());
        Assertions.assertEquals(2024, season.getEndYear());
        Assertions.assertTrue(season.getMatchesKeys().size() > 300);

        Collection<String> matchesIds = season.getMatchesKeys().stream().map(MatchKey::getMatchId).toList();
        Assertions.assertTrue(matchesIds.contains("bZd70Ik6"));
    }

}