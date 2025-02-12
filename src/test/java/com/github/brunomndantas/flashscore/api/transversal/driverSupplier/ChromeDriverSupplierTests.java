package com.github.brunomndantas.flashscore.api.transversal.driverSupplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChromeDriverSupplierTests {

    @Value("${driver.path}")
    protected String driverPath;

    @Value("${driver.silent}")
    protected boolean driverSilent;

    @Value("${driver.headless}")
    protected boolean driverHeadless;


    @Test
    public void shouldReturnDriver() throws Exception {
        ChromeDriverSupplier driverSupplier = new ChromeDriverSupplier(driverPath, driverSilent, driverHeadless);

        WebDriver driver = driverSupplier.getDriver();

        try {
            Assertions.assertNotNull(driver);
            Assertions.assertInstanceOf(ChromeDriver.class, driver);
        } finally {
            driver.quit();
        }
    }

}