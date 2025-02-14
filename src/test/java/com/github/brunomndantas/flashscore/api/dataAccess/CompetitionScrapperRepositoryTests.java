package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.apache.commons.lang3.math.NumberUtils;
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
        CompetitionKey key = new CompetitionKey("basketball", "usa", "nba");
        ScrapperRepository<CompetitionKey, Competition> repository = createRepository(DRIVER_POOL);

        Competition competition = repository.get(key);

        Assertions.assertEquals(key.getSportId(), competition.getKey().getSportId());
        Assertions.assertEquals(key.getRegionId(), competition.getKey().getRegionId());
        Assertions.assertEquals(key.getCompetitionId(), competition.getKey().getCompetitionId());
        Assertions.assertEquals("NBA", competition.getName());
        Assertions.assertNotNull(competition.getSeasonsKeys());
        Assertions.assertTrue(competition.getSeasonsKeys().size() > 6);

        for(SeasonKey seasonKey : competition.getSeasonsKeys()) {
            Assertions.assertNotNull(seasonKey);
            Assertions.assertEquals(key.getSportId(), seasonKey.getSportId());
            Assertions.assertEquals(key.getRegionId(), seasonKey.getRegionId());
            Assertions.assertEquals(key.getCompetitionId(), seasonKey.getCompetitionId());

            String id = seasonKey.getSeasonId();
            Assertions.assertNotNull(id);
            Assertions.assertFalse(id.trim().isEmpty());

            if(seasonKey.getSeasonId().contains("-")) {
                Assertions.assertTrue(NumberUtils.isDigits(id.split("-")[0]));
                Assertions.assertTrue(NumberUtils.isDigits(id.split("-")[1]));
            } else {
                Assertions.assertTrue(NumberUtils.isDigits(id));
            }
        }

        Collection<String> seasonsIds = competition.getSeasonsKeys().stream().map(SeasonKey::getSeasonId).toList();
        Assertions.assertTrue(seasonsIds.contains("2018-2019"));
    }

}