package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchId;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MatchScrapperRepositoryTests extends ScrapperRepositoryTests<MatchId, Match> {

    @Override
    protected ScrapperRepository<MatchId, Match> createRepository() {
        return createRepository(DRIVER_POOL);
    }

    @Override
    protected ScrapperRepository<MatchId, Match> createRepository(IDriverPool driverPool) {
        return new MatchScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Override
    protected MatchId getExistentKey() {
        return new MatchId("48TzjJGs");
    }

    @Override
    protected MatchId getNonExistentKey() {
        return new MatchId("non_existent_id");
    }


    @Test
    public void shouldScrapData() throws Exception {
        MatchId key = new MatchId("z1BLcJWa");
        ScrapperRepository<MatchId, Match> repository = createRepository(DRIVER_POOL);

        Match match = repository.get(key);

        Assertions.assertEquals(key.getId(), match.getId().getId());
        Assertions.assertEquals("bournemouth/OtpNdwrc", match.getHomeTeamId());
        Assertions.assertEquals("liverpool/lId4TMwf", match.getAwayTeamId());
        Assertions.assertEquals(0, match.getHomeTeamGoals());
        Assertions.assertEquals(2, match.getAwayTeamGoals());
        Assertions.assertEquals(MatchScrapperRepository.DATE_FORMAT.parse("01.02.2025 15:00"), match.getDate());
    }

}