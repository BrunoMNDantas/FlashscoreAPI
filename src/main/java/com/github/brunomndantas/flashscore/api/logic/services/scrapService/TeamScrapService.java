package com.github.brunomndantas.flashscore.api.logic.services.scrapService;

import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityReport;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.IEntityScrapper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

public class TeamScrapService extends EntityScrapService<TeamKey, Team> {

    private int maxTeamsPerSeason;


    public TeamScrapService(IEntityScrapper<TeamKey, Team> entityScrapper, int maxTeamsPerSeason) {
        super(entityScrapper);
        this.maxTeamsPerSeason = maxTeamsPerSeason;
    }


    @Override
    protected EntityReport<TeamKey, Team> getEntityReport(Report report) {
        return report.getTeamReport();
    }

    @Override
    public void registerEntitiesToLoad(Report report, EntityReport<TeamKey, Team> entityReport) {
        Collection<Season> seasons = report.getSeasonReport().getSucceededLoads().values();
        Map<MatchKey, Match> matches = report.getMatchReport().getSucceededLoads();

        Match match;
        Collection<TeamKey> teamsOfSeason;
        for(Season season : seasons) {
            teamsOfSeason = new LinkedList<>();

            for(MatchKey matchKey : season.getMatchesKeys()) {
                if(matches.containsKey(matchKey)) {
                    match = matches.get(matchKey);
                    teamsOfSeason.add(match.getHomeTeamKey());
                    teamsOfSeason.add(match.getAwayTeamKey());
                }
            }

            teamsOfSeason
                    .stream()
                    .filter(Objects::nonNull)
                    .distinct()
                    .limit(maxTeamsPerSeason == ALL ? Long.MAX_VALUE : maxTeamsPerSeason)
                    .forEach(report.getTeamReport()::addEntityToLoad);
        }
    }

}