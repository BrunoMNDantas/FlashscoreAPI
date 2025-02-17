package com.github.brunomndantas.flashscore.api.dataAccess.utils;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;

public class FlashscoreURLs {

    public static final String FLASHSCORE_URL = "https://www.flashscore.com";

    public static final String SPORT_ID_PLACEHOLDER = "{sportId}";
    public static final String SPORT_URL = FLASHSCORE_URL + "/" + SPORT_ID_PLACEHOLDER;

    public static final String REGION_ID_PLACEHOLDER = "{regionId}";
    public static final String REGION_URL = FLASHSCORE_URL + "/" + SPORT_ID_PLACEHOLDER + "/" + REGION_ID_PLACEHOLDER;

    public static final String COMPETITION_ID_PLACEHOLDER = "{competitionId}";
    public static final String COMPETITION_URL = FLASHSCORE_URL + "/" + SPORT_ID_PLACEHOLDER + "/" + REGION_ID_PLACEHOLDER + "/" + COMPETITION_ID_PLACEHOLDER + "/archive";

    public static final String SEASON_ID_PLACEHOLDER = "{seasonId}";
    public static final String SEASON_URL = FLASHSCORE_URL + "/" + SPORT_ID_PLACEHOLDER + "/" + REGION_ID_PLACEHOLDER + "/" + COMPETITION_ID_PLACEHOLDER + "-" + SEASON_ID_PLACEHOLDER;

    public static final String PAST_MATCHES_URL = FLASHSCORE_URL + "/" + SPORT_ID_PLACEHOLDER + "/" + REGION_ID_PLACEHOLDER + "/" + COMPETITION_ID_PLACEHOLDER + "-" + SEASON_ID_PLACEHOLDER + "/results";
    public static final String FUTURE_MATCHES_URL = FLASHSCORE_URL + "/" + SPORT_ID_PLACEHOLDER + "/" + REGION_ID_PLACEHOLDER + "/" + COMPETITION_ID_PLACEHOLDER + "-" + SEASON_ID_PLACEHOLDER + "/fixtures";
    public static final String TODAY_MATCHES_URL = FLASHSCORE_URL + "/" + SPORT_ID_PLACEHOLDER + "/" + REGION_ID_PLACEHOLDER + "/" + COMPETITION_ID_PLACEHOLDER + "-" + SEASON_ID_PLACEHOLDER;

    public static final String MATCH_ID_PLACEHOLDER = "{matchId}";
    public static final String MATCH_URL = FLASHSCORE_URL + "/match/" + MATCH_ID_PLACEHOLDER + "/#/match-summary";

    public static final String TEAM_NAME_PLACEHOLDER = "{teamName}";
    public static final String TEAM_ID_PLACEHOLDER = "{teamId}";
    public static final String TEAM_URL = FLASHSCORE_URL + "/team/" + TEAM_NAME_PLACEHOLDER + "/" + TEAM_ID_PLACEHOLDER;
    public static final String TEAM_SQUAD_URL = TEAM_URL + "/squad";

    public static final String PLAYER_NAME_PLACEHOLDER = "{playerName}";
    public static final String PLAYER_ID_PLACEHOLDER = "{playerId}";
    public static final String PLAYER_URL = FLASHSCORE_URL + "/player/" + PLAYER_NAME_PLACEHOLDER + "/" + PLAYER_ID_PLACEHOLDER;


    public static String getSportURL(SportKey sportKey) {
        return SPORT_URL.replace(SPORT_ID_PLACEHOLDER, sportKey.getSportId());
    }

    public static String getRegionURL(RegionKey regionKey) {
        return REGION_URL
                .replace(SPORT_ID_PLACEHOLDER, regionKey.getSportId())
                .replace(REGION_ID_PLACEHOLDER, regionKey.getRegionId());
    }

    public static String getCompetitionURL(CompetitionKey competitionKey) {
        return COMPETITION_URL
                .replace(SPORT_ID_PLACEHOLDER, competitionKey.getSportId())
                .replace(REGION_ID_PLACEHOLDER, competitionKey.getRegionId())
                .replace(COMPETITION_ID_PLACEHOLDER, competitionKey.getCompetitionId());
    }

    public static String getSeasonURL(SeasonKey seasonKey) {
        return SEASON_URL
                .replace(SPORT_ID_PLACEHOLDER, seasonKey.getSportId())
                .replace(REGION_ID_PLACEHOLDER, seasonKey.getRegionId())
                .replace(COMPETITION_ID_PLACEHOLDER, seasonKey.getCompetitionId())
                .replace(SEASON_ID_PLACEHOLDER, seasonKey.getSeasonId());
    }

    public static String getSeasonPastMatchesURL(SeasonKey seasonKey) {
        return PAST_MATCHES_URL
                .replace(SPORT_ID_PLACEHOLDER, seasonKey.getSportId())
                .replace(REGION_ID_PLACEHOLDER, seasonKey.getRegionId())
                .replace(COMPETITION_ID_PLACEHOLDER, seasonKey.getCompetitionId())
                .replace(SEASON_ID_PLACEHOLDER, seasonKey.getSeasonId());
    }

    public static String getSeasonTodayMatchesURL(SeasonKey seasonKey) {
        return TODAY_MATCHES_URL
                .replace(SPORT_ID_PLACEHOLDER, seasonKey.getSportId())
                .replace(REGION_ID_PLACEHOLDER, seasonKey.getRegionId())
                .replace(COMPETITION_ID_PLACEHOLDER, seasonKey.getCompetitionId())
                .replace(SEASON_ID_PLACEHOLDER, seasonKey.getSeasonId());
    }

    public static String getSeasonFutureMatchesURL(SeasonKey seasonKey) {
        return FUTURE_MATCHES_URL
                .replace(SPORT_ID_PLACEHOLDER, seasonKey.getSportId())
                .replace(REGION_ID_PLACEHOLDER, seasonKey.getRegionId())
                .replace(COMPETITION_ID_PLACEHOLDER, seasonKey.getCompetitionId())
                .replace(SEASON_ID_PLACEHOLDER, seasonKey.getSeasonId());
    }

    public static String getMatchURL(MatchKey matchKey) {
        return MATCH_URL.replace(MATCH_ID_PLACEHOLDER, matchKey.getMatchId());

    }

    public static String getTeamURL(TeamKey teamKey) {
        return TEAM_URL
                .replace(TEAM_NAME_PLACEHOLDER, teamKey.getTeamName())
                .replace(TEAM_ID_PLACEHOLDER, teamKey.getTeamId());

    }

    public static String getTeamSquadURL(TeamKey teamKey) {
        return TEAM_SQUAD_URL
                .replace(TEAM_NAME_PLACEHOLDER, teamKey.getTeamName())
                .replace(TEAM_ID_PLACEHOLDER, teamKey.getTeamId());
    }

    public static String getPlayerURL(PlayerKey playerKey) {
        return PLAYER_URL
                .replace(PLAYER_NAME_PLACEHOLDER, playerKey.getPlayerName())
                .replace(PLAYER_ID_PLACEHOLDER, playerKey.getPlayerId());
    }

    public static SportKey getSportKey(String url) {
        String[] parts = url.split(FLASHSCORE_URL);
        parts = parts[1].split("/");

        String sportId = parts[1];

        return new SportKey(sportId);
    }

    public static RegionKey getRegionKey(String url) {
        String[] parts = url.split(FLASHSCORE_URL);
        parts = parts[1].split("/");

        String sportId = parts[1];
        String regionId = parts[2];

        return new RegionKey(sportId, regionId);
    }

    public static CompetitionKey getCompetitionKey(String url) {
        String[] parts = url.split(FLASHSCORE_URL);
        parts = parts[1].split("/");

        String sportId = parts[1];
        String regionId = parts[2];
        String competitionId = parts[3];

        return new CompetitionKey(sportId, regionId, competitionId);
    }

    public static SeasonKey getSeasonKey(String url) {
        String[] parts = url.split(FLASHSCORE_URL);
        parts = parts[1].split("/");

        String sportId = parts[1];
        String regionId = parts[2];
        String competitionId = parts[3].split("-")[0];
        String seasonId = parts[3].replace(competitionId+"-", "");

        return new SeasonKey(sportId, regionId, competitionId, seasonId);
    }

    public static MatchKey getMatchKey(String url) {
        String[] parts = url.split(FLASHSCORE_URL);
        parts = parts[1].split("/");

        String matchId = parts[2];

        return new MatchKey(matchId);
    }

    public static TeamKey getTeamKey(String url) {
        String[] parts = url.split(FLASHSCORE_URL);
        parts = parts[1].split("/");

        String teamName = parts[2];
        String teamId = parts[3];

        return new TeamKey(teamName, teamId);
    }

    public static PlayerKey getPlayerKey(String url) {
        String[] parts = url.split(FLASHSCORE_URL);
        parts = parts[1].split("/");

        String playerName = parts[2];
        String playerId = parts[3];

        return new PlayerKey(playerName, playerId);
    }

}
