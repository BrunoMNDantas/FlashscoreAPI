package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository;

import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPool;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPoolException;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import com.github.brunomndantas.flashscore.api.transversal.driverSupplier.FlashscoreDriverSupplier;
import com.github.brunomndantas.jscrapper.core.driverSupplier.IDriverSupplier;
import com.github.brunomndantas.jscrapper.support.driverSupplier.ChromeDriverSupplier;
import com.github.brunomndantas.repository4j.exception.NonExistentEntityException;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Set;

@SpringBootTest
public abstract class ScrapperRepositoryTests<K,E> {

    protected static IDriverSupplier SOURCE_DRIVER_SUPPLIER;
    protected static IDriverSupplier DRIVER_SUPPLIER;
    protected static IDriverPool DRIVER_POOL;


    @AfterAll
    public static void dispose() throws Exception {
        DRIVER_POOL.close();
        DRIVER_POOL = null;
    }


    @Value("${driver.path}")
    protected String driverPath;
    @Value("${driver.silent}")
    protected boolean driverSilent;
    @Value("${driver.headless}")
    protected boolean driverHeadless;
    @Value("${screenshots.directory}")
    protected String screenshotsDirectory;


    @BeforeEach
    public void init() {
        if(DRIVER_POOL == null) {
            SOURCE_DRIVER_SUPPLIER = new ChromeDriverSupplier(driverPath, driverSilent, driverHeadless);
            DRIVER_SUPPLIER = new FlashscoreDriverSupplier(SOURCE_DRIVER_SUPPLIER);
            DRIVER_POOL = new DriverPool(DRIVER_SUPPLIER, 1);
        }
    }


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
        WebDriver driver = DRIVER_POOL.getDriver();
        IDriverSupplier driverSupplier = () -> driver;
        IDriverPool driverPool = new DriverPool(driverSupplier, 1);

        try {
            ScrapperRepository<K,E> repository = createRepository(driverPool);
            repository.get(getExistentKey());
            Assertions.assertSame(driver, driverPool.getDriver());
        } finally {
            DRIVER_POOL.returnDriver(driver);
        }
    }

    @Test
    public void shouldWrapExceptionGettingDriverFromPool() throws Exception {
        DriverPoolException exception = new DriverPoolException();
        WebDriver driver = new ChromeDriverSupplier(driverPath, driverSilent, driverHeadless).getDriver();
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
            driver.quit();
        }
    }

    @Test
    public void shouldWrapExceptionReturningDriverToPool() throws Exception {
        DriverPoolException exception = new DriverPoolException();
        WebDriver driver = DRIVER_POOL.getDriver();
        IDriverSupplier driverSupplier = () -> driver;
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
            DRIVER_POOL.returnDriver(driver);
        }
    }

    @Test
    public void shouldThrowNonExistentEntityException() {
        K key = getNonExistentKey();
        ScrapperRepository<K,E> repository = createRepository();
        NonExistentEntityException exception = Assertions.assertThrows(NonExistentEntityException.class, () -> repository.get(key));
        Assertions.assertTrue(exception.getMessage().contains(key.toString()));
    }

    @Test
    public void shouldReturnEntity() throws Exception {
        K key = getExistentKey();
        ScrapperRepository<K,E> repository = createRepository();

        E entity = repository.get(key);

        Assertions.assertNotNull(entity);
        Assertions.assertNull(getConstraintViolation(entity));
    }

    protected <E> String getConstraintViolation(E entity) {
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();

        try(factory) {
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<E>> violations = validator.validate(entity);

            if(!violations.isEmpty()) {
                ConstraintViolation<E> violation = violations.iterator().next();
                return violation.getPropertyPath() + ": " + violation.getMessage();
            }
        }

        return null;
    }

    @Test
    public void shouldSaveScreenShoot() throws Exception {
        IDriverPool driverPool = new DriverPool(DRIVER_SUPPLIER, 1);
        ScrapperRepository<K,E> repository = new ScrapperRepository<>(driverPool, screenshotsDirectory) {

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
            File[] files = new File(screenshotsDirectory).listFiles();
            int filesBefore = files == null ? 0 : files.length;

            Assertions.assertThrows(Exception.class, () -> repository.get(null));

            files = new File(screenshotsDirectory).listFiles();
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