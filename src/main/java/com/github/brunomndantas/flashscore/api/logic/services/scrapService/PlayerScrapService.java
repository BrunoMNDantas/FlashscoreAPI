package com.github.brunomndantas.flashscore.api.logic.services.scrapService;

import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.player.Player;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityReport;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.IEntityScrapper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

public class PlayerScrapService extends EntityScrapService<PlayerKey, Player> {

    private int maxPlayersPerMatch;
    private int maxPlayersPerTeam;


    public PlayerScrapService(IEntityScrapper<PlayerKey,Player> entityScrapper, int maxPlayersPerMatch, int maxPlayersPerTeam) {
        super(entityScrapper);

        this.maxPlayersPerMatch = maxPlayersPerMatch;
        this.maxPlayersPerTeam = maxPlayersPerTeam;
    }


    @Override
    protected EntityReport<PlayerKey, Player> getEntityReport(Report report) {
        return report.getPlayerReport();
    }

    @Override
    public void registerEntitiesToLoad(Report report, EntityReport<PlayerKey, Player> entityReport) {
        registerPlayersToLoadPerMatch(report);
        registerPlayersToLoadPerTeam(report);
    }

    public void registerPlayersToLoadPerMatch(Report report) {
        Collection<Season> seasons = report.getSeasonReport().getSucceededLoads().values();
        Map<MatchKey, Match> matches = report.getMatchReport().getSucceededLoads();

        Match match;
        Collection<PlayerKey> playersOfMatch;
        for(Season season : seasons) {
            for(MatchKey matchKey : season.getMatchesKeys()) {
                if(matches.containsKey(matchKey)) {
                    playersOfMatch = new LinkedList<>();

                    match = matches.get(matchKey);

                    playersOfMatch.add(match.getHomeCoachPlayerKey());
                    playersOfMatch.add(match.getAwayCoachPlayerKey());

                    playersOfMatch.addAll(match.getHomeLineupPlayersKeys());
                    playersOfMatch.addAll(match.getAwayLineupPlayersKeys());

                    playersOfMatch.addAll(match.getHomeBenchPlayersKeys());
                    playersOfMatch.addAll(match.getAwayBenchPlayersKeys());

                    playersOfMatch
                            .stream()
                            .filter(Objects::nonNull)
                            .distinct()
                            .limit(maxPlayersPerMatch == ALL ? Long.MAX_VALUE : maxPlayersPerMatch)
                            .forEach(report.getPlayerReport()::addEntityToLoad);
                }
            }
        }
    }

    public void registerPlayersToLoadPerTeam(Report report) {
        Collection<Team> teams = report.getTeamReport().getSucceededLoads().values();

        Collection<PlayerKey> playersOfTeam;
        for(Team team : teams) {
            playersOfTeam = new LinkedList<>();

            playersOfTeam.add(team.getCoachKey());
            playersOfTeam.addAll(team.getPlayersKeys());

            playersOfTeam
                    .stream()
                    .filter(Objects::nonNull)
                    .distinct()
                    .limit(maxPlayersPerTeam == ALL ? Long.MAX_VALUE : maxPlayersPerTeam)
                    .forEach(report.getPlayerReport()::addEntityToLoad);
        }
    }

}