package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository;

import com.github.brunomndantas.flashscore.api.logic.domain.player.Player;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PlayerScrapperRepositoryTests extends ScrapperRepositoryTests<PlayerKey, Player> {

    @Override
    protected ScrapperRepository<PlayerKey, Player> createRepository() {
        return createRepository(driverPool);
    }

    @Override
    protected ScrapperRepository<PlayerKey, Player> createRepository(IDriverPool driverPool) {
        return new PlayerScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Override
    protected PlayerKey getExistentKey() {
        return new PlayerKey("mainoo-kobbie", "nBy3DbC3");
    }

    @Override
    protected PlayerKey getNonExistentKey() {
        return new PlayerKey("non_existent_name", "non_existent_id");
    }


    @Test
    public void shouldScrapData() throws Exception {
        PlayerKey key = new PlayerKey("gyokeres-viktor", "zaBZ1xIk");
        ScrapperRepository<PlayerKey, Player> repository = createRepository(driverPool);

        Player player = repository.get(key);

        Assertions.assertNull(getConstraintViolation(player));

        Assertions.assertEquals(key, player.getKey());
        Assertions.assertEquals("Viktor Gyokeres", player.getName());
        Assertions.assertEquals("Forward", player.getRole());
        Assertions.assertEquals(PlayerScrapperRepository.DATE_FORMAT.parse("04.06.1998"), player.getBirthDate());
    }

}