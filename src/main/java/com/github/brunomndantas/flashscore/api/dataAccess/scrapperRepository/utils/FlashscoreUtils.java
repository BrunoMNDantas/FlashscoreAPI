package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils;

import com.github.brunomndantas.flashscore.api.transversal.Config;
import com.github.brunomndantas.flashscore.api.transversal.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;

public class FlashscoreUtils {

    public static final By SHOW_ALL_COMPETITIONS_SELECTOR = By.xpath("//*[contains(@class, 'show-more')]");
    public static final By LOAD_MORE_MATCHES_LABEL_SELECTOR = By.xpath("//*[contains(@class, 'event__more')]");
    public static final By SHOW_ALL_REGIONS_LABEL_SELECTOR = By.xpath("//*[@class = 'lmc__itemMore']");


    public static void expandAllRegions(WebDriver driver) {
        Collection<WebElement> showMoreLabels = driver.findElements(SHOW_ALL_REGIONS_LABEL_SELECTOR);

        if(showMoreLabels.isEmpty()) {
            return;
        }

        WebElement showMoreLabel = showMoreLabels.stream().findFirst().get();

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", showMoreLabel);

        showMoreLabel.click();
    }

    public static void expandAllCompetitions(WebDriver driver) {
        Collection<WebElement> showMoreLabels = driver.findElements(SHOW_ALL_COMPETITIONS_SELECTOR);

        if(showMoreLabels.isEmpty()) {
            return;
        }

        WebElement showMoreLabel = showMoreLabels.stream().findFirst().get();

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", showMoreLabel);

        showMoreLabel.click();
    }

    public static void loadAllMatches(WebDriver driver) {
        Collection<WebElement> loadMoreElements;
        WebElement loadMoreElement;

        while(true) {
            loadMoreElements = driver.findElements(LOAD_MORE_MATCHES_LABEL_SELECTOR);

            if(loadMoreElements.isEmpty()) {
                break;
            }

            loadMoreElement = loadMoreElements.stream().findFirst().get();
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", loadMoreElement);
            loadMoreElement.click();

            Utils.sleep(Config.QUICK_WAIT);
        }
    }

}
