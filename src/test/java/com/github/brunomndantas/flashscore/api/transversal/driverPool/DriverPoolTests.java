package com.github.brunomndantas.flashscore.api.transversal.driverPool;

import com.github.brunomndantas.jscrapper.core.driverSupplier.DriverSupplierException;
import com.github.brunomndantas.jscrapper.core.driverSupplier.IDriverSupplier;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.factory.TaskFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest
public class DriverPoolTests {

    private static final IDriverSupplier DRIVER_SUPPLIER = DummyWebDriver::new;

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) { }
    }


    @Test
    public void shouldReturnNewDriver() throws Exception {
        try(DriverPool pool = new DriverPool(DRIVER_SUPPLIER, 2)) {
            WebDriver driver = pool.getDriver();

            Assertions.assertNotNull(driver);
            Assertions.assertInstanceOf(DummyWebDriver.class, driver);
        }
    }

    @Test
    public void shouldReuseDriver() throws Exception {
        try(DriverPool pool = new DriverPool(DRIVER_SUPPLIER, 2)) {
            WebDriver firstDriver = pool.getDriver();
            pool.returnDriver(firstDriver);

            WebDriver secondDriver = pool.getDriver();
            Assertions.assertSame(firstDriver, secondDriver);
        }
    }

    @Test
    public void shouldNotReuseDriver() throws Exception {
        try(DriverPool pool = new DriverPool(DRIVER_SUPPLIER, 2)) {
            WebDriver firstDriver = pool.getDriver();
            WebDriver secondDriver = pool.getDriver();

            Assertions.assertNotSame(firstDriver, secondDriver);
        }
    }

    @Test
    public void shouldWaitForDriver() throws Exception {
        try(DriverPool pool = new DriverPool(DRIVER_SUPPLIER, 1)) {
            WebDriver firstDriver = pool.getDriver();
            final AtomicReference<WebDriver> secondDriver = new AtomicReference<>();

            Task<?> task = TaskFactory.createAndStart(() -> secondDriver.set(pool.getDriver()));

            sleep(1000);
            Assertions.assertNull(secondDriver.get());

            pool.returnDriver(firstDriver);

            task.getFinishedEvent().await(1000);

            Assertions.assertSame(firstDriver, secondDriver.get());
        }
    }

    @Test
    public void shouldThrowExceptionIfWeTryToGetWhenItsClosed() {
        try(DriverPool pool = new DriverPool(DRIVER_SUPPLIER, 2)) {
            pool.close();

            Exception exception = Assertions.assertThrows(DriverPoolException.class, pool::getDriver);
            Assertions.assertTrue(exception.getMessage().contains("closed"));
        }
    }

    @Test
    public void shouldThrowExceptionIfDriverIsReturnedTwice() throws Exception {
        try(DriverPool pool = new DriverPool(DRIVER_SUPPLIER, 2)) {
            WebDriver driver = pool.getDriver();
            pool.returnDriver(driver);

            Exception exception = Assertions.assertThrows(DriverPoolException.class, () -> pool.returnDriver(driver));
            Assertions.assertTrue(exception.getMessage().contains("released"));
        }
    }

    @Test
    public void shouldThrowExceptionIfWeTryToReturnWhenItsClosed() throws Exception {
        try(DriverPool pool = new DriverPool(DRIVER_SUPPLIER, 2)) {
            WebDriver driver = pool.getDriver();
            pool.close();

            Exception exception = Assertions.assertThrows(DriverPoolException.class, () -> pool.returnDriver(driver));
            Assertions.assertTrue(exception.getMessage().contains("closed"));
        }
    }

    @Test
    public void shouldThrowExceptionIfWeReturnAnUnknownDriver() {
        try(DriverPool pool = new DriverPool(DRIVER_SUPPLIER, 2)) {
            WebDriver driver = new DummyWebDriver();

            Exception exception = Assertions.assertThrows(DriverPoolException.class, () -> pool.returnDriver(driver));
            Assertions.assertTrue(exception.getMessage().contains("doesn't belong"));
        }
    }

    @Test
    public void shouldWrapDriverSupplierException() {
        DriverSupplierException e = new DriverSupplierException("Error");
        IDriverSupplier supplier = () -> { throw e; };
        try(DriverPool pool = new DriverPool(supplier, 1)) {
            Exception exception = Assertions.assertThrows(DriverPoolException.class, pool::getDriver);
            Assertions.assertSame(exception.getCause(), e);
        }
    }

    @Test
    public void shouldWrapInterruptedException() throws Exception {
        try(DriverPool pool = new DriverPool(DRIVER_SUPPLIER, 1)) {
            WebDriver firstDriver = pool.getDriver();
            final AtomicReference<Exception> exceptionReference = new AtomicReference<>();

            Thread thread = new Thread(() -> {
                try {
                    pool.getDriver();
                } catch (Exception e) {
                    exceptionReference.set(e);
                }
            });

            thread.start();
            sleep(1000);
            thread.interrupt();
            sleep(1000);

            Assertions.assertNotNull(exceptionReference.get());
            Assertions.assertInstanceOf(InterruptedException.class, exceptionReference.get().getCause());
        }
    }

}
