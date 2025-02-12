package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.stream.Collectors;

import static com.github.brunomndantas.flashscore.api.dataAccess.FlashscoreConstants.*;

public class TeamScrapperRepository extends ScrapperRepository<String, Team> {

    public TeamScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected String getUrlOfEntity(String teamId) {
        return TEAM_URL.replace(TEAM_ID_PLACEHOLDER, teamId);
    }

    @Override
    protected Team scrapEntity(WebDriver driver, String teamId) {
        Team team = new Team();

        team.setId(teamId);
        team.setName(scrapName(driver));
        team.setCoachId(scrapCoachId(driver, teamId));
        team.setPlayersIds(scrapPlayersIds(driver, teamId, team.getCoachId()));

        return team;
    }

    protected String scrapName(WebDriver driver) {
        WebElement element = driver.findElement(TEAM_NAME_SELECTOR);
        return element.getText();
    }

    protected String scrapCoachId(WebDriver driver, String teamId) {
        String url = TEAM_SQUAD_URL.replace(TEAM_ID_PLACEHOLDER, teamId);
        driver.get(url);
        super.waitPageToBeLoaded(driver);

        Collection<WebElement> elements = driver.findElements(TEAM_COACH_SELECTOR);
        WebElement element = elements.stream().findFirst().get();
        return getPlayerIdOfElement(element);
    }

    protected Collection<String> scrapPlayersIds(WebDriver driver, String teamId, String coachId) {
        String url = TEAM_SQUAD_URL.replace(TEAM_ID_PLACEHOLDER, teamId);
        driver.get(url);
        super.waitPageToBeLoaded(driver);

        Collection<WebElement> elements = driver.findElements(TEAM_PLAYERS_SELECTOR);
        return elements
                .stream()
                .map(this::getPlayerIdOfElement)
                .filter(id -> !id.equals(coachId))
                .collect(Collectors.toSet());
    }

    protected String getPlayerIdOfElement(WebElement element) {
        String href = element.getAttribute("href");
        href = StringUtils.splitByWholeSeparatorPreserveAllTokens(href, "player", 2)[1];
        href = href.split("/")[1] + "/" + href.split("/")[2];
        return href;
    }

}