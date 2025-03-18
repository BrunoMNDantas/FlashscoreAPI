package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.competition;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

@SpringBootTest
public class CompetitionPageTests {

    private static final CompetitionKey KEY = new CompetitionKey("basketball", "europe", "euroleague");
    private static final CompetitionKey NON_EXISTENT_KEY = new CompetitionKey("non", "existent", "key");


    @Autowired
    private DriverPool driverPool;

    private WebDriver driver;


    @BeforeEach
    public void init() throws Exception {
        this.driver = driverPool.getDriver();
    }

    @AfterEach
    public void end() throws Exception {
        driverPool.returnDriver(driver);
    }


    @Test
    public void shouldReturnNameIfPresent() {
        CompetitionPage page = new CompetitionPage(driver, KEY);

        page.load();

        Assertions.assertEquals("Euroleague", page.getName());
    }

    @Test
    public void shouldReturnDefaultNameIfNotPresent() {
        CompetitionPage page = new CompetitionPage(driver, NON_EXISTENT_KEY);

        page.load();

        Assertions.assertNull(page.getName());
    }

    @Test
    public void shouldReturnSeasonsKeysIfPresent() {
        CompetitionPage page = new CompetitionPage(driver, KEY);

        page.load();

        Collection<SeasonKey> keys = page.getSeasonKeys();
        Assertions.assertNotNull(keys);
        Assertions.assertTrue(keys.size() > 20);

        for(SeasonKey seasonKey : keys) {
            Assertions.assertEquals(KEY.getSportId(), seasonKey.getSportId());
            Assertions.assertEquals(KEY.getRegionId(), seasonKey.getRegionId());
            Assertions.assertEquals(KEY.getCompetitionId(), seasonKey.getCompetitionId());
        }

        Assertions.assertTrue(keys.contains(new SeasonKey(KEY.getSportId(), KEY.getRegionId(), KEY.getCompetitionId(), "2018-2019")));
    }

    @Test
    public void shouldReturnDefaultSeasonsKeysIfNotPresent() {
        CompetitionPage page = new CompetitionPage(driver, NON_EXISTENT_KEY);

        page.load();

        Collection<SeasonKey> keys = page.getSeasonKeys();
        Assertions.assertNotNull(keys);
        Assertions.assertTrue(keys.isEmpty());
    }

}