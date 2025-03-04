package com.github.brunomndantas.flashscore.api.logic.services.scrapService;

import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapperException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ScrapService implements IScrapService {

    private IScrapService sportScrapService;
    private IScrapService regionScrapService;
    private IScrapService competitionScrapService;
    private IScrapService seasonScrapService;
    private IScrapService matchScrapService;
    private IScrapService teamScrapService;
    private IScrapService playerScrapService;


    @Override
    public void scrap(Report report) throws EntityScrapperException {
        sportScrapService.scrap(report);
        regionScrapService.scrap(report);
        competitionScrapService.scrap(report);
        seasonScrapService.scrap(report);
        matchScrapService.scrap(report);
        teamScrapService.scrap(report);
        playerScrapService.scrap(report);
    }

}