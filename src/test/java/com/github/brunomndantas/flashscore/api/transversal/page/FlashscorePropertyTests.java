package com.github.brunomndantas.flashscore.api.transversal.page;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

@SpringBootTest
public class FlashscorePropertyTests {

    @Autowired
    private DriverPool driverPool;

    private WebDriver driver;

    @BeforeEach
    public void init() throws Exception {
        this.driver = driverPool.getDriver();
        this.driver.get(FlashscoreURLs.FLASHSCORE_URL);
    }

    @AfterEach
    public void end() throws Exception {
        driverPool.returnDriver(driver);
    }


    @Test
    public void shouldReturnElementsIfPresent() {
        FlashscoreProperty<String> property = new FlashscoreProperty<>(driver, FlashscorePage.LOGO_SELECTOR, null, null);

        Collection<WebElement> elements = property.getElements();

        Assertions.assertNotNull(elements);
        Assertions.assertEquals(1, elements.size());
    }

    @Test
    public void shouldReturnEmptyIfElementsAreNotPresent() {
        FlashscoreProperty<String> property = new FlashscoreProperty<>(driver, FlashscorePage.UNKNOWN_PAGE_ERROR_SELECTOR, null, null);

        Collection<WebElement> elements = property.getElements();

        Assertions.assertNotNull(elements);
        Assertions.assertTrue(elements.isEmpty());
    }

    @Test
    public void shouldReturnElementIfPresent() {
        FlashscoreProperty<String> property = new FlashscoreProperty<>(driver, FlashscorePage.LOGO_SELECTOR, null, null);
        Assertions.assertNotNull(property.getElement());
    }

    @Test
    public void shouldReturnNullIfElementIsNotPresent() {
        FlashscoreProperty<String> property = new FlashscoreProperty<>(driver, FlashscorePage.UNKNOWN_PAGE_ERROR_SELECTOR, null, null);
        Assertions.assertNull(property.getElement());
    }

    @Test
    public void shouldReturnValuesIfPresent() {
        String value = "value";
        FlashscoreProperty<String> property = new FlashscoreProperty<>(driver, FlashscorePage.LOGO_SELECTOR, null, e -> value);

        Collection<String> values = property.getValues();
        Assertions.assertNotNull(values);
        Assertions.assertEquals(1, values.size());
        Assertions.assertSame(value, values.stream().findFirst().orElse(null));
    }

    @Test
    public void shouldReturnEmptyIfValuesAreNotPresent() {
        FlashscoreProperty<String> property = new FlashscoreProperty<>(driver, FlashscorePage.UNKNOWN_PAGE_ERROR_SELECTOR, null, e -> null);

        Collection<String> values = property.getValues();
        Assertions.assertNotNull(values);
        Assertions.assertTrue(values.isEmpty());
    }

    @Test
    public void shouldReturnValueIfElementIsPresent() {
        String value = "value";
        FlashscoreProperty<String> property = new FlashscoreProperty<>(driver, FlashscorePage.LOGO_SELECTOR, null, e -> value);
        Assertions.assertEquals(value, property.getValue());
    }

    @Test
    public void shouldReturnDefaultValueIfElementIsNotPresent() {
        String defaultValue = "value";
        FlashscoreProperty<String> property = new FlashscoreProperty<>(driver, FlashscorePage.UNKNOWN_PAGE_ERROR_SELECTOR, defaultValue, e -> null);
        Assertions.assertEquals(defaultValue, property.getValue());
    }

}