package com.github.brunomndantas.flashscore.api.logic.services.scrapService;

import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityReport;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.IEntityScrapper;

import java.util.Collection;
import java.util.Objects;

public class MatchScrapService extends EntityScrapService<MatchKey, Match> {

    private int maxMatchesPerSeason;


    public MatchScrapService(IEntityScrapper<MatchKey, Match> entityScrapper, int maxMatchesPerSeason) {
        super(entityScrapper);
        this.maxMatchesPerSeason = maxMatchesPerSeason;
    }


    @Override
    protected EntityReport<MatchKey, Match> getEntityReport(Report report) {
        return report.getMatchReport();
    }

    @Override
    public void registerEntitiesToLoad(Report report, EntityReport<MatchKey, Match> entityReport) {
        Collection<Season> seasons = report.getSeasonReport().getSucceededLoads().values();
        Collection<Collection<MatchKey>> matchesOfSeasons = seasons.stream().map(Season::getMatchesKeys).toList();
        matchesOfSeasons.forEach(matchesOfSeason -> {
            matchesOfSeason
                .stream()
                .filter(Objects::nonNull)
                .limit(maxMatchesPerSeason == ALL ? Long.MAX_VALUE : maxMatchesPerSeason)
                .forEach(report.getMatchReport()::addEntityToLoad);
        });
    }

}