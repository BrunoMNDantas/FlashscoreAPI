package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.region;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.ScrapperRepository;
import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.openqa.selenium.WebDriver;

public class RegionScrapperRepository extends ScrapperRepository<RegionKey, Region, RegionPage> {

    public RegionScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected RegionPage getPage(WebDriver driver, RegionKey key) {
        return new RegionPage(driver, key);
    }

    @Override
    protected Region scrapEntity(WebDriver driver, RegionPage page, RegionKey regionKey) {
        Region region = new Region();

        region.setKey(regionKey);
        region.setName(page.getName());
        region.setCompetitionsKeys(page.getCompetitionsKeys());

        return region;
    }

}