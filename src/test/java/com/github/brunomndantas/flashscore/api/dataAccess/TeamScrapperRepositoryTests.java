package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPool;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TeamScrapperRepositoryTests extends ScrapperRepositoryTests<String, Team> {

    protected static final IDriverPool DRIVER_POOL = new DriverPool(DRIVER_SUPPLIER, 1);


    @AfterAll
    public static void dispose() throws Exception {
        DRIVER_POOL.close();
    }


    @Override
    protected ScrapperRepository<String, Team> createRepository() {
        return createRepository(DRIVER_POOL);
    }

    @Override
    protected ScrapperRepository<String, Team> createRepository(IDriverPool driverPool) {
        return new TeamScrapperRepository(driverPool, SCREEN_SHOOTS_DIRECTORY);
    }

    @Override
    protected String getExistentKey() {

        return "bradford-city/bsc3EJ27";
    }

    @Override
    protected String getNonExistentKey() {
        return "non_existent_id";
    }


    @Test
    public void shouldScrapData() throws Exception {
        String key = "dortmund/vVcwNP6f";
        ScrapperRepository<String, Team> repository = createRepository(DRIVER_POOL);

        Team team = repository.get(key);

        Assertions.assertEquals(key, team.getId());
        Assertions.assertEquals("Dortmund II", team.getName());
        Assertions.assertEquals("zimmermann-jan/2F8lpifB", team.getCoachId());
        Assertions.assertNotNull(team.getPlayersIds());
        Assertions.assertTrue(team.getPlayersIds().size() > 20);
        Assertions.assertTrue(team.getPlayersIds().size() < 35);
        Assertions.assertTrue(!team.getPlayersIds().contains(team.getCoachId()));

        for(String playerId: team.getPlayersIds()) {
            Assertions.assertNotNull(playerId);
            Assertions.assertFalse(playerId.trim().isEmpty());
        }
    }

}