package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.team;

import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TeamPageTests {

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
    public void shouldReturnNameIfPresent() {
        TeamPage page = new TeamPage(driver, KEY);

        page.load();

        Assertions.assertEquals("Dortmund II", page.getName());
    }

    @Test
    public void shouldReturnDefaultNameIfNotPresent() {
        TeamPage page = new TeamPage(driver, NON_EXISTENT_KEY);

        page.load();

        Assertions.assertNull(page.getName());
    }

    @Test
    public void shouldReturnStadiumIfPresent() {
        TeamPage page = new TeamPage(driver, KEY);

        page.load();

        Assertions.assertEquals("Stadion Rote Erde (Dortmund)", page.getStadium());
    }

    @Test
    public void shouldReturnDefaultStadiumIfNotPresent() {
        TeamPage page = new TeamPage(driver, NON_EXISTENT_KEY);

        page.load();

        Assertions.assertNull(page.getStadium());
    }

    @Test
    public void shouldReturnStadiumCapacityIfPresent() {
        TeamPage page = new TeamPage(driver, KEY);

        page.load();

        Assertions.assertEquals(25000, page.getStadiumCapacity());
    }

    @Test
    public void shouldReturnDefaultStadiumCapacityIfNotPresent() {
        TeamPage page = new TeamPage(driver, NON_EXISTENT_KEY);

        page.load();

        Assertions.assertEquals(-1, page.getStadiumCapacity());
    }

}