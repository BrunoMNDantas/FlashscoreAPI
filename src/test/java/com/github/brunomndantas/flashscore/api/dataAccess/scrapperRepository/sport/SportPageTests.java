package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.sport;

import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
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
public class SportPageTests {

    private static final SportKey KEY = new SportKey("beach-soccer");
    private static final SportKey NON_EXISTENT_KEY = new SportKey("non-existent-key");


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
    public void shouldReturnName() {
        SportPage page = new SportPage(driver, KEY);

        page.load();

        Assertions.assertEquals("Beach Soccer", page.getName());
    }

    @Test
    public void shouldReturnRegionsKeysIfPresent() {
        SportPage page = new SportPage(driver, KEY);

        page.load();

        Collection<RegionKey> keys = page.getRegionsKeys();
        Assertions.assertNotNull(keys);
        Assertions.assertTrue(keys.size() > 8);

        for(RegionKey regionKey : keys) {
            Assertions.assertEquals(KEY.getSportId(), regionKey.getSportId());
        }

        Assertions.assertTrue(keys.contains(new RegionKey(KEY.getSportId(), "portugal")));
    }

    @Test
    public void shouldReturnDefaultRegionsKeysIfNotPresent() {
        SportPage page = new SportPage(driver, NON_EXISTENT_KEY);

        page.load();

        Collection<RegionKey> keys = page.getRegionsKeys();
        Assertions.assertNotNull(keys);
        Assertions.assertTrue(keys.isEmpty());
    }

}