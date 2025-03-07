package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreSelectors;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreUtils;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.apache.commons.text.WordUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;

public class SportScrapperRepository extends ScrapperRepository<SportKey, Sport> {

    public SportScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected String getUrlOfEntity(SportKey sportKey) {
        return FlashscoreURLs.getSportURL(sportKey);
    }

    @Override
    protected Sport scrapEntity(WebDriver driver, SportKey sportKey) {
        Sport sport = new Sport();

        sport.setKey(sportKey);
        sport.setName(scrapName(sportKey));
        sport.setRegionsKeys(scrapRegionsKeys(driver));

        return sport;
    }

    protected String scrapName(SportKey sportKey) {
        String name = sportKey.getSportId().replace("-", " ").toUpperCase();
        name = WordUtils.capitalizeFully(name);
        return name;
    }

    protected Collection<RegionKey> scrapRegionsKeys(WebDriver driver) {
        FlashscoreUtils.expandAllRegions(driver);

        Collection<WebElement> regionsLinks = driver.findElements(FlashscoreSelectors.REGIONS_LINKS_SELECTOR);

        return regionsLinks
                .stream()
                .map(element -> element.getAttribute("href"))
                .map(FlashscoreURLs::getRegionKey)
                .toList();
    }

}