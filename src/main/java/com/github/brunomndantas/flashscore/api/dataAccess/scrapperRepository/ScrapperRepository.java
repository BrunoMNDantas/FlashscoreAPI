package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreSelectors;
import com.github.brunomndantas.flashscore.api.transversal.Config;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPoolException;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.exception.NonExistentEntityException;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.UUID;

@Log4j2
public abstract class ScrapperRepository<K,E> implements IRepository<K,E> {

    public static final String SCREEN_SHOOT_EXTENSION = ".png";


    protected IDriverPool driverPool;
    protected String logDirectory;


    public ScrapperRepository(IDriverPool driverPool, String logDirectory) {
        this.driverPool = driverPool;
        this.logDirectory = logDirectory;
    }


    @Override
    public Collection<E> getAll() throws RepositoryException {
        throw new UnsupportedOperationException("GetAll is not supported!");
    }

    @Override
    public void insert(E entity) throws RepositoryException {
        throw new UnsupportedOperationException("Insert is not supported!");
    }

    @Override
    public void update(E entity) throws RepositoryException {
        throw new UnsupportedOperationException("Update is not supported!");
    }

    @Override
    public void delete(K key) throws RepositoryException {
        throw new UnsupportedOperationException("Delete is not supported!");
    }

    @Override
    public E get(K key) throws RepositoryException {
        WebDriver driver = getDriver();

        try{
            if(!loadDriver(driver, key)) {
                throw new NonExistentEntityException("There is not entity for key:" + key);
            }

            return scrapEntity(driver, key);
        } catch (RuntimeException | RepositoryException e) {
            takeScreenShoot(driver);
            throw e;
        } finally {
            returnDriver(driver);
        }
    }

    protected boolean loadDriver(WebDriver driver, K key) throws RepositoryException {
        String url = getUrlOfEntity(key);

        driver.get(url);

        waitPageToBeLoaded(driver);

        return loadedSuccessfully(driver);
    }

    protected void waitPageToBeLoaded(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(Config.MEDIUM_WAIT).getSeconds());

        ExpectedCondition<Boolean> condition = ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(FlashscoreSelectors.LOGO_SELECTOR),
                ExpectedConditions.visibilityOfElementLocated(FlashscoreSelectors.UNKNOWN_PAGE_ERROR_SELECTOR)
        );

        wait.until(condition);
    }

    protected boolean loadedSuccessfully(WebDriver driver) {
        Collection<WebElement> errorElements = driver.findElements(FlashscoreSelectors.UNKNOWN_PAGE_ERROR_SELECTOR);
        return errorElements.isEmpty();
    }

    protected WebDriver getDriver() throws RepositoryException {
        try {
            return driverPool.getDriver();
        } catch (DriverPoolException e) {
            throw new RepositoryException("Error getting driver!", e);
        }
    }

    protected void returnDriver(WebDriver driver) throws RepositoryException {
        try {
            driverPool.returnDriver(driver);
        } catch (DriverPoolException e) {
            throw new RepositoryException("Error returning driver!", e);
        }
    }

    protected void takeScreenShoot(WebDriver driver) {
        String fileName = UUID.randomUUID().toString();
        String filePath = logDirectory + File.separator + fileName + SCREEN_SHOOT_EXTENSION;

        new File(logDirectory).mkdirs();

        log.warn("Saving Screenshot at: " + filePath);

        File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotFile, new File(filePath));
        } catch (IOException e) {
            log.error("Error trying to save screenshot at:" + filePath, e);
        }
    }


    protected abstract String getUrlOfEntity(K key) throws RepositoryException;
    protected abstract E scrapEntity(WebDriver driver, K key) throws RepositoryException;

}
