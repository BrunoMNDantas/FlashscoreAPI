package com.github.brunomndantas.flashscore.api.transversal.driverSupplier;

import com.github.brunomndantas.flashscore.api.transversal.Config;
import com.github.brunomndantas.jscrapper.core.driverSupplier.DriverSupplierException;
import com.github.brunomndantas.jscrapper.core.driverSupplier.IDriverSupplier;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.github.brunomndantas.flashscore.api.dataAccess.FlashscoreConstants.ACCEPT_TERMS_BUTTON_SELECTOR;
import static com.github.brunomndantas.flashscore.api.dataAccess.FlashscoreConstants.FLASHSCORE_URL;

public class FlashscoreDriverSupplier implements IDriverSupplier {

    protected IDriverSupplier sourceSupplier;


    public FlashscoreDriverSupplier(IDriverSupplier sourceSupplier) {
        this.sourceSupplier = sourceSupplier;
    }


    @Override
    public WebDriver get() throws DriverSupplierException {
        WebDriver driver = sourceSupplier.get();

        driver.manage().window().maximize();

        driver.get(FLASHSCORE_URL);

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
