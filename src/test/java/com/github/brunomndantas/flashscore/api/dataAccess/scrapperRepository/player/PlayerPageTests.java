package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.player;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
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

import java.text.ParseException;
import java.util.Arrays;

@SpringBootTest
public class PlayerPageTests {

    private static final PlayerKey KEY = new PlayerKey("gyokeres-viktor", "zaBZ1xIk");
    private static final PlayerKey NON_EXISTENT_KEY = new PlayerKey("non-existent", "key");


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
        PlayerPage page = new PlayerPage(driver, KEY);

        page.load();

        Assertions.assertEquals("Viktor Gyokeres", page.getName());
    }

    @Test
    public void shouldReturnDefaultNameIfNotPresent() {
        PlayerPage page = new PlayerPage(driver, NON_EXISTENT_KEY);

        page.load();

        Assertions.assertNull(page.getName());
    }

    @Test
    public void shouldReturnBirthDateIfPresent() throws Exception {
        PlayerPage page = new PlayerPage(driver, KEY);

        page.load();

        Assertions.assertEquals(PlayerPage.DATE_FORMAT.parse("04.06.1998"), page.getBirthDate());
    }

    @Test
    public void shouldReturnDefaultBirthDateIfNotPresent() {
        PlayerPage page = new PlayerPage(driver, NON_EXISTENT_KEY);

        page.load();

        Assertions.assertNull(page.getBirthDate());
    }

    @Test
    public void shouldWrapBirthDateParsingException() {
        WebDriver mockDriver = Mockito.spy(driver);
        WebElement mockElement = Mockito.mock(WebElement.class);

        Mockito.doReturn(Arrays.asList(mockElement)).when(mockDriver).findElements(PlayerPage.BIRTHDATE_SELECTOR);
        Mockito.doReturn(mockElement).when(mockDriver).findElement(PlayerPage.BIRTHDATE_SELECTOR);
        Mockito.when(mockElement.getText()).thenReturn("invalid date");

        PlayerPage page = new PlayerPage(mockDriver, KEY);

        page.load();

        Exception exception = Assertions.assertThrows(RuntimeException.class, page::getBirthDate);
        Assertions.assertNotNull(exception.getCause(), "Exception cause should not be null");
        Assertions.assertInstanceOf(ParseException.class, exception.getCause());
    }

    @Test
    public void shouldReturnRoleIfPresent() {
        PlayerPage page = new PlayerPage(driver, KEY);

        page.load();

        Assertions.assertEquals("Forward", page.getRole());
    }

    @Test
    public void shouldReturnDefaultRoleIfNotPresent() {
        PlayerPage page = new PlayerPage(driver, NON_EXISTENT_KEY);

        page.load();

        Assertions.assertNull(page.getRole());
    }

}