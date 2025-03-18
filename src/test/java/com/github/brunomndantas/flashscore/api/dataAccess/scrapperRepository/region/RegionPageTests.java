package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.region;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
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
public class RegionPageTests {

    private static final RegionKey KEY = new RegionKey("handball", "spain");
    private static final RegionKey NON_EXISTENT_KEY = new RegionKey("non-existent", "key");


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
        RegionPage page = new RegionPage(driver, KEY);

        page.load();

        Assertions.assertEquals("Spain", page.getName());
    }

    @Test
    public void shouldReturnDefaultNameIfNotPresent() {
        RegionPage page = new RegionPage(driver, NON_EXISTENT_KEY);

        page.load();

        Assertions.assertNull(page.getName());
    }

    @Test
    public void shouldReturnCompetitionsKeysIfPresent() {
        RegionPage page = new RegionPage(driver, KEY);

        page.load();

        Collection<CompetitionKey> keys = page.getCompetitionsKeys();
        Assertions.assertNotNull(keys);
        Assertions.assertTrue(keys.size() > 6);

        for(CompetitionKey competitionKey : keys) {
            Assertions.assertEquals(KEY.getSportId(), competitionKey.getSportId());
            Assertions.assertEquals(KEY.getRegionId(), competitionKey.getRegionId());
        }

        Assertions.assertTrue(keys.contains(new CompetitionKey(KEY.getSportId(), KEY.getRegionId(), "liga-asobal")));
    }

    @Test
    public void shouldReturnDefaultCompetitionsKeysIfNotPresent() {
        RegionPage page = new RegionPage(driver, NON_EXISTENT_KEY);

        page.load();

        Collection<CompetitionKey> keys = page.getCompetitionsKeys();
        Assertions.assertNotNull(keys);
        Assertions.assertTrue(keys.isEmpty());
    }

}