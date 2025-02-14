package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;

import static com.github.brunomndantas.flashscore.api.dataAccess.FlashscoreConstants.*;

public class SportScrapperRepository extends ScrapperRepository<SportKey, Sport> {

    public SportScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected String getUrlOfEntity(SportKey sportKey) {
        return SPORT_URL.replace(SPORT_ID_PLACEHOLDER, sportKey.getSportId());
    }

    @Override
    protected Sport scrapEntity(WebDriver driver, SportKey sportKey) {
        Sport sport = new Sport();

        sport.setKey(sportKey);
        sport.setName(scrapName(sportKey));
        sport.setRegionsKeys(scrapRegionsKeys(driver, sportKey));

        return sport;
    }

    protected String scrapName(SportKey sportKey) {
        String name = sportKey.getSportId().replace("-", " ").toUpperCase();
        name = WordUtils.capitalizeFully(name);
        return name;
    }

    protected Collection<RegionKey> scrapRegionsKeys(WebDriver driver, SportKey sportKey) {
        expandAllRegions(driver);

        Collection<WebElement> regionsLinks = driver.findElements(REGIONS_LINKS_SELECTOR);

        return regionsLinks
                .stream()
                .map(element -> element.getAttribute("href"))
                .map(href -> StringUtils.splitByWholeSeparatorPreserveAllTokens(href, sportKey.getSportId(), 2)[1])
                .map(region -> region.replace("/", "").trim())
                .map(regionId -> new RegionKey(sportKey.getSportId(), regionId))
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