package com.github.brunomndantas.flashscore.api.logic.services.scrapService;

import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityReport;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapperException;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.IEntityScrapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class EntityScrapService<K, E> implements IScrapService {

    public static final int ALL = -1;


    protected IEntityScrapper<K,E> scrapper;


    @Override
    public void scrap(Report report) throws EntityScrapperException {
        EntityReport<K,E> entityReport = getEntityReport(report);
        registerEntitiesToLoad(report, entityReport);
        scrapper.scrap(entityReport);
    }


    protected abstract EntityReport<K,E> getEntityReport(Report report);
    protected abstract void registerEntitiesToLoad(Report report, EntityReport<K,E> entityReport);

}
