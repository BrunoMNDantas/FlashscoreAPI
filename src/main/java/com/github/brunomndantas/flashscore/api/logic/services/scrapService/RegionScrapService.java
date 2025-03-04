package com.github.brunomndantas.flashscore.api.logic.services.scrapService;

import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityReport;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.IEntityScrapper;

import java.util.Collection;
import java.util.Objects;

public class RegionScrapService extends EntityScrapService<RegionKey, Region> {

    private int maxRegionsPerSport;


    public RegionScrapService(IEntityScrapper<RegionKey, Region> entityScrapper, int maxRegionsPerSport) {
        super(entityScrapper);
        this.maxRegionsPerSport = maxRegionsPerSport;
    }


    @Override
    protected EntityReport<RegionKey, Region> getEntityReport(Report report) {
        return report.getRegionReport();
    }

    @Override
    public void registerEntitiesToLoad(Report report, EntityReport<RegionKey, Region> entityReport) {
        Collection<Sport> sports = report.getSportReport().getSucceededLoads().values();
        Collection<Collection<RegionKey>> regionsOfSports = sports.stream().map(Sport::getRegionsKeys).toList();
        regionsOfSports.forEach(regionsOfSport -> {
            regionsOfSport
                .stream()
                .filter(Objects::nonNull)
                .limit(maxRegionsPerSport == ALL ? Long.MAX_VALUE : maxRegionsPerSport)
                .forEach(report.getRegionReport()::addEntityToLoad);
        });
    }

}