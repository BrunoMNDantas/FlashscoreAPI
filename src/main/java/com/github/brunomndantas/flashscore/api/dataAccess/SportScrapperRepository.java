package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionId;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;

import static com.github.brunomndantas.flashscore.api.dataAccess.FlashscoreConstants.*;

public class SportScrapperRepository extends ScrapperRepository<String, Sport> {

    public SportScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected String getUrlOfEntity(String sportId) {
        return SPORT_URL.replace(SPORT_ID_PLACEHOLDER, sportId);
    }

    @Override
    protected Sport scrapEntity(WebDriver driver, String sportId) {
        Sport sport = new Sport();

        sport.setId(sportId);
        sport.setName(scrapName(sportId));
        sport.setRegionsIds(scrapRegionsIds(driver, sportId));

        return sport;
    }

    protected String scrapName(String sportId) {
        return sportId.replace("-", " ").toUpperCase();
    }

    protected Collection<RegionId> scrapRegionsIds(WebDriver driver, String sportId) {
        expandAllRegions(driver);

        Collection<WebElement> regionsLinks = driver.findElements(REGIONS_LINKS_SELECTOR);

        return regionsLinks
                .stream()
                .map(element -> element.getAttribute("href"))
                .map(href -> StringUtils.splitByWholeSeparatorPreserveAllTokens(href, sportId, 2)[1])
                .map(region -> region.replace("/", "").trim())
                .map(regionId -> new RegionId(sportId, regionId))
                .toList();
    }

    protected void expandAllRegions(WebDriver driver) {
        Collection<WebElement> showMoreLabels = driver.findElements(SHOW_ALL_REGIONS_LABEL_SELECTOR);

        if(showMoreLabels.isEmpty()) {
            return;
        }

        WebElement showMoreLabel = showMoreLabels.stream().findFirst().get();

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", showMoreLabel);

        showMoreLabel.click();
    }

}