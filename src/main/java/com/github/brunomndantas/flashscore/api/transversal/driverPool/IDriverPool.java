package com.github.brunomndantas.flashscore.api.transversal.driverPool;

import org.openqa.selenium.WebDriver;

public interface IDriverPool extends AutoCloseable {

    WebDriver getDriver() throws DriverPoolException;

    void returnDriver(WebDriver driver) throws DriverPoolException;

}
