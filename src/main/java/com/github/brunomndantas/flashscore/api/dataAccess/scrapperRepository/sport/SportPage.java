package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.sport;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.FlashscorePage;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreUtils;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import org.apache.commons.text.WordUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Collection;

public class SportPage extends FlashscorePage {

    public static final By REGIONS_LINKS_SELECTOR = By.xpath("//*[contains(@class, 'lmc__element') and contains(@class, 'lmc__item')]");


    private SportKey sportKey;


    public SportPage(WebDriver driver, SportKey sportKey) {
        super(driver, FlashscoreURLs.getSportURL(sportKey));
        this.sportKey = sportKey;
    }


    public String getName() {
        String name = sportKey.getSportId().replace("-", " ").toUpperCase();
        name = WordUtils.capitalizeFully(name);
        return name;
    }

    public Collection<RegionKey> getRegionsKeys() {
        FlashscoreUtils.expandAllRegions(driver);
        return getValues(REGIONS_LINKS_SELECTOR, element -> {
            String href = element.getAttribute("href");
            return FlashscoreURLs.getRegionKey(href);
        });
    }

}
