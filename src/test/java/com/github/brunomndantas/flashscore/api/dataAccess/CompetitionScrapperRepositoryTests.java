package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

@SpringBootTest
public class CompetitionScrapperRepositoryTests extends ScrapperRepositoryTests<CompetitionKey, Competition> {

    @Override
    protected ScrapperRepository<CompetitionKey, Competition> createRepository() {
        return createRepository(DRIVER_POOL);
    }

    @Override
    protected ScrapperRepository<CompetitionKey, Competition> createRepository(IDriverPool driverPool) {
        return new CompetitionScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Override
    protected CompetitionKey getExistentKey() {
        return new CompetitionKey("football", "portugal", "liga-portugal");
    }

    @Override
    protected CompetitionKey getNonExistentKey() {
        return new CompetitionKey("football", "portugal", "non-existent-id");
    }


    @Test
    public void shouldScrapData() throws Exception {
        CompetitionKey key = new CompetitionKey("basketball", "europe", "euroleague");
        ScrapperRepository<CompetitionKey, Competition> repository = createRepository(DRIVER_POOL);

        Competition competition = repository.get(key);

        Assertions.assertNull(getConstraintViolation(competition));

        Assertions.assertEquals(key, competition.getKey());
        Assertions.assertEquals("Euroleague", competition.getName());
        Assertions.assertTrue(competition.getSeasonsKeys().size() > 20);

        for(SeasonKey seasonKey : competition.getSeasonsKeys()) {
            Assertions.assertEquals(key.getSportId(), seasonKey.getSportId());
            Assertions.assertEquals(key.getRegionId(), seasonKey.getRegionId());
            Assertions.assertEquals(key.getCompetitionId(), seasonKey.getCompetitionId());
        }

        Collection<String> seasonsIds = competition.getSeasonsKeys().stream().map(SeasonKey::getSeasonId).toList();
        Assertions.assertTrue(seasonsIds.contains("2018-2019"));
    }

}