package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.team;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.ScrapperRepository;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.ScrapperRepositoryTests;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TeamScrapperRepositoryTests extends ScrapperRepositoryTests<TeamKey, Team, TeamPage> {

    @Override
    protected ScrapperRepository<TeamKey, Team, TeamPage> createRepository() {
        return createRepository(driverPool);
    }

    @Override
    protected ScrapperRepository<TeamKey, Team, TeamPage> createRepository(IDriverPool driverPool) {
        return new TeamScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Override
    protected TeamKey getExistentKey() {
        return new TeamKey("turkspor-dortmund", "0nkpeeFd");
    }

    @Override
    protected TeamKey getNonExistentKey() {
        return new TeamKey("non_existent_name", "non_existent_id");
    }


    @Test
    public void shouldScrapData() throws Exception {
        TeamKey key = new TeamKey("dortmund", "vVcwNP6f");
        ScrapperRepository<TeamKey, Team, TeamPage> repository = createRepository(driverPool);

        Team team = repository.get(key);

        Assertions.assertNull(getConstraintViolation(team));

        Assertions.assertEquals(key, team.getKey());
        Assertions.assertEquals("Dortmund II", team.getName());
        Assertions.assertEquals("Stadion Rote Erde (Dortmund)", team.getStadium());
        Assertions.assertEquals(25000, team.getStadiumCapacity());
        Assertions.assertEquals(new PlayerKey("zimmermann-jan", "2F8lpifB"), team.getCoachKey());
        Assertions.assertTrue(team.getPlayersKeys().size() > 20);
        Assertions.assertTrue(team.getPlayersKeys().size() < 35);
    }

}