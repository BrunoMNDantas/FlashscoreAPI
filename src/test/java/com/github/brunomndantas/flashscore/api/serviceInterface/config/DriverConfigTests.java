package com.github.brunomndantas.flashscore.api.serviceInterface.config;

import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import com.github.brunomndantas.flashscore.api.transversal.driverSupplier.FlashscoreDriverSupplier;
import com.github.brunomndantas.jscrapper.core.driverSupplier.IDriverSupplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DriverConfigTests {

    @Autowired
    private IDriverSupplier driverSupplier;

    @Autowired
    private IDriverPool driverPool;


    @Test
    public void shouldCreateDriverSupplier() {
        Assertions.assertNotNull(driverSupplier);
    }

    @Test
    public void shouldCreateInstanceOfFlashscoreDriverSupplier() {
        Assertions.assertInstanceOf(FlashscoreDriverSupplier.class, driverSupplier);
    }

    @Test
    public void shouldCreateDriverPool() {
        Assertions.assertNotNull(driverPool);
    }

}
