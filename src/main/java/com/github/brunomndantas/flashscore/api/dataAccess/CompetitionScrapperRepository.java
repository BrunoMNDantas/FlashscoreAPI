package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionId;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonId;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;

import static com.github.brunomndantas.flashscore.api.dataAccess.FlashscoreConstants.*;

public class CompetitionScrapperRepository extends ScrapperRepository<CompetitionId, Competition> {

    public CompetitionScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected String getUrlOfEntity(CompetitionId competitionId) {
        return COMPETITION_URL
                .replace(SPORT_ID_PLACEHOLDER, competitionId.getSportId())
                .replace(REGION_ID_PLACEHOLDER, competitionId.getRegionId())
                .replace(COMPETITION_ID_PLACEHOLDER, competitionId.getId());
    }

    @Override
    protected Competition scrapEntity(WebDriver driver, CompetitionId competitionId) {
        Competition competition = new Competition();

        competition.setId(competitionId);
        competition.setName(scrapName(competitionId));
        competition.setSeasonsIds(scrapSeasonsIds(driver, competitionId));

        return competition;
    }

    protected String scrapName(CompetitionId competitionId) {
        return competitionId.getId().replace("-", " ").toUpperCase();
    }

    protected Collection<SeasonId> scrapSeasonsIds(WebDriver driver, CompetitionId competitionId) {
        Collection<WebElement> seasonsLinks = driver.findElements(SEASONS_LINKS_SELECTOR);
        return seasonsLinks
                .stream()
                .map(element -> element.getText().trim())
                .map(text -> {
                    String[] parts = text.split(" ");
                    return parts[parts.length - 1];
                })
                .map(season -> season.replace("/", "-"))
                .map(seasonId -> new SeasonId(competitionId.getSportId(), competitionId.getRegionId(), competitionId.getId(), seasonId))
                .toList();
    }

}