package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.stream.Collectors;

import static com.github.brunomndantas.flashscore.api.dataAccess.FlashscoreConstants.*;

public class TeamScrapperRepository extends ScrapperRepository<TeamKey, Team> {

    public TeamScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected String getUrlOfEntity(TeamKey teamKey) {
        return TEAM_URL.replace(TEAM_ID_PLACEHOLDER, teamKey.getTeamId());
    }

    @Override
    protected Team scrapEntity(WebDriver driver, TeamKey teamKey) {
        Team team = new Team();

        team.setKey(teamKey);
        team.setName(scrapName(driver));
        team.setCoachKey(scrapCoachKey(driver, teamKey));
        team.setPlayersKeys(scrapPlayersKeys(driver, teamKey, team.getCoachKey()));

        return team;
    }

    protected String scrapName(WebDriver driver) {
        WebElement element = driver.findElement(TEAM_NAME_SELECTOR);
        return element.getText();
    }

    protected PlayerKey scrapCoachKey(WebDriver driver, TeamKey teamKey) {
        String url = TEAM_SQUAD_URL.replace(TEAM_ID_PLACEHOLDER, teamKey.getTeamId());
        driver.get(url);
        super.waitPageToBeLoaded(driver);

        Collection<WebElement> elements = driver.findElements(TEAM_COACH_SELECTOR);
        WebElement element = elements.stream().findFirst().get();
        return getPlayerKeyOfElement(element);
    }

    protected Collection<PlayerKey> scrapPlayersKeys(WebDriver driver, TeamKey teamKey, PlayerKey coachKey) {
        String url = TEAM_SQUAD_URL.replace(TEAM_ID_PLACEHOLDER, teamKey.getTeamId());
        driver.get(url);
        super.waitPageToBeLoaded(driver);

        Collection<WebElement> elements = driver.findElements(TEAM_PLAYERS_SELECTOR);
        return elements
                .stream()
                .map(this::getPlayerKeyOfElement)
                .filter(key -> !key.getPlayerId().equals(coachKey.getPlayerId()))
                .collect(Collectors.toSet());
    }

    protected PlayerKey getPlayerKeyOfElement(WebElement element) {
        String href = element.getAttribute("href");
        href = StringUtils.splitByWholeSeparatorPreserveAllTokens(href, "player", 2)[1];
        href = href.split("/")[1] + "/" + href.split("/")[2];
        return new PlayerKey(href);
    }

}