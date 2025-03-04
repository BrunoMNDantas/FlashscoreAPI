package com.github.brunomndantas.flashscore.api.logic.services.entityScrapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class EntityReport<K/*Key*/, E/*Entity*/> {

    private Collection<K> entitiesToLoad = new LinkedList<>();

    private Map<K, Exception> failedLoads = new HashMap<>();

    private Map<K, E> succeededLoads = new HashMap<>();


    public synchronized void addEntityToLoad(K key) {
        if(key == null) {
            throw new IllegalArgumentException("Key cannot be null!");
        }

        if(!entitiesToLoad.contains(key)) {
            entitiesToLoad.add(key);
        }
    }

    public synchronized void addFailedLoad(K key, Exception error) {
        if(key == null) {
            throw new IllegalArgumentException("Key cannot be null!");
        }

        failedLoads.put(key, error);
    }

    public synchronized void addSucceededLoad(K key, E entity) {
        if(key == null) {
            throw new IllegalArgumentException("Key cannot be null!");
        }

        succeededLoads.put(key, entity);
    }

    public synchronized Collection<K> getEntitiesToLoad() {
        return new LinkedList<>(entitiesToLoad);
    }

    public synchronized Map<K, Exception> getFailedLoads() {
        return new HashMap<>(failedLoads);
    }

    public synchronized Map<K, E> getSucceededLoads() {
        return new HashMap<>(succeededLoads);
    }

}
