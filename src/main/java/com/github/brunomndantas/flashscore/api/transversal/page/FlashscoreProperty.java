package com.github.brunomndantas.flashscore.api.transversal.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.function.Function;

public class FlashscoreProperty<V/*Value*/> {

    protected WebDriver driver;
    protected By selector;
    protected V defaultValue;
    protected Function<WebElement, V> valueFunc;


    public FlashscoreProperty(WebDriver driver, By selector, V defaultValue, Function<WebElement, V> valueFunc) {
        this.driver = driver;
        this.selector = selector;
        this.defaultValue = defaultValue;
        this.valueFunc = valueFunc;
    }


    public Collection<WebElement> getElements() {
        return driver.findElements(selector);
    }

    public WebElement getElement() {
        return driver.findElements(selector).isEmpty() ? null : driver.findElement(selector);
    }

    public Collection<V> getValues() {
        return getElements().stream().map(valueFunc).toList();
    }

    public V getValue() {
        WebElement element = getElement();
        return element == null ? defaultValue : valueFunc.apply(element);
    }

}