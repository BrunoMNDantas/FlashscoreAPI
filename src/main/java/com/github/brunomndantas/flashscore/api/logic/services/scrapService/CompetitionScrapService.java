package com.github.brunomndantas.flashscore.api.logic.services.scrapService;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityReport;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.IEntityScrapper;

import java.util.Collection;
import java.util.Objects;

public class CompetitionScrapService extends EntityScrapService<CompetitionKey, Competition> {

    private int maxCompetitionsPerRegion;


    public CompetitionScrapService(IEntityScrapper<CompetitionKey, Competition> entityScrapper, int maxCompetitionsPerRegion) {
        super(entityScrapper);
        this.maxCompetitionsPerRegion = maxCompetitionsPerRegion;
    }


    @Override
    protected EntityReport<CompetitionKey, Competition> getEntityReport(Report report) {
        return report.getCompetitionReport();
    }

    @Override
    public void registerEntitiesToLoad(Report report, EntityReport<CompetitionKey, Competition> entityReport) {
        Collection<Region> regions = report.getRegionReport().getSucceededLoads().values();
        Collection<Collection<CompetitionKey>> competitionsOfRegions = regions.stream().map(Region::getCompetitionsKeys).toList();
        competitionsOfRegions.forEach(competitionsOfRegion -> {
            competitionsOfRegion
                .stream()
                .filter(Objects::nonNull)
                .limit(maxCompetitionsPerRegion == ALL ? Long.MAX_VALUE : maxCompetitionsPerRegion)
                .forEach(report.getCompetitionReport()::addEntityToLoad);
        });
    }

}