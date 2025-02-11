package com.github.brunomndantas.flashscore.api.transversal.driverPool;

import com.github.brunomndantas.jscrapper.core.driverSupplier.DriverSupplierException;
import com.github.brunomndantas.jscrapper.core.driverSupplier.IDriverSupplier;
import org.openqa.selenium.WebDriver;

import java.util.Collection;
import java.util.LinkedList;

public class DriverPool implements IDriverPool {

    private final Object lock = new Object();
    private final Collection<WebDriver> drivers = new LinkedList<>();
    private final LinkedList<WebDriver> freeDrivers = new LinkedList<>();
    private boolean closed = false;

    private final IDriverSupplier driverSupplier;
    private final int maxInstances;


    public DriverPool(IDriverSupplier driverSupplier, int maxInstances) {
        this.driverSupplier = driverSupplier;
        this.maxInstances = maxInstances;
    }


    @Override
    public WebDriver getDriver() throws DriverPoolException {
        synchronized (lock) {
            while(true) {
                if(closed) {
                    throw new DriverPoolException("Driver is closed!");
                }

                if(!freeDrivers.isEmpty()) {
                    return getDriverFromPool();
                } else if(drivers.size() < maxInstances) {
                    return getNewDriver();
                } else {
                    await();
                }
            }
        }
    }

    private WebDriver getNewDriver() throws DriverPoolException {
        try {
            WebDriver driver = driverSupplier.get();
            drivers.add(driver);
            return driver;
        } catch (DriverSupplierException e) {
            throw new DriverPoolException("Error getting driver!", e);
        }
    }

    private WebDriver getDriverFromPool() {
        return freeDrivers.remove(0);
    }

    private void await() throws DriverPoolException {
        try {
            lock.wait();
        } catch (InterruptedException e) {
            throw new DriverPoolException("Wait was interrupted!", e);
        }
    }

    @Override
    public void returnDriver(WebDriver driver) throws DriverPoolException {
        synchronized (lock) {
            if(closed) {
                throw new DriverPoolException("Driver is closed!");
            }

            if(!drivers.contains(driver)) {
                throw new DriverPoolException("This driver doesn't belong to this pool!");
            }

            if(freeDrivers.contains(driver)) {
                throw new DriverPoolException("This driver was already released!");
            }

            freeDrivers.add(driver);
            lock.notifyAll();
        }
    }

    @Override
    public void close() {
        synchronized (lock) {
            for(WebDriver driver : drivers) {
                driver.quit();
            }

            closed = true;
            lock.notifyAll();
        }
    }

}