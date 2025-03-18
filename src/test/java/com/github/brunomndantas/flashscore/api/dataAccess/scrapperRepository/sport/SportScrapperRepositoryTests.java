package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.sport;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.ScrapperRepository;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.ScrapperRepositoryTests;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SportScrapperRepositoryTests extends ScrapperRepositoryTests<SportKey, Sport, SportPage> {

    @Override
    protected ScrapperRepository<SportKey, Sport, SportPage> createRepository() {
        return createRepository(driverPool);
    }

    @Override
    protected ScrapperRepository<SportKey, Sport, SportPage> createRepository(IDriverPool driverPool) {
        return new SportScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Override
    protected SportKey getExistentKey() {
        return new SportKey("football");
    }

    @Override
    protected SportKey getNonExistentKey() {
        return new SportKey("non_existent_key");
    }


    @Test
    public void shouldScrapData() throws Exception {
        SportKey key = new SportKey("beach-soccer");
        ScrapperRepository<SportKey,Sport,SportPage> repository = createRepository(driverPool);

        Sport sport = repository.get(key);

        Assertions.assertNull(getConstraintViolation(sport));

        Assertions.assertEquals(key, sport.getKey());
        Assertions.assertEquals("Beach Soccer", sport.getName());
        Assertions.assertTrue(sport.getRegionsKeys().size() > 8);
    }

}