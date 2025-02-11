package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.Config;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPool;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPoolException;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import com.github.brunomndantas.flashscore.api.transversal.driverSupplier.FlashscoreDriverSupplier;
import com.github.brunomndantas.jscrapper.core.driverSupplier.IDriverSupplier;
import com.github.brunomndantas.jscrapper.support.driverSupplier.ChromeDriverSupplier;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.io.File;

public abstract class ScrapperRepositoryTests<K,E> {

    protected static final String SCREEN_SHOOTS_DIRECTORY = "./screenshoots";
    protected static final IDriverSupplier SOURCE_DRIVER_SUPPLIER = new ChromeDriverSupplier(Config.DRIVER_PATH, Config.DRIVER_SILENT_MODE, Config.DRIVER_HEADLESS_MODE);
    protected static final IDriverSupplier DRIVER_SUPPLIER = new FlashscoreDriverSupplier(SOURCE_DRIVER_SUPPLIER);


    @Test
    public void shouldThrowUnsupportedOperationOnGetAll() {
        ScrapperRepository<K,E> repository = createRepository();
        Assertions.assertThrows(UnsupportedOperationException.class, repository::getAll);
    }

    @Test
    public void shouldThrowUnsupportedOperationOnInsert() {
        ScrapperRepository<K,E> repository = createRepository();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> repository.insert(null));
    }

    @Test
    public void shouldThrowUnsupportedOperationOnUpdate() {
        ScrapperRepository<K,E> repository = createRepository();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> repository.update(null));
    }

    @Test
    public void shouldThrowUnsupportedOperationOnDelete() {
        ScrapperRepository<K,E> repository = createRepository();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> repository.delete(null));
    }

    @Test
    public void shouldReturnDriverToPool() throws Exception {
        WebDriver driver = new ChromeDriverSupplier(Config.DRIVER_PATH, Config.DRIVER_SILENT_MODE, Config.DRIVER_HEADLESS_MODE).getDriver();
        IDriverSupplier driverSupplier = new FlashscoreDriverSupplier(() -> driver);
        IDriverPool driverPool = new DriverPool(driverSupplier, 1);

        try {
            ScrapperRepository<K,E> repository = createRepository(driverPool);
            repository.get(getExistentKey());
            Assertions.assertSame(driver, driverPool.getDriver());
        } finally {
            driverPool.close();
        }
    }

    @Test
    public void shouldWrapExceptionGettingDriverFromPool() throws Exception {
        DriverPoolException exception = new DriverPoolException();
        WebDriver driver = new ChromeDriverSupplier(Config.DRIVER_PATH, Config.DRIVER_SILENT_MODE, Config.DRIVER_HEADLESS_MODE).getDriver();
        IDriverSupplier driverSupplier = new FlashscoreDriverSupplier(() -> driver);
        IDriverPool driverPool = new DriverPool(driverSupplier, 1) {
            @Override
            public WebDriver getDriver() throws DriverPoolException {
                throw exception;
            }
        };

        try {
            ScrapperRepository<K,E> repository = createRepository(driverPool);
            Exception returnedException = Assertions.assertThrows(RepositoryException.class, () -> repository.get(getExistentKey()));
            Assertions.assertSame(exception, returnedException.getCause());
        } finally {
            driverPool.close();
        }
    }

    @Test
    public void shouldWrapExceptionReturningDriverToPool() throws Exception {
        DriverPoolException exception = new DriverPoolException();
        WebDriver driver = new ChromeDriverSupplier(Config.DRIVER_PATH, Config.DRIVER_SILENT_MODE, Config.DRIVER_HEADLESS_MODE).getDriver();
        IDriverSupplier driverSupplier = new FlashscoreDriverSupplier(() -> driver);
        IDriverPool driverPool = new DriverPool(driverSupplier, 1) {
            @Override
            public void returnDriver(WebDriver driver) throws DriverPoolException {
                throw exception;
            }
        };

        try {
            ScrapperRepository<K,E> repository = createRepository(driverPool);
            Exception returnedException = Assertions.assertThrows(RepositoryException.class, () -> repository.get(getExistentKey()));
            Assertions.assertSame(exception, returnedException.getCause());
        } finally {
            driverPool.close();
        }
    }

    @Test
    public void shouldReturnNull() throws Exception {
        K key = getNonExistentKey();
        ScrapperRepository<K,E> repository = createRepository();
        Assertions.assertNull(repository.get(key));
    }

    @Test
    public void shouldReturnEntity() throws Exception {
        K key = getExistentKey();
        ScrapperRepository<K,E> repository = createRepository();
        Assertions.assertNotNull(repository.get(key));
    }

    @Test
    public void shouldSaveScreenShoot() throws Exception {
        IDriverPool driverPool = new DriverPool(DRIVER_SUPPLIER, 1);
        ScrapperRepository<K,E> repository = new ScrapperRepository<>(driverPool, SCREEN_SHOOTS_DIRECTORY) {

            @Override
            protected boolean loadDriver(WebDriver driver, K key) {
                throw new IllegalArgumentException("Forced Error!");
            }

            @Override
            protected String getUrlOfEntity(K key) {
                return "https://www.google.com";
            }

            @Override
            protected E scrapEntity(WebDriver driver, K key) {
                return null;
            }

        };

        try {
            File[] files = new File(SCREEN_SHOOTS_DIRECTORY).listFiles();
            int filesBefore = files == null ? 0 : files.length;

            Assertions.assertThrows(Exception.class, () -> repository.get(null));

            files = new File(SCREEN_SHOOTS_DIRECTORY).listFiles();
            int filesAfter = files == null ? 0 : files.length;

            Assertions.assertEquals(filesBefore + 1, filesAfter);
        } finally {
            driverPool.close();
        }
    }


    protected abstract ScrapperRepository<K,E> createRepository();
    protected abstract ScrapperRepository<K,E> createRepository(IDriverPool driverPool);
    protected abstract K getExistentKey();
    protected abstract K getNonExistentKey();

}