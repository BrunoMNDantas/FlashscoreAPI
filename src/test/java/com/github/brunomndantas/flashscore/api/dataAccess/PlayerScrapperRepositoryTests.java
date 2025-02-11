package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.player.Player;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPool;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerScrapperRepositoryTests extends ScrapperRepositoryTests<String, Player> {

    protected static final IDriverPool DRIVER_POOL = new DriverPool(DRIVER_SUPPLIER, 1);


    @AfterAll
    public static void dispose() throws Exception {
        DRIVER_POOL.close();
    }


    @Override
    protected ScrapperRepository<String, Player> createRepository() {
        return createRepository(DRIVER_POOL);
    }

    @Override
    protected ScrapperRepository<String, Player> createRepository(IDriverPool driverPool) {
        return new PlayerScrapperRepository(driverPool, SCREEN_SHOOTS_DIRECTORY);
    }

    @Override
    protected String getExistentKey() {
        return "mainoo-kobbie/nBy3DbC3";
    }

    @Override
    protected String getNonExistentKey() {
        return "non_existent_id";
    }


    @Test
    public void shouldScrapData() throws Exception {
        String key = "gyokeres-viktor/zaBZ1xIk";
        ScrapperRepository<String, Player> repository = createRepository(DRIVER_POOL);

        Player player = repository.get(key);

        Assertions.assertEquals(key, player.getId());
        Assertions.assertEquals("Viktor Gyokeres", player.getName());
        Assertions.assertEquals(PlayerScrapperRepository.DATE_FORMAT.parse("04.06.1998"), player.getBirthDate());
    }

}