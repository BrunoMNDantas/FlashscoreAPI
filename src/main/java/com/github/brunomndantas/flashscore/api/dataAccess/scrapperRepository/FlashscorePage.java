package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository;

import com.github.brunomndantas.flashscore.api.transversal.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FlashscorePage {

    public static final By UNKNOWN_PAGE_ERROR_SELECTOR = By.xpath("//*[contains(text(), \"page can't be displayed\")]");
    public static final By LOGO_SELECTOR = By.xpath("//*[contains(@class, 'header__logo')]");


    protected WebDriver driver;
    protected String url;
    protected ExpectedCondition<?> loadedCondition;


    public FlashscorePage(WebDriver driver, String url, ExpectedCondition<?> loadedCondition) {
        this.driver = driver;
        this.url = url;
        this.loadedCondition = loadedCondition;
    }

    public FlashscorePage(WebDriver driver, String url, By loadElementSelector) {
        this(driver, url, ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(loadElementSelector),
                ExpectedConditions.visibilityOfElementLocated(UNKNOWN_PAGE_ERROR_SELECTOR)
        ));
    }

    public FlashscorePage(WebDriver driver, String url) {
        this(driver, url, LOGO_SELECTOR);
    }


    public void load() {
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(Config.MEDIUM_WAIT).getSeconds());
        wait.until(loadedCondition);
    }

    public boolean exists() {
        return !existsElement(UNKNOWN_PAGE_ERROR_SELECTOR);
    }

    protected boolean existsElement(By selector) {
        return !driver.findElements(selector).isEmpty();
    }

}
