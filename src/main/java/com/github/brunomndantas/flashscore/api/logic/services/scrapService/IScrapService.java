package com.github.brunomndantas.flashscore.api.logic.services.scrapService;

import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapperException;

public interface IScrapService {

    public void scrap(Report report) throws EntityScrapperException;

}
