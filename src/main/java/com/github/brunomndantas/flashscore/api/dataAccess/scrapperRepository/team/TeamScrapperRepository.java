package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.team;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.ScrapperRepository;
import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.openqa.selenium.WebDriver;

public class TeamScrapperRepository extends ScrapperRepository<TeamKey, Team, TeamPage> {

    public TeamScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected TeamPage getPage(WebDriver driver, TeamKey key) {
        return new TeamPage(driver, key);
    }

    @Override
    protected Team scrapEntity(WebDriver driver, TeamPage page, TeamKey teamKey) {
        Team team = new Team();

        team.setKey(teamKey);
        team.setName(page.getName());
        team.setStadium(page.getStadium());
        team.setStadiumCapacity(page.getStadiumCapacity());

        SquadPage squadPage = new SquadPage(driver, teamKey);
        squadPage.load();

        team.setCoachKey(squadPage.getCoachKey());
        team.setPlayersKeys(squadPage.getPlayersKeys());

        return team;
    }

}