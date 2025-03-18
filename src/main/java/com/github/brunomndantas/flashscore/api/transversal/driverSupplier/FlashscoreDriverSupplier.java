package com.github.brunomndantas.flashscore.api.transversal.driverSupplier;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.transversal.Config;
import com.github.brunomndantas.jscrapper.core.driverSupplier.DriverSupplierException;
import com.github.brunomndantas.jscrapper.core.driverSupplier.IDriverSupplier;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FlashscoreDriverSupplier implements IDriverSupplier {

    public static final By ACCEPT_TERMS_BUTTON_SELECTOR = By.xpath("//*[@id='onetrust-accept-btn-handler']");


    protected IDriverSupplier sourceSupplier;


    public FlashscoreDriverSupplier(IDriverSupplier sourceSupplier) {
        this.sourceSupplier = sourceSupplier;
    }


    @Override
    public WebDriver get() throws DriverSupplierException {
        WebDriver driver = sourceSupplier.get();

        driver.manage().window().maximize();

        driver.get(FlashscoreURLs.FLASHSCORE_URL);

        acceptTerms(driver);

        return driver;
    }

    protected void acceptTerms(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(Config.MEDIUM_WAIT).getSeconds());

        WebElement acceptTermsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(ACCEPT_TERMS_BUTTON_SELECTOR));
        acceptTermsButton.click();

        wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfElementLocated(ACCEPT_TERMS_BUTTON_SELECTOR)));
    }

}
