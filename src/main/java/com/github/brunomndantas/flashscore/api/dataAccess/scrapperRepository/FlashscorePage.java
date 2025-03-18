package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository;

import com.github.brunomndantas.flashscore.api.transversal.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collection;
import java.util.function.Function;

public class FlashscorePage {

    public static final By UNKNOWN_PAGE_ERROR_SELECTOR = By.xpath("//*[contains(text(), \"page can't be displayed\")]");
    public static final By LOGO_SELECTOR = By.xpath("//a[contains(@class, 'header__logo')]");


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
        return driver.findElements(UNKNOWN_PAGE_ERROR_SELECTOR).isEmpty();
    }

    public <V> V getValue(By selector, V defaultValue, Function<WebElement, V> valueFunc) {
        if(!driver.findElements(selector).isEmpty()) {
            WebElement element = driver.findElement(selector);
            return valueFunc.apply(element);
        }

        return defaultValue;
    }

    public <V> Collection<V> getValues(By selector, Function<WebElement, V> valueFunc) {
        return driver.findElements(selector).stream().map(valueFunc).toList();
    }

}