package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerId;
import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamId;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TeamScrapperRepositoryTests extends ScrapperRepositoryTests<TeamId, Team> {

    @Override
    protected ScrapperRepository<TeamId, Team> createRepository() {
        return createRepository(DRIVER_POOL);
    }

    @Override
    protected ScrapperRepository<TeamId, Team> createRepository(IDriverPool driverPool) {
        return new TeamScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Override
    protected TeamId getExistentKey() {
        return new TeamId("bradford-city/bsc3EJ27");
    }

    @Override
    protected TeamId getNonExistentKey() {
        return new TeamId("non_existent_id");
    }


    @Test
    public void shouldScrapData() throws Exception {
        TeamId key = new TeamId("dortmund/vVcwNP6f");
        ScrapperRepository<TeamId, Team> repository = createRepository(DRIVER_POOL);

        Team team = repository.get(key);

        Assertions.assertEquals(key.getId(), team.getId().getId());
        Assertions.assertEquals("Dortmund II", team.getName());
        Assertions.assertEquals("zimmermann-jan/2F8lpifB", team.getCoachId().getId());
        Assertions.assertNotNull(team.getPlayersIds());
        Assertions.assertTrue(team.getPlayersIds().size() > 20);
        Assertions.assertTrue(team.getPlayersIds().size() < 35);
        Assertions.assertTrue(team.getPlayersIds().stream().noneMatch(id -> id.getId().equals(team.getCoachId().getId())));

        for(PlayerId playerId: team.getPlayersIds()) {
            Assertions.assertNotNull(playerId);
            Assertions.assertNotNull(playerId.getId());
            Assertions.assertFalse(playerId.getId().trim().isEmpty());
        }
    }

}