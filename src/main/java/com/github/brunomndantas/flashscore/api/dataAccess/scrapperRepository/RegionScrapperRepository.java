package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreSelectors;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreUtils;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;

public class RegionScrapperRepository extends ScrapperRepository<RegionKey, Region> {

    public RegionScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected String getUrlOfEntity(RegionKey regionKey) {
        return FlashscoreURLs.getRegionURL(regionKey);
    }

    @Override
    protected Region scrapEntity(WebDriver driver, RegionKey regionKey) {
        Region region = new Region();

        region.setKey(regionKey);
        region.setName(scrapName(driver, regionKey));
        region.setCompetitionsKeys(scrapCompetitionsKeys(driver));

        return region;
    }

    protected String scrapName(WebDriver driver, RegionKey regionKey) {
        FlashscoreUtils.expandAllRegions(driver);

        Collection<WebElement> regionsLinks = driver.findElements(FlashscoreSelectors.REGIONS_LINKS_SELECTOR);
        WebElement element = regionsLinks
                .stream()
                .filter(regionLink -> {
                    String href = regionLink.getAttribute("href");
                    return href.contains(regionKey.getSportId()) && href.contains(regionKey.getRegionId());
                })
                .findFirst()
                .get();

        return element.getText();
    }

    protected Collection<CompetitionKey> scrapCompetitionsKeys(WebDriver driver) {
        FlashscoreUtils.expandAllCompetitions(driver);

        Collection<WebElement> competitionsLinks = driver.findElements(FlashscoreSelectors.COMPETITIONS_LINKS_SELECTOR);
        return competitionsLinks
                .stream()
                .map(element -> element.getAttribute("href"))
                .map(FlashscoreURLs::getCompetitionKey)
                .toList();
    }

}