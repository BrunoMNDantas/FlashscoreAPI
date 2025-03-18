package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.competition;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.ScrapperRepository;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.openqa.selenium.WebDriver;

public class CompetitionScrapperRepository extends ScrapperRepository<CompetitionKey, Competition, CompetitionPage> {

    public CompetitionScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected CompetitionPage getPage(WebDriver driver, CompetitionKey key) {
        return new CompetitionPage(driver, key);
    }

    @Override
    protected Competition scrapEntity(WebDriver driver, CompetitionPage page, CompetitionKey competitionKey) {
        Competition competition = new Competition();

        competition.setKey(competitionKey);
        competition.setName(page.getName());
        competition.setSeasonsKeys(page.getSeasonKeys());

        return competition;
    }

}