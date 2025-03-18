package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.season;

import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
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
public class PastMatchesPageTests {

    private static final SeasonKey KEY = new SeasonKey("hockey", "germany", "del", "2023-2024");
    private static final SeasonKey NON_EXISTENT_KEY = new SeasonKey("non", "existent", "season", "key");


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
    public void shouldReturnMatchesKeysIfPresent() {
        PastMatchesPage page = new PastMatchesPage(driver, KEY);

        page.load();

        Collection<MatchKey> keys = page.getMatchesKeys();
        Assertions.assertNotNull(keys);
        Assertions.assertTrue(keys.size() > 300);
        Assertions.assertTrue(keys.contains(new MatchKey("bZd70Ik6")));
    }

    @Test
    public void shouldReturnDefaultMatchesKeysIfNotPresent() {
        PastMatchesPage page = new PastMatchesPage(driver, NON_EXISTENT_KEY);

        page.load();

        Collection<MatchKey> keys = page.getMatchesKeys();
        Assertions.assertNotNull(keys);
        Assertions.assertTrue(keys.isEmpty());
    }

}