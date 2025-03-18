package com.github.brunomndantas.flashscore.api.transversal.driverSupplier;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.jscrapper.support.driverSupplier.ChromeDriverSupplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FlashoscoreDriverSupplierTests {

    @Value("${driver.path}")
    protected String driverPath;
    @Value("${driver.silent}")
    protected boolean driverSilent;
    @Value("${driver.headless}")
    protected boolean driverHeadless;


    @Test
    public void shouldPointToFlashscore() throws Exception {
        ChromeDriverSupplier sourceSupplier = new ChromeDriverSupplier(driverPath, driverSilent, driverHeadless);
        FlashscoreDriverSupplier driverSupplier = new FlashscoreDriverSupplier(sourceSupplier);
        WebDriver driver = driverSupplier.get();

        try{
            String url = driver.getCurrentUrl();
            Assertions.assertTrue(url.startsWith(FlashscoreURLs.FLASHSCORE_URL));
        } finally {
            driver.quit();
        }
    }

    @Test
    public void shouldAcceptTerms() throws Exception {
        ChromeDriverSupplier sourceSupplier = new ChromeDriverSupplier(driverPath, driverSilent, driverHeadless);
        FlashscoreDriverSupplier driverSupplier = new FlashscoreDriverSupplier(sourceSupplier);
        WebDriver driver = driverSupplier.get();

        try{
            WebElement acceptTermsButton = driver.findElement(FlashscoreDriverSupplier.ACCEPT_TERMS_BUTTON_SELECTOR);
            Assertions.assertFalse(acceptTermsButton.isDisplayed());
        } finally {
            driver.quit();
        }
    }

    @Test
    public void shouldMaximizeWindow() throws Exception {
        ChromeDriverSupplier sourceSupplier = new ChromeDriverSupplier(driverPath, driverSilent, driverHeadless);
        FlashscoreDriverSupplier driverSupplier = new FlashscoreDriverSupplier(sourceSupplier);
        WebDriver driver = driverSupplier.get();

        try{
            Dimension initialSize = driver.manage().window().getSize();

            driver.manage().window().maximize();
            Dimension maximizedSize = driver.manage().window().getSize();

            Assertions.assertEquals(maximizedSize.getWidth(), initialSize.getWidth());
            Assertions.assertEquals(maximizedSize.getHeight(), initialSize.getHeight());
        } finally {
            driver.quit();
        }
    }

}
