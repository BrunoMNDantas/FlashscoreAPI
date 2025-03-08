package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

@SpringBootTest
public class TeamScrapperRepositoryTests extends ScrapperRepositoryTests<TeamKey, Team> {

    @Override
    protected ScrapperRepository<TeamKey, Team> createRepository() {
        return createRepository(DRIVER_POOL);
    }

    @Override
    protected ScrapperRepository<TeamKey, Team> createRepository(IDriverPool driverPool) {
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
        ScrapperRepository<TeamKey, Team> repository = createRepository(DRIVER_POOL);

        Team team = repository.get(key);

        Assertions.assertNull(getConstraintViolation(team));

        Assertions.assertEquals(key, team.getKey());
        Assertions.assertEquals("Dortmund II", team.getName());
        Assertions.assertEquals("Stadion Rote Erde (Dortmund)", team.getStadium());
        Assertions.assertEquals(25000, team.getStadiumCapacity());
        Assertions.assertEquals(new PlayerKey("zimmermann-jan", "2F8lpifB"), team.getCoachKey());
        Assertions.assertTrue(team.getPlayersKeys().size() > 20);
        Assertions.assertTrue(team.getPlayersKeys().size() < 35);

        Collection<String> playersIds = team.getPlayersKeys().stream().map(PlayerKey::getPlayerId).toList();
        Assertions.assertFalse(playersIds.contains(team.getCoachKey().getPlayerId()));
    }

}