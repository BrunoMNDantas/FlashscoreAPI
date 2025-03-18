package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository;

import com.github.brunomndantas.flashscore.api.config.GlobalTestConfig;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPool;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPoolException;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import com.github.brunomndantas.jscrapper.core.driverSupplier.IDriverSupplier;
import com.github.brunomndantas.repository4j.exception.NonExistentEntityException;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.io.File;
import java.util.Set;

@SpringBootTest
@Import(GlobalTestConfig.class)
public abstract class ScrapperRepositoryTests<K,E,P extends FlashscorePage> {

    @Value("${screenshots.directory}")
    protected String screenshotsDirectory;

    @Autowired
    protected IDriverPool driverPool;


    @Test
    public void shouldThrowUnsupportedOperationOnGetAll() {
        ScrapperRepository<K,E,P> repository = createRepository();
        Assertions.assertThrows(UnsupportedOperationException.class, repository::getAll);
    }

    @Test
    public void shouldThrowUnsupportedOperationOnInsert() {
        ScrapperRepository<K,E,P> repository = createRepository();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> repository.insert(null));
    }

    @Test
    public void shouldThrowUnsupportedOperationOnUpdate() {
        ScrapperRepository<K,E,P> repository = createRepository();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> repository.update(null));
    }

    @Test
    public void shouldThrowUnsupportedOperationOnDelete() {
        ScrapperRepository<K,E,P> repository = createRepository();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> repository.delete(null));
    }

    @Test
    public void shouldReturnDriverToPool() throws Exception {
        WebDriver driver = driverPool.getDriver();
        IDriverSupplier driverSupplier = () -> driver;
        IDriverPool pool = new DriverPool(driverSupplier, 1);

        try {
            ScrapperRepository<K,E,P> repository = createRepository(pool);
            repository.get(getExistentKey());
            Assertions.assertSame(driver, pool.getDriver());
        } finally {
            driverPool.returnDriver(driver);
        }
    }

    @Test
    public void shouldWrapExceptionGettingDriverFromPool() {
        DriverPoolException exception = new DriverPoolException();
        IDriverPool driverPool = new DriverPool(null, 1) {
            @Override
            public WebDriver getDriver() throws DriverPoolException {
                throw exception;
            }
        };

        ScrapperRepository<K,E,P> repository = createRepository(driverPool);
        Exception returnedException = Assertions.assertThrows(RepositoryException.class, () -> repository.get(getExistentKey()));
        Assertions.assertSame(exception, returnedException.getCause());
    }

    @Test
    public void shouldWrapExceptionReturningDriverToPool() throws Exception {
        DriverPoolException exception = new DriverPoolException();
        WebDriver driver = driverPool.getDriver();
        IDriverSupplier driverSupplier = () -> driver;
        IDriverPool pool = new DriverPool(driverSupplier, 1) {
            @Override
            public void returnDriver(WebDriver driver) throws DriverPoolException {
                throw exception;
            }
        };

        try {
            ScrapperRepository<K,E,P> repository = createRepository(pool);
            Exception returnedException = Assertions.assertThrows(RepositoryException.class, () -> repository.get(getExistentKey()));
            Assertions.assertSame(exception, returnedException.getCause());
        } finally {
            driverPool.returnDriver(driver);
        }
    }

    @Test
    public void shouldThrowNonExistentEntityException() {
        K key = getNonExistentKey();
        ScrapperRepository<K,E,P> repository = createRepository();
        NonExistentEntityException exception = Assertions.assertThrows(NonExistentEntityException.class, () -> repository.get(key));
        Assertions.assertTrue(exception.getMessage().contains(key.toString()));
    }

    @Test
    public void shouldReturnEntity() throws Exception {
        K key = getExistentKey();
        ScrapperRepository<K,E,P> repository = createRepository();

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
    public void shouldSaveScreenShoot() {
        ScrapperRepository<K,E,P> repository = new ScrapperRepository<>(driverPool, screenshotsDirectory) {

            @Override
            protected P getPage(WebDriver driver, K key) {
                throw new IllegalArgumentException("Forced Error!");
            }

            @Override
            protected E scrapEntity(WebDriver driver, P page, K key) throws RepositoryException {
                return null;
            }
        };

        File[] files = new File(screenshotsDirectory).listFiles();
        int filesBefore = files == null ? 0 : files.length;

        Assertions.assertThrows(Exception.class, () -> repository.get(null));

        files = new File(screenshotsDirectory).listFiles();
        int filesAfter = files == null ? 0 : files.length;

        Assertions.assertEquals(filesBefore + 1, filesAfter);
    }


    protected abstract ScrapperRepository<K,E,P> createRepository();
    protected abstract ScrapperRepository<K,E,P> createRepository(IDriverPool driverPool);
    protected abstract K getExistentKey();
    protected abstract K getNonExistentKey();

}