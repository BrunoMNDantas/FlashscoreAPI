package com.github.brunomndantas.flashscore.api.dataAccess.utils;

import org.openqa.selenium.By;

public class FlashscoreSelectors {

    public static final By UNKNOWN_PAGE_ERROR_SELECTOR = By.xpath("//*[contains(text(), \"page can't be displayed\")]");
    public static final By LOGO_SELECTOR = By.xpath("//*[contains(@class, 'header__logo')]");

    public static final By ACCEPT_TERMS_BUTTON_SELECTOR = By.xpath("//*[@id='onetrust-accept-btn-handler']");

    public static final By SHOW_ALL_REGIONS_LABEL_SELECTOR = By.xpath("//*[@class = 'lmc__itemMore']");
    public static final By REGIONS_LINKS_SELECTOR = By.xpath("//*[contains(@class, 'lmc__element') and contains(@class, 'lmc__item')]");

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
    public static final By MATCH_LINEUP_BUTTON_SELECTOR = By.xpath("//button[text()='Lineups']");
    public static final By MATCH_HOME_COACH_SELECTOR = By.xpath("//*[text()='Coaches']/../..//*[@class='lf__side'][1]//a[contains(@class, 'nameWrapper')]");
    public static final By MATCH_AWAY_COACH_SELECTOR = By.xpath("//*[text()='Coaches']/../..//*[@class='lf__side'][2]//a[contains(@class, 'nameWrapper')]");
    public static final By MATCH_HOME_LINEUP_SELECTOR = By.xpath("//*[text()='Starting Lineups']/../..//*[@class='lf__side'][1]//a[contains(@class, 'nameWrapper')]");
    public static final By MATCH_AWAY_LINEUP_SELECTOR = By.xpath("//*[text()='Starting Lineups']/../..//*[@class='lf__side'][2]//a[contains(@class, 'nameWrapper')]");
    public static final By MATCH_HOME_BENCH_SELECTOR = By.xpath("//*[text()='Substitutes']/../..//*[@class='lf__side'][1]//a[contains(@class, 'nameWrapper')]");
    public static final By MATCH_AWAY_BENCH_SELECTOR = By.xpath("//*[text()='Substitutes']/../..//*[@class='lf__side'][2]//a[contains(@class, 'nameWrapper')]");
    public static final By MATCH_EVENTS_SELECTOR = By.xpath("//*[contains(@class,'smv__verticalSections')]/div");
    public static final String MATCH_EVENT_CLASS = "smv__participantRow";
    public static final String MATCH_EVENT_HOME_CLASS = "smv__homeParticipant";
    public static final String MATCH_EVENT_AWAY_CLASS = "smv__awayParticipant";
    public static final By MATCH_EVENT_MINUTE_SELECTOR = By.xpath(".//*[@class='smv__timeBox']");
    public static final By MATCH_EVENT_GOAL_SELECTOR = By.xpath(".//*[@data-testid='wcl-icon-soccer']");
    public static final By MATCH_EVENT_CARD_SELECTOR = By.xpath(".//*[contains(@class,'card-ico')]");
    public static final By MATCH_EVENT_SUBSTITUTION_SELECTOR = By.xpath(".//*[contains(@class,'substitution')]");
    public static final By MATCH_EVENT_PENALTY_SELECTOR = By.xpath(".//*[contains(text(),'Penalty')]");
    public static final By MATCH_EVENT_GOAL_PLAYER_SELECTOR = By.xpath(".//*[contains(@class,'smv__incident')]/a");
    public static final By MATCH_EVENT_GOAL_ASSIST_SELECTOR = By.xpath(".//*[contains(@class,'smv__assist')]/a");
    public static final By MATCH_EVENT_CARD_PLAYER_SELECTOR = By.xpath(".//*[@class='smv__playerName']");
    public static final String MATCH_EVENT_YELLOW_CARD_CLASS = "yellowCard-ico";
    public static final String MATCH_EVENT_RED_CARD_CLASS = "redCard-ico";
    public static final By MATCH_EVENT_SUBSTITUTION_IN_PLAYER_SELECTOR = By.xpath(".//*[@class='smv__incident']/a");
    public static final By MATCH_EVENT_SUBSTITUTION_OUT_PLAYER_SELECTOR = By.xpath(".//*[contains(@class, 'smv__subDown')]");
    public static final By MATCH_EVENT_PENALTY_PLAYER_SELECTOR = By.xpath(".//*[contains(@class, 'smv__playerName')]");

    public static final By TEAM_NAME_SELECTOR = By.xpath("//*[@class='heading__name']");
    public static final By TEAM_STADIUM_SELECTOR = By.xpath("//*[@class='heading__info']/*[1]");
    public static final By TEAM_STADIUM_CAPACITY_SELECTOR = By.xpath("//*[@class='heading__info']/*[2]");
    public static final By TEAM_COACH_SELECTOR = By.xpath("//*[text()='Coach']/..//*[contains(@class, 'lineupTable__cell--player')]//*[@class='lineupTable__cell--name']");
    public static final By TEAM_PLAYERS_SELECTOR = By.xpath("//*[@class='lineupTable__row']//*[contains(@class, 'lineupTable__cell--player')]//*[@class='lineupTable__cell--name']");

    public static final By PLAYER_NAME_SELECTOR = By.xpath("//*[@class='playerHeader__nameWrapper']");
    public static final By PLAYER_ROLE_SELECTOR = By.xpath("//*[@data-testid='wcl-scores-simpleText-01']");
    public static final By PLAYER_BIRTHDATE_SELECTOR = By.xpath("//*[@class='playerInfoItem']/span[2]");

}
