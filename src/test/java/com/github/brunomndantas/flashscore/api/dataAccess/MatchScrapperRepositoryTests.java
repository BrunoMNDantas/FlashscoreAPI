package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MatchScrapperRepositoryTests extends ScrapperRepositoryTests<MatchKey, Match> {

    @Override
    protected ScrapperRepository<MatchKey, Match> createRepository() {
        return createRepository(DRIVER_POOL);
    }

    @Override
    protected ScrapperRepository<MatchKey, Match> createRepository(IDriverPool driverPool) {
        return new MatchScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Override
    protected MatchKey getExistentKey() {
        return new MatchKey("48TzjJGs");
    }

    @Override
    protected MatchKey getNonExistentKey() {
        return new MatchKey("non_existent_id");
    }


    @Test
    public void shouldScrapData() throws Exception {
        MatchKey key = new MatchKey("z1BLcJWa");
        ScrapperRepository<MatchKey, Match> repository = createRepository(DRIVER_POOL);

        Match match = repository.get(key);

        Assertions.assertEquals(key.getMatchId(), match.getKey().getMatchId());
        Assertions.assertEquals("bournemouth/OtpNdwrc", match.getHomeTeamKey());
        Assertions.assertEquals("liverpool/lId4TMwf", match.getAwayTeamKey());
        Assertions.assertEquals(0, match.getHomeTeamGoals());
        Assertions.assertEquals(2, match.getAwayTeamGoals());
        Assertions.assertEquals(MatchScrapperRepository.DATE_FORMAT.parse("01.02.2025 15:00"), match.getDate());
    }

}