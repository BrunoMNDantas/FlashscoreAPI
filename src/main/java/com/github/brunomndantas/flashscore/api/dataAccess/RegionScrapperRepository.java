package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionId;
import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionId;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;

import static com.github.brunomndantas.flashscore.api.dataAccess.FlashscoreConstants.*;

public class RegionScrapperRepository extends ScrapperRepository<RegionId, Region> {

    public RegionScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected String getUrlOfEntity(RegionId regionId) {
        return REGION_URL
                .replace(SPORT_ID_PLACEHOLDER, regionId.getSportId())
                .replace(REGION_ID_PLACEHOLDER, regionId.getId());
    }

    @Override
    protected Region scrapEntity(WebDriver driver, RegionId regionId) {
        Region region = new Region();

        region.setId(regionId);
        region.setName(scrapName(regionId));
        region.setCompetitionsIds(scrapCompetitionsIds(driver, regionId));

        return region;
    }

    protected String scrapName(RegionId regionId) {
        return regionId.getId().replace("-", " ").toUpperCase();
    }

    protected Collection<CompetitionId> scrapCompetitionsIds(WebDriver driver, RegionId regionId) {
        expandAllCompetitions(driver);

        Collection<WebElement> competitionsLinks = driver.findElements(COMPETITIONS_LINKS_SELECTOR);
        return competitionsLinks
                .stream()
                .map(element -> element.getAttribute("href"))
                .map(href -> href.split(regionId.getId())[1])
                .map(competition -> competition.replace("/", "").trim())
                .map(competitionId -> new CompetitionId(regionId.getSportId(), regionId.getId(), competitionId))
                .toList();
    }

    protected void expandAllCompetitions(WebDriver driver) {
        Collection<WebElement> showMoreLabels = driver.findElements(SHOW_ALL_COMPETITIONS_SELECTOR);

        if(showMoreLabels.isEmpty()) {
            return;
        }

        WebElement showMoreLabel = showMoreLabels.stream().findFirst().get();

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", showMoreLabel);

        showMoreLabel.click();
    }

}