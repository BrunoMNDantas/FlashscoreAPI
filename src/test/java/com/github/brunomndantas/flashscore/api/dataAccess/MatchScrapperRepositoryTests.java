package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MatchScrapperRepositoryTests extends ScrapperRepositoryTests<String, Match> {

    @Override
    protected ScrapperRepository<String, Match> createRepository() {
        return createRepository(driverPool);
    }

    @Override
    protected ScrapperRepository<String, Match> createRepository(IDriverPool driverPool) {
        return new MatchScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Override
    protected String getExistentKey() {
        return "48TzjJGs";
    }

    @Override
    protected String getNonExistentKey() {
        return "non_existent_id";
    }


    @Test
    public void shouldScrapData() throws Exception {
        String key = "z1BLcJWa";
        ScrapperRepository<String, Match> repository = createRepository(driverPool);

        Match match = repository.get(key);

        Assertions.assertEquals(key, match.getId());
        Assertions.assertEquals("bournemouth/OtpNdwrc", match.getHomeTeamId());
        Assertions.assertEquals("liverpool/lId4TMwf", match.getAwayTeamId());
        Assertions.assertEquals(0, match.getHomeTeamGoals());
        Assertions.assertEquals(2, match.getAwayTeamGoals());
        Assertions.assertEquals(MatchScrapperRepository.DATE_FORMAT.parse("01.02.2025 15:00"), match.getDate());
    }

}