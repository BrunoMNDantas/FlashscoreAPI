package com.github.brunomndantas.flashscore.api.serviceInterface.config;

import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPool;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import com.github.brunomndantas.flashscore.api.transversal.driverSupplier.ChromeDriverSupplier;
import com.github.brunomndantas.flashscore.api.transversal.driverSupplier.FlashscoreDriverSupplier;
import com.github.brunomndantas.jscrapper.core.driverSupplier.IDriverSupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverConfig {

    @Value("${driver.path}")
    protected String driverPath;

    @Value("${driver.silent}")
    protected boolean driverSilent;

    @Value("${driver.headless}")
    protected boolean driverHeadless;

    @Value("${maxdrivers}")
    protected int maxDrivers;


    @Bean
    public IDriverSupplier getDriverSupplier() {
        IDriverSupplier sourceSupplier = new ChromeDriverSupplier(driverPath, driverSilent, driverHeadless);
        return new FlashscoreDriverSupplier(sourceSupplier);
    }

    @Bean
    public IDriverPool getDriverPool(IDriverSupplier driverSupplier) {
        return new DriverPool(driverSupplier, maxDrivers);
    }


}
