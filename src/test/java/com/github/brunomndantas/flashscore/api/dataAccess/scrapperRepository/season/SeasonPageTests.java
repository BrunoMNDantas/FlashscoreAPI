package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.season;

import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SeasonPageTests {

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
    public void shouldReturnInitYearIfPresent() {
        SeasonPage page = new SeasonPage(driver, KEY);

        page.load();

        Assertions.assertEquals(2023, page.getInitYear());
    }

    @Test
    public void shouldReturnDefaultInitYearIfNotPresent() {
        SeasonPage page = new SeasonPage(driver, NON_EXISTENT_KEY);

        page.load();

        Assertions.assertEquals(-1, page.getInitYear());
    }

    @Test
    public void shouldReturnEndYearIfPresent() {
        SeasonPage page = new SeasonPage(driver, KEY);

        page.load();

        Assertions.assertEquals(2024, page.getEndYear());
    }

    @Test
    public void shouldReturnDefaultEndYearIfNotPresent() {
        SeasonPage page = new SeasonPage(driver, NON_EXISTENT_KEY);

        page.load();

        Assertions.assertEquals(-1, page.getEndYear());
    }

}