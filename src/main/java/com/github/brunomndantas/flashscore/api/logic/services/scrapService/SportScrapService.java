package com.github.brunomndantas.flashscore.api.logic.services.scrapService;

import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityReport;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.IEntityScrapper;

import java.util.Collection;
import java.util.Objects;

public class SportScrapService extends EntityScrapService<SportKey, Sport> {

    private Collection<String> sportsIds;
    private int maxSportsToLoad;


    public SportScrapService(IEntityScrapper<SportKey,Sport> entityScrapper, Collection<String> sportsIds, int maxSportsToLoad) {
        super(entityScrapper);

        this.sportsIds = sportsIds;
        this.maxSportsToLoad = maxSportsToLoad;
    }


    @Override
    protected EntityReport<SportKey, Sport> getEntityReport(Report report) {
        return report.getSportReport();
    }

    @Override
    public void registerEntitiesToLoad(Report report, EntityReport<SportKey, Sport> entityReport) {
        sportsIds
            .stream()
            .filter(Objects::nonNull)
            .limit(maxSportsToLoad == ALL ? Long.MAX_VALUE : maxSportsToLoad)
            .forEach(id -> report.getSportReport().addEntityToLoad(new SportKey(id)));
    }

}