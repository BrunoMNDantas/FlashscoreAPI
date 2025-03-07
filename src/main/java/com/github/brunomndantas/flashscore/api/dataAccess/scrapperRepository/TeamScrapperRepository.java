package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreSelectors;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.stream.Collectors;

public class TeamScrapperRepository extends ScrapperRepository<TeamKey, Team> {

    public TeamScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected String getUrlOfEntity(TeamKey teamKey) {
        return FlashscoreURLs.getTeamURL(teamKey);
    }

    @Override
    protected Team scrapEntity(WebDriver driver, TeamKey teamKey) {
        Team team = new Team();

        team.setKey(teamKey);
        team.setName(scrapName(driver));
        team.setStadium(scrapStadium(driver));
        team.setStadiumCapacity(scrapStadiumCapacity(driver));
        team.setCoachKey(scrapCoachKey(driver, teamKey));
        team.setPlayersKeys(scrapPlayersKeys(driver, teamKey, team.getCoachKey()));

        return team;
    }

    protected String scrapName(WebDriver driver) {
        WebElement element = driver.findElement(FlashscoreSelectors.TEAM_NAME_SELECTOR);
        return element.getText();
    }

    protected String scrapStadium(WebDriver driver) {
        if(!driver.findElements(FlashscoreSelectors.TEAM_STADIUM_SELECTOR).isEmpty()) {
            WebElement element = driver.findElement(FlashscoreSelectors.TEAM_STADIUM_SELECTOR);
            String text = element.getText();
            text = text.replace("Stadium:", "").trim();
            return text;
        }

        return null;
    }

    protected int scrapStadiumCapacity(WebDriver driver) {
        if(!driver.findElements(FlashscoreSelectors.TEAM_STADIUM_CAPACITY_SELECTOR).isEmpty()) {
            WebElement element = driver.findElement(FlashscoreSelectors.TEAM_STADIUM_CAPACITY_SELECTOR);
            String text = element.getText();
            text = text.replace("Capacity:", "");
            text = text.replace(" ", "").trim();
            return Integer.parseInt(text);
        }

        return -1;
    }

    protected PlayerKey scrapCoachKey(WebDriver driver, TeamKey teamKey) {
        String url = FlashscoreURLs.getTeamSquadURL(teamKey);
        driver.get(url);
        super.waitPageToBeLoaded(driver);

        if(!driver.findElements(FlashscoreSelectors.TEAM_COACH_SELECTOR).isEmpty()) {
            WebElement element = driver.findElement(FlashscoreSelectors.TEAM_COACH_SELECTOR);
            return FlashscoreURLs.getPlayerKey(element.getAttribute("href"));
        }

        return null;
    }

    protected Collection<PlayerKey> scrapPlayersKeys(WebDriver driver, TeamKey teamKey, PlayerKey coachKey) {
        String url = FlashscoreURLs.getTeamSquadURL(teamKey);
        driver.get(url);
        super.waitPageToBeLoaded(driver);

        Collection<WebElement> elements = driver.findElements(FlashscoreSelectors.TEAM_PLAYERS_SELECTOR);
        return elements
                .stream()
                .map(element -> element.getAttribute("href"))
                .map(FlashscoreURLs::getPlayerKey)
                .filter(key -> !key.getPlayerId().equals(coachKey.getPlayerId()))
                .collect(Collectors.toSet());
    }

}