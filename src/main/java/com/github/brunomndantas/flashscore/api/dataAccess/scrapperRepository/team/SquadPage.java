package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.team;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.FlashscorePage;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Collection;
import java.util.stream.Collectors;

public class SquadPage extends FlashscorePage {

    public static final By COACH_SELECTOR = By.xpath("//*[text()='Coach']/..//*[contains(@class, 'lineupTable__cell--player')]//*[@class='lineupTable__cell--name']");
    public static final By PLAYERS_SELECTOR = By.xpath("//*[@class='lineupTable__row']//*[contains(@class, 'lineupTable__cell--player')]//*[@class='lineupTable__cell--name']");


    private TeamKey teamKey;


    public SquadPage(WebDriver driver, TeamKey teamKey) {
        super(driver, FlashscoreURLs.getTeamSquadURL(teamKey));
        this.teamKey = teamKey;
    }


    public PlayerKey getCoachKey() {
        return getValue(COACH_SELECTOR, null, element -> FlashscoreURLs.getPlayerKey(element.getAttribute("href")));
    }

    public Collection<PlayerKey> getPlayersKeys() {
        PlayerKey coachKey = getCoachKey();

        Collection<PlayerKey> keys = getValues(PLAYERS_SELECTOR, element -> {
            String href = element.getAttribute("href");
            return FlashscoreURLs.getPlayerKey(href);
        });

        return keys.stream()
                .filter(key -> coachKey == null || !key.getPlayerId().equals(coachKey.getPlayerId()))
                .collect(Collectors.toSet());
    }

}