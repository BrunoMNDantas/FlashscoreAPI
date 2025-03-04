package com.github.brunomndantas.flashscore.api.logic.services.entityScrapper;

public interface IEntityScrapper<K/*Key*/, E/*Entity*/> {

    public void scrap(EntityReport<K, E> entityReport) throws EntityScrapperException;

}
