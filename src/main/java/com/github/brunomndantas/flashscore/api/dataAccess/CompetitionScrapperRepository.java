package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.dataAccess.utils.FlashscoreSelectors;
import com.github.brunomndantas.flashscore.api.dataAccess.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;

public class CompetitionScrapperRepository extends ScrapperRepository<CompetitionKey, Competition> {

    public CompetitionScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected String getUrlOfEntity(CompetitionKey competitionKey) {
        return FlashscoreURLs.getCompetitionURL(competitionKey);
    }

    @Override
    protected Competition scrapEntity(WebDriver driver, CompetitionKey competitionKey) {
        Competition competition = new Competition();

        competition.setKey(competitionKey);
        competition.setName(scrapName(driver));
        competition.setSeasonsKeys(scrapSeasonsKeys(driver, competitionKey));

        return competition;
    }

    protected String scrapName(WebDriver driver) {
        WebElement nameElement = driver.findElement(FlashscoreSelectors.COMPETITION_NAME_SELECTOR);
        return nameElement.getText();
    }

    protected Collection<SeasonKey> scrapSeasonsKeys(WebDriver driver, CompetitionKey competitionKey) {
        Collection<WebElement> seasonsLinks = driver.findElements(FlashscoreSelectors.SEASONS_LINKS_SELECTOR);
        return seasonsLinks
                .stream()
                .map(element -> element.getText().trim())
                .map(text -> {
                    String[] parts = text.split(" ");
                    return parts[parts.length - 1];
                })
                .map(season -> season.replace("/", "-"))
                .map(seasonId -> new SeasonKey(competitionKey.getSportId(), competitionKey.getRegionId(), competitionKey.getCompetitionId(), seasonId))
                .toList();
    }

}