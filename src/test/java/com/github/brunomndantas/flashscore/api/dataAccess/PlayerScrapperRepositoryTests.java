package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.player.Player;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PlayerScrapperRepositoryTests extends ScrapperRepositoryTests<String, Player> {

    @Override
    protected ScrapperRepository<String, Player> createRepository() {
        return createRepository(driverPool);
    }

    @Override
    protected ScrapperRepository<String, Player> createRepository(IDriverPool driverPool) {
        return new PlayerScrapperRepository(driverPool, screenshotsDirectory);
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
        ScrapperRepository<String, Player> repository = createRepository(driverPool);

        Player player = repository.get(key);

        Assertions.assertEquals(key, player.getId());
        Assertions.assertEquals("Viktor Gyokeres", player.getName());
        Assertions.assertEquals(PlayerScrapperRepository.DATE_FORMAT.parse("04.06.1998"), player.getBirthDate());
    }

}