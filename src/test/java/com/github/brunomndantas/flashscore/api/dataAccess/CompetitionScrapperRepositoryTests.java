package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionId;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonId;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

@SpringBootTest
public class CompetitionScrapperRepositoryTests extends ScrapperRepositoryTests<CompetitionId, Competition> {

    @Override
    protected ScrapperRepository<CompetitionId, Competition> createRepository() {
        return createRepository(driverPool);
    }

    @Override
    protected ScrapperRepository<CompetitionId, Competition> createRepository(IDriverPool driverPool) {
        return new CompetitionScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Override
    protected CompetitionId getExistentKey() {
        return new CompetitionId("football", "portugal", "liga-portugal");
    }

    @Override
    protected CompetitionId getNonExistentKey() {
        return new CompetitionId("football", "portugal", "non-existent-id");
    }


    @Test
    public void shouldScrapData() throws Exception {
        CompetitionId key = new CompetitionId("basketball", "usa", "nba");
        ScrapperRepository<CompetitionId, Competition> repository = createRepository(driverPool);

        Competition competition = repository.get(key);

        Assertions.assertEquals(key.getSportId(), competition.getId().getSportId());
        Assertions.assertEquals(key.getRegionId(), competition.getId().getRegionId());
        Assertions.assertEquals(key.getId(), competition.getId().getId());
        Assertions.assertEquals("NBA", competition.getName());
        Assertions.assertNotNull(competition.getSeasonsIds());
        Assertions.assertTrue(competition.getSeasonsIds().size() > 6);

        for(SeasonId seasonId: competition.getSeasonsIds()) {
            Assertions.assertNotNull(seasonId);
            Assertions.assertEquals(key.getSportId(), seasonId.getSportId());
            Assertions.assertEquals(key.getRegionId(), seasonId.getRegionId());
            Assertions.assertEquals(key.getId(), seasonId.getCompetitionId());

            String id = seasonId.getId();
            Assertions.assertNotNull(id);
            Assertions.assertFalse(id.trim().isEmpty());

            if(seasonId.getId().contains("-")) {
                Assertions.assertTrue(NumberUtils.isDigits(id.split("-")[0]));
                Assertions.assertTrue(NumberUtils.isDigits(id.split("-")[1]));
            } else {
                Assertions.assertTrue(NumberUtils.isDigits(id));
            }
        }

        Collection<String> seasonsIds = competition.getSeasonsIds().stream().map(SeasonId::getId).toList();
        Assertions.assertTrue(seasonsIds.contains("2018-2019"));
    }

}