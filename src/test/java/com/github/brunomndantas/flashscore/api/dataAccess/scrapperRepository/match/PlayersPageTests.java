package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.match;

import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
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
public class PlayersPageTests {

    private static final MatchKey KEY = new MatchKey("vekYdzTa");
    private static final MatchKey NON_EXISTENT_KEY = new MatchKey("nonExistentKey");


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
    public void shouldReturnHomeCoachKeyIfPresent() {
        PlayersPage page = new PlayersPage(driver, KEY);

        page.load();

        Assertions.assertEquals(new PlayerKey("caldwell-gary", "prPkSBdq"), page.getHomeCoachKey());
    }

    @Test
    public void shouldReturnDefaultHomeCoachKeyIfNotPresent() {
        PlayersPage page = new PlayersPage(driver, NON_EXISTENT_KEY);

        page.load();

        Assertions.assertNull(page.getHomeCoachKey());
    }

    @Test
    public void shouldReturnAwayCoachKeyIfPresent() {
        PlayersPage page = new PlayersPage(driver, KEY);

        page.load();

        Assertions.assertEquals(new PlayerKey("espirito-santo", "IoJ2QpOs"), page.getAwayCoachKey());
    }

    @Test
    public void shouldReturnDefaultAwayCoachKeyIfNotPresent() {
        PlayersPage page = new PlayersPage(driver, NON_EXISTENT_KEY);

        page.load();

        Assertions.assertNull(page.getAwayCoachKey());
    }

    @Test
    public void shouldReturnHomeLineupPlayersKeysIfPresent() {
        PlayersPage page = new PlayersPage(driver, KEY);

        page.load();

        Collection<PlayerKey> keys = page.getHomeLineupPlayersKeys();
        Assertions.assertNotNull(keys);
        Assertions.assertEquals(11, keys.size());
        Assertions.assertTrue(keys.contains(new PlayerKey("turns-ed", "Kraw6tR1")));
    }

    @Test
    public void shouldReturnDefaultHomeLineupPlayersKeysIfNotPresent() {
        PlayersPage page = new PlayersPage(driver, NON_EXISTENT_KEY);

        page.load();

        Collection<PlayerKey> keys = page.getHomeLineupPlayersKeys();
        Assertions.assertNotNull(keys);
        Assertions.assertTrue(keys.isEmpty());
    }

    @Test
    public void shouldReturnAwayLineupPlayersKeysIfPresent() {
        PlayersPage page = new PlayersPage(driver, KEY);

        page.load();

        Collection<PlayerKey> keys = page.getAwayLineupPlayersKeys();
        Assertions.assertNotNull(keys);
        Assertions.assertEquals(11, keys.size());
        Assertions.assertTrue(keys.contains(new PlayerKey("jota-ii", "MklkHcIh")));
    }

    @Test
    public void shouldReturnDefaultAwayLineupPlayersKeysIfNotPresent() {
        PlayersPage page = new PlayersPage(driver, NON_EXISTENT_KEY);

        page.load();

        Collection<PlayerKey> keys = page.getAwayLineupPlayersKeys();
        Assertions.assertNotNull(keys);
        Assertions.assertTrue(keys.isEmpty());
    }

    @Test
    public void shouldReturnHomeBenchPlayersKeysIfPresent() {
        PlayersPage page = new PlayersPage(driver, KEY);

        page.load();

        Collection<PlayerKey> keys = page.getHomeBenchPlayers();
        Assertions.assertNotNull(keys);
        Assertions.assertEquals(9, keys.size());
        Assertions.assertTrue(keys.contains(new PlayerKey("cole-reece", "WCqq6BwA")));
    }

    @Test
    public void shouldReturnDefaultHomeBenchPlayersKeysIfNotPresent() {
        PlayersPage page = new PlayersPage(driver, NON_EXISTENT_KEY);

        page.load();

        Collection<PlayerKey> keys = page.getHomeBenchPlayers();
        Assertions.assertNotNull(keys);
        Assertions.assertTrue(keys.isEmpty());
    }

    @Test
    public void shouldReturnAwayBenchPlayersKeysIfPresent() {
        PlayersPage page = new PlayersPage(driver, KEY);

        page.load();

        Collection<PlayerKey> keys = page.getAwayBenchPlayers();
        Assertions.assertNotNull(keys);
        Assertions.assertEquals(9, keys.size());
        Assertions.assertTrue(keys.contains(new PlayerKey("morato", "M1x2dIqA")));
    }

    @Test
    public void shouldReturnDefaultAwayBenchPlayersKeysIfNotPresent() {
        PlayersPage page = new PlayersPage(driver, NON_EXISTENT_KEY);

        page.load();

        Collection<PlayerKey> keys = page.getAwayBenchPlayers();
        Assertions.assertNotNull(keys);
        Assertions.assertTrue(keys.isEmpty());
    }

}