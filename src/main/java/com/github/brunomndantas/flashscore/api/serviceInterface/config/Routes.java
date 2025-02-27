package com.github.brunomndantas.flashscore.api.serviceInterface.config;

public class Routes {

    public static final String SPORTS_ROUTE = "/sports";
    public static final String SPORT_ROUTE = SPORTS_ROUTE + "/{sportId}";

    public static final String REGIONS_ROUTE = SPORT_ROUTE + "/regions";
    public static final String REGION_ROUTE = REGIONS_ROUTE + "/{regionId}";

    public static final String COMPETITIONS_ROUTE = REGION_ROUTE + "/competitions";
    public static final String COMPETITION_ROUTE = COMPETITIONS_ROUTE + "/{competitionId}";

    public static final String SEASONS_ROUTE = COMPETITION_ROUTE + "/seasons";
    public static final String SEASON_ROUTE = SEASONS_ROUTE + "/{seasonId}";

    public static final String MATCHES_ROUTE = "/matches";
    public static final String MATCH_ROUTE = MATCHES_ROUTE + "/{matchId}";

    public static final String TEAMS_ROUTE = "/teams";
    public static final String TEAM_ROUTE = TEAMS_ROUTE + "/{teamName}/{teamId}";

    public static final String PLAYERS_ROUTE = "/players";
    public static final String PLAYER_ROUTE = PLAYERS_ROUTE + "/{playerName}/{playerId}";

}

