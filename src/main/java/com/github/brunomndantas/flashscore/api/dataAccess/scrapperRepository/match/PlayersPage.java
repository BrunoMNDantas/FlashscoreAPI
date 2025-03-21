package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.match;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.FlashscorePage;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Collection;
import java.util.stream.Collectors;

public class PlayersPage extends FlashscorePage {

    public static final By HOME_COACH_SELECTOR = By.xpath("//*[text()='Coaches']/../..//*[@class='lf__side'][1]//a[contains(@class, 'nameWrapper')]");
    public static final By AWAY_COACH_SELECTOR = By.xpath("//*[text()='Coaches']/../..//*[@class='lf__side'][2]//a[contains(@class, 'nameWrapper')]");
    public static final By HOME_LINEUP_SELECTOR = By.xpath("//*[text()='Starting Lineups']/../..//*[@class='lf__side'][1]//a[contains(@class, 'nameWrapper')]");
    public static final By AWAY_LINEUP_SELECTOR = By.xpath("//*[text()='Starting Lineups']/../..//*[@class='lf__side'][2]//a[contains(@class, 'nameWrapper')]");
    public static final By HOME_BENCH_SELECTOR = By.xpath("//*[text()='Substitutes']/../..//*[@class='lf__side'][1]//a[contains(@class, 'nameWrapper')]");
    public static final By AWAY_BENCH_SELECTOR = By.xpath("//*[text()='Substitutes']/../..//*[@class='lf__side'][2]//a[contains(@class, 'nameWrapper')]");


    private MatchKey matchKey;


    public PlayersPage(WebDriver driver, MatchKey matchKey) {
        super(driver, FlashscoreURLs.getMatchPlayersURL(matchKey));
        this.matchKey = matchKey;
    }


    public PlayerKey getHomeCoachKey() {
        return getValue(HOME_COACH_SELECTOR, null, element -> FlashscoreURLs.getPlayerKey(element.getAttribute("href")));
    }

    public PlayerKey getAwayCoachKey() {
        return getValue(AWAY_COACH_SELECTOR, null, element -> FlashscoreURLs.getPlayerKey(element.getAttribute("href")));
    }

    public Collection<PlayerKey> getHomeLineupPlayersKeys() {
        Collection<PlayerKey> keys = getValues(HOME_LINEUP_SELECTOR, element -> {
            String href = element.getAttribute("href");
            return FlashscoreURLs.getPlayerKey(href);
        });

        return keys.stream().collect(Collectors.toSet());
    }

    public Collection<PlayerKey> getAwayLineupPlayersKeys() {
        Collection<PlayerKey> keys = getValues(AWAY_LINEUP_SELECTOR, element -> {
            String href = element.getAttribute("href");
            return FlashscoreURLs.getPlayerKey(href);
        });

        return keys.stream().collect(Collectors.toSet());
    }

    public Collection<PlayerKey> getHomeBenchPlayers() {
        Collection<PlayerKey> keys = getValues(HOME_BENCH_SELECTOR, element -> {
            String href = element.getAttribute("href");
            return FlashscoreURLs.getPlayerKey(href);
        });

        return keys.stream().collect(Collectors.toSet());
    }

    public Collection<PlayerKey> getAwayBenchPlayers() {
        Collection<PlayerKey> keys = getValues(AWAY_BENCH_SELECTOR, element -> {
            String href = element.getAttribute("href");
            return FlashscoreURLs.getPlayerKey(href);
        });

        return keys.stream().collect(Collectors.toSet());
    }

}