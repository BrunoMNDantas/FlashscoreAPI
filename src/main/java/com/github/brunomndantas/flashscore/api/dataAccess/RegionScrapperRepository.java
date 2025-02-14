package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;

import static com.github.brunomndantas.flashscore.api.dataAccess.FlashscoreConstants.*;

public class RegionScrapperRepository extends ScrapperRepository<RegionKey, Region> {

    public RegionScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected String getUrlOfEntity(RegionKey regionKey) {
        return REGION_URL
                .replace(SPORT_ID_PLACEHOLDER, regionKey.getSportId())
                .replace(REGION_ID_PLACEHOLDER, regionKey.getRegionId());
    }

    @Override
    protected Region scrapEntity(WebDriver driver, RegionKey regionKey) {
        Region region = new Region();

        region.setKey(regionKey);
        region.setName(scrapName(driver, regionKey));
        region.setCompetitionsKeys(scrapCompetitionsKeys(driver, regionKey));

        return region;
    }

    protected String scrapName(WebDriver driver, RegionKey regionKey) {
        FlashscoreUtils.expandAllRegions(driver);

        Collection<WebElement> regionsLinks = driver.findElements(REGIONS_LINKS_SELECTOR);

        return regionsLinks
                .stream()
                .filter(regionLink -> {
                    String href = regionLink.getAttribute("href");
                    return href.contains(regionKey.getSportId()) && href.contains(regionKey.getRegionId());
                })
                .findFirst()
                .get()
                .getText();
    }

    protected Collection<CompetitionKey> scrapCompetitionsKeys(WebDriver driver, RegionKey regionKey) {
        FlashscoreUtils.expandAllCompetitions(driver);

        Collection<WebElement> competitionsLinks = driver.findElements(COMPETITIONS_LINKS_SELECTOR);
        return competitionsLinks
                .stream()
                .map(element -> element.getAttribute("href"))
                .map(href -> StringUtils.splitByWholeSeparatorPreserveAllTokens(href, regionKey.getRegionId(), 2)[1])
                .map(competition -> competition.replace("/", "").trim())
                .map(competitionId -> new CompetitionKey(regionKey.getSportId(), regionKey.getRegionId(), competitionId))
                .toList();
    }

}