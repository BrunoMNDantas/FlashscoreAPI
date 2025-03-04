package com.github.brunomndantas.flashscore.api.logic.services.scrapService;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityReport;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.IEntityScrapper;

import java.util.Collection;
import java.util.Objects;

public class SeasonScrapService extends EntityScrapService<SeasonKey, Season> {

    private int maxSeasonsPerCompetition;


    public SeasonScrapService(IEntityScrapper<SeasonKey, Season> entityScrapper, int maxSeasonsPerCompetition) {
        super(entityScrapper);
        this.maxSeasonsPerCompetition = maxSeasonsPerCompetition;
    }


    @Override
    protected EntityReport<SeasonKey, Season> getEntityReport(Report report) {
        return report.getSeasonReport();
    }

    @Override
    public void registerEntitiesToLoad(Report report, EntityReport<SeasonKey, Season> entityReport) {
        Collection<Competition> competitions = report.getCompetitionReport().getSucceededLoads().values();
        Collection<Collection<SeasonKey>> seasonsOfCompetitions = competitions.stream().map(Competition::getSeasonsKeys).toList();
        seasonsOfCompetitions.forEach(seasonsOfCompetition -> {
            seasonsOfCompetition
                .stream()
                .filter(Objects::nonNull)
                .limit(maxSeasonsPerCompetition == ALL ? Long.MAX_VALUE : maxSeasonsPerCompetition)
                .forEach(report.getSeasonReport()::addEntityToLoad);
        });
    }

}