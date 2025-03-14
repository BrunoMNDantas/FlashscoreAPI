package com.github.brunomndantas.flashscore.api.config;

import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPool;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import com.github.brunomndantas.flashscore.api.transversal.driverSupplier.FlashscoreDriverSupplier;
import com.github.brunomndantas.jscrapper.core.driverSupplier.IDriverSupplier;
import com.github.brunomndantas.jscrapper.support.driverSupplier.ChromeDriverSupplier;
import jakarta.annotation.PreDestroy;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class GlobalTestConfig {

    @Value("${driver.path}")
    protected String driverPath;
    @Value("${driver.silent}")
    protected boolean driverSilent;
    @Value("${driver.headless}")
    protected boolean driverHeadless;

    protected IDriverPool driverPool;
    protected WebDriver driver;


    @Bean
    @Primary
    public IDriverPool getPool() {
        if(driverPool == null) {
            IDriverSupplier sourceDriverSupplier = new ChromeDriverSupplier(driverPath, driverSilent, driverHeadless);
            IDriverSupplier driverSupplier = new FlashscoreDriverSupplier(sourceDriverSupplier);
            driverPool = new DriverPool(driverSupplier, 1);
        }

        return driverPool;
    }

    @Bean
    public WebDriver getDriver() throws Exception {
        if(driver == null) {
            IDriverSupplier sourceDriverSupplier = new ChromeDriverSupplier(driverPath, driverSilent, driverHeadless);
            IDriverSupplier driverSupplier = new FlashscoreDriverSupplier(sourceDriverSupplier);
            driver = driverSupplier.get();
        }

        return driver;
    }

    @PreDestroy
    public void dispose() throws Exception {
        driverPool.close();
        driver.close();
    }

}