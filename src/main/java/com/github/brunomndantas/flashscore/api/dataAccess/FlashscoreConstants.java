package com.github.brunomndantas.flashscore.api.dataAccess;

import org.openqa.selenium.By;

public class FlashscoreConstants {

    /********/
    /* URLS */
    /********/

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

    public static final String TEAM_ID_PLACEHOLDER = "{teamId}";
    public static final String TEAM_URL = FLASHSCORE_URL + "/team/" + TEAM_ID_PLACEHOLDER;
    public static final String TEAM_SQUAD_URL = FLASHSCORE_URL + "/team/" + TEAM_ID_PLACEHOLDER + "/squad";

    public static final String PLAYER_ID_PLACEHOLDER = "{playerId}";
    public static final String PLAYER_URL = FLASHSCORE_URL + "/player/" + PLAYER_ID_PLACEHOLDER;

    /*************/
    /* SELECTORS */
    /*************/

    public static final By UNKNOWN_PAGE_ERROR_SELECTOR = By.xpath("//*[contains(text(), \"page can't be displayed\")]");
    public static final By LOGO_SELECTOR = By.xpath("//*[contains(@class, 'header__logo')]");

    public static final By ACCEPT_TERMS_BUTTON_SELECTOR = By.xpath("//*[@id='onetrust-accept-btn-handler']");

    public static final By SHOW_ALL_REGIONS_LABEL_SELECTOR = By.xpath("//*[@class = \"lmc__itemMore\"]");
    public static final By REGIONS_LINKS_SELECTOR = By.xpath("//*[contains(@class, \"lmc__element\") and contains(@class, \"lmc__item\")]");

    public static final By SHOW_ALL_COMPETITIONS_SELECTOR = By.xpath("//*[contains(@class, 'show-more')]");
    public static final By COMPETITIONS_LINKS_SELECTOR = By.xpath("//*[contains(@class,'selected-country')]//*[@class='leftMenu__href']");

    public static final By COMPETITION_NAME_SELECTOR = By.xpath("//*[@class='heading__name']");
    public static final By SEASONS_LINKS_SELECTOR = By.xpath("//*[@class = 'archive__row']//*[@class = 'archive__season']//a");


    public static final By SEASONS_YEARS_SELECTOR = By.xpath("//*[@class='heading__info']");
    public static final By LOAD_MORE_MATCHES_LABEL_SELECTOR = By.xpath("//*[contains(@class, 'event__more')]");
    public static final By PAST_MATCHES_SELECTOR = By.xpath("//*[contains(@class, 'event__match')]/a[@class = 'eventRowLink']");
    public static final By TODAY_MATCHES_SELECTOR = By.xpath("//*[contains(@class, 'leagues--live')]//*[contains(@class, 'event__match')]/a[@class = 'eventRowLink']");
    public static final By FUTURE_MATCHES_SELECTOR = By.xpath("//*[contains(@class, 'event__match')]/a[@class = 'eventRowLink']");

    public static final By MATCH_HOME_TEAM_SELECTOR = By.xpath("//*[contains(@class, 'duelParticipant__home')]//a[contains(@class, 'participant__participantName')]");
    public static final By MATCH_AWAY_TEAM_SELECTOR = By.xpath("//*[contains(@class, 'duelParticipant__away')]//a[contains(@class, 'participant__participantName')]");
    public static final By MATCH_HOME_TEAM_GOALS_SELECTOR = By.xpath("//*[contains(@class, 'detailScore__wrapper')]/span[not(contains(@class, 'divider'))][1]");
    public static final By MATCH_AWAY_TEAM_GOALS_SELECTOR = By.xpath("//*[contains(@class, 'detailScore__wrapper')]/span[not(contains(@class, 'divider'))][2]");
    public static final By MATCH_DATE_SELECTOR = By.xpath("//*[@class='duelParticipant__startTime']");
    
    public static final By TEAM_NAME_SELECTOR = By.xpath("//*[@class='heading__name']");
    public static final By TEAM_COACH_SELECTOR = By.xpath("//*[text()='Coach']/..//*[contains(@class, 'lineupTable__cell--player')]//*[@class='lineupTable__cell--name']");
    public static final By TEAM_PLAYERS_SELECTOR = By.xpath("//*[@class='lineupTable__row']//*[contains(@class, 'lineupTable__cell--player')]//*[@class='lineupTable__cell--name']");

    public static final By PLAYER_NAME_SELECTOR = By.xpath("//*[@class='playerHeader__nameWrapper']");
    public static final By PLAYER_BIRTHDATE_SELECTOR = By.xpath("//*[@class='playerInfoItem']/span[2]");
    
}