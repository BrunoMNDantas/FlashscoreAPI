package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.player.Player;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerId;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PlayerScrapperRepositoryTests extends ScrapperRepositoryTests<PlayerId, Player> {

    @Override
    protected ScrapperRepository<PlayerId, Player> createRepository() {
        return createRepository(DRIVER_POOL);
    }

    @Override
    protected ScrapperRepository<PlayerId, Player> createRepository(IDriverPool driverPool) {
        return new PlayerScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Override
    protected PlayerId getExistentKey() {
        return new PlayerId("mainoo-kobbie/nBy3DbC3");
    }

    @Override
    protected PlayerId getNonExistentKey() {
        return new PlayerId("non_existent_id");
    }


    @Test
    public void shouldScrapData() throws Exception {
        PlayerId key = new PlayerId("gyokeres-viktor/zaBZ1xIk");
        ScrapperRepository<PlayerId, Player> repository = createRepository(DRIVER_POOL);

        Player player = repository.get(key);

        Assertions.assertEquals(key.getId(), player.getId().getId());
        Assertions.assertEquals("Viktor Gyokeres", player.getName());
        Assertions.assertEquals(PlayerScrapperRepository.DATE_FORMAT.parse("04.06.1998"), player.getBirthDate());
    }

}