package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.season;

import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;

@SpringBootTest
public class FutureMatchesPageTests {

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
        WebDriver mockDriver = Mockito.spy(driver);
        WebElement mockElement = Mockito.mock(WebElement.class);

        Mockito.doReturn(Arrays.asList(mockElement)).when(mockDriver).findElements(FutureMatchesPage.MATCHES_SELECTOR);
        Mockito.doReturn(mockElement).when(mockDriver).findElement(FutureMatchesPage.MATCHES_SELECTOR);
        Mockito.when(mockElement.getAttribute("href")).thenReturn("https://www.flashscore.com/match/KEzgqXY5/#/match-summary");

        FutureMatchesPage page = new FutureMatchesPage(mockDriver, KEY);

        Collection<MatchKey> keys = page.getMatchesKeys();
        Assertions.assertNotNull(keys);
        Assertions.assertEquals(1, keys.size());
        Assertions.assertTrue(keys.contains(new MatchKey("KEzgqXY5")));
    }

    @Test
    public void shouldReturnDefaultMatchesKeysIfNotPresent() {
        FutureMatchesPage page = new FutureMatchesPage(driver, NON_EXISTENT_KEY);

        page.load();

        Collection<MatchKey> keys = page.getMatchesKeys();
        Assertions.assertNotNull(keys);
        Assertions.assertTrue(keys.isEmpty());
    }

}