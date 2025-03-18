package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.competition;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.ScrapperRepository;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.ScrapperRepositoryTests;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CompetitionScrapperRepositoryTests extends ScrapperRepositoryTests<CompetitionKey, Competition, CompetitionPage> {

    @Override
    protected ScrapperRepository<CompetitionKey, Competition, CompetitionPage> createRepository() {
        return createRepository(driverPool);
    }

    @Override
    protected ScrapperRepository<CompetitionKey, Competition, CompetitionPage> createRepository(IDriverPool driverPool) {
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
        ScrapperRepository<CompetitionKey, Competition, CompetitionPage> repository = createRepository(driverPool);

        Competition competition = repository.get(key);

        Assertions.assertNull(getConstraintViolation(competition));

        Assertions.assertEquals(key, competition.getKey());
        Assertions.assertEquals("Euroleague", competition.getName());
        Assertions.assertTrue(competition.getSeasonsKeys().size() > 20);
    }

}