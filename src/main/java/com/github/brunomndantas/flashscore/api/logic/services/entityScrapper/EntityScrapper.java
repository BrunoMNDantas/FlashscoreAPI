package com.github.brunomndantas.flashscore.api.logic.services.entityScrapper;

import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.tpl4j.task.pool.TaskPool;

public class EntityScrapper<K, E> implements IEntityScrapper<K, E> {

    protected IRepository<K, E> repository;
    protected TaskPool taskPool;


    public EntityScrapper(IRepository<K,E> repository, TaskPool taskPool) {
        this.repository = repository;
        this.taskPool = taskPool;
    }


    @Override
    public void scrap(EntityReport<K, E> entityReport) throws EntityScrapperException {
        try {
            taskPool.forEach(entityReport.getEntitiesToLoad(), key -> {
                try {
                    E entity = repository.get(key);
                    entityReport.addSucceededLoad(key, entity);
                } catch (Exception e) {
                    entityReport.addFailedLoad(key, e);
                }
            }).getResult();
        } catch (Exception e) {
            throw new EntityScrapperException("Error scrapping entities!", e);
        }
    }

}