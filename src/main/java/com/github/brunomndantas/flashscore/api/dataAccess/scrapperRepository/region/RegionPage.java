package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.region;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.FlashscorePage;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreUtils;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;

public class RegionPage extends FlashscorePage {

    public static final By COMPETITIONS_LINKS_SELECTOR = By.xpath("//*[contains(@class,'selected-country')]//*[@class='leftMenu__href']");
    public static final By REGIONS_LINKS_SELECTOR = By.xpath("//*[contains(@class, 'lmc__element') and contains(@class, 'lmc__item')]");


    private RegionKey regionKey;


    public RegionPage(WebDriver driver, RegionKey regionKey) {
        super(driver, FlashscoreURLs.getRegionURL(regionKey));
        this.regionKey = regionKey;
    }


    public String getName() {
        FlashscoreUtils.expandAllRegions(driver);

        Collection<WebElement> regions = driver.findElements(REGIONS_LINKS_SELECTOR);
        WebElement element = regions
                .stream()
                .filter(regionLink -> {
                    String href = regionLink.getAttribute("href");
                    return href.contains(regionKey.getSportId()) && href.contains(regionKey.getRegionId());
                })
                .findFirst()
                .orElse(null);

        return element == null ? null : element.getText();
    }

    public Collection<CompetitionKey> getCompetitionsKeys() {
        FlashscoreUtils.expandAllCompetitions(driver);
        return getValues(COMPETITIONS_LINKS_SELECTOR,element -> {
            String href = element.getAttribute("href");
            return FlashscoreURLs.getCompetitionKey(href);
        });
    }

}