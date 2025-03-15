package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository;

import com.github.brunomndantas.flashscore.api.config.GlobalTestConfig;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.transversal.Config;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(GlobalTestConfig.class)
public class FlashscorePageTests {

    @Autowired
    protected IDriverPool driverPool;

    protected WebDriver driver;


    @BeforeEach
    public void init() throws Exception {
        driver = driverPool.getDriver();
    }

    @AfterEach
    public void finish() throws Exception {
        driverPool.returnDriver(driver);
    }


    @Test
    public void shouldWaitForConditionReceivedOnConstructor() {
        By selector = By.xpath("//a[@href=\"/basketball/\"]");
        ExpectedCondition<?> condition = ExpectedConditions.visibilityOfElementLocated(selector);
        FlashscorePage page = new FlashscorePage(driver, FlashscoreURLs.FLASHSCORE_URL, condition);

        page.load();

        Assertions.assertFalse(driver.findElements(selector).isEmpty());
    }

    @Test
    public void shouldWaitForElementReceivedOnConstructor() {
        By selector = By.xpath("//a[@href=\"/basketball/\"]");
        FlashscorePage page = new FlashscorePage(driver, FlashscoreURLs.FLASHSCORE_URL, selector);

        page.load();

        Assertions.assertFalse(driver.findElements(selector).isEmpty());
    }

    @Test
    public void shouldWaitForLogoElement() {
        FlashscorePage page = new FlashscorePage(driver, FlashscoreURLs.FLASHSCORE_URL);

        page.load();

        Assertions.assertFalse(driver.findElements(FlashscorePage.LOGO_SELECTOR).isEmpty());
    }
    
    @Test
    public void shouldWaitExistentPageToBeLoaded() {
        FlashscorePage page = new FlashscorePage(driver, FlashscoreURLs.FLASHSCORE_URL);

        long timeBefore = System.currentTimeMillis();
        page.load();
        long timeAfter = System.currentTimeMillis();

        Assertions.assertTrue(timeAfter-timeBefore < Config.MEDIUM_WAIT);
        Assertions.assertFalse(driver.findElements(FlashscorePage.LOGO_SELECTOR).isEmpty());
    }

    @Test
    public void shouldWaitNonExistentPageToBeLoaded() {
        FlashscorePage page = new FlashscorePage(driver, FlashscoreURLs.FLASHSCORE_URL + "/nonExistent");

        long timeBefore = System.currentTimeMillis();
        page.load();
        long timeAfter = System.currentTimeMillis();

        Assertions.assertTrue(timeAfter-timeBefore < Config.MEDIUM_WAIT);
        Assertions.assertFalse(driver.findElements(FlashscorePage.UNKNOWN_PAGE_ERROR_SELECTOR).isEmpty());
    }

    @Test
    public void shouldDetectPageExists() {
        FlashscorePage page = new FlashscorePage(driver, FlashscoreURLs.FLASHSCORE_URL);

        page.load();

        Assertions.assertTrue(page.exists());
    }

    @Test
    public void shouldDetectPageDoesntExist() {
        FlashscorePage page = new FlashscorePage(driver, FlashscoreURLs.FLASHSCORE_URL + "/nonExistent");

        page.load();

        Assertions.assertFalse(page.exists());
    }

}