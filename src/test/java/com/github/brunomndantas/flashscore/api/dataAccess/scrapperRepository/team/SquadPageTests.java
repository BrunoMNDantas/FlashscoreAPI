package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.team;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
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
public class SquadPageTests {

    private static final TeamKey KEY = new TeamKey("dortmund", "vVcwNP6f");
    private static final TeamKey NON_EXISTENT_KEY = new TeamKey("non-existent", "key");


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
    public void shouldReturnCoachKeyIfPresent() {
        SquadPage page = new SquadPage(driver, KEY);

        page.load();

        Assertions.assertEquals(new PlayerKey("zimmermann-jan", "2F8lpifB"), page.getCoachKey());
    }

    @Test
    public void shouldReturnDefaultCoachKeyIfNotPresent() {
        SquadPage page = new SquadPage(driver, NON_EXISTENT_KEY);

        page.load();

        Assertions.assertNull(page.getCoachKey());
    }

    @Test
    public void shouldReturnPlayersKeysIfPresents() {
        SquadPage page = new SquadPage(driver, KEY);

        page.load();

        Collection<PlayerKey> keys = page.getPlayersKeys();
        Assertions.assertNotNull(keys);
        Assertions.assertTrue(keys.size() > 20);
        Assertions.assertTrue(keys.size() < 35);
        Assertions.assertFalse(keys.contains(new PlayerKey("zimmermann-jan", "2F8lpifB")));
    }

    @Test
    public void shouldReturnDefaultPlayersKeysIfNotPresent() {
        SquadPage page = new SquadPage(driver, NON_EXISTENT_KEY);

        page.load();

        Collection<PlayerKey> keys = page.getPlayersKeys();
        Assertions.assertNotNull(keys);
        Assertions.assertTrue(keys.isEmpty());
    }

}