package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.player;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.ScrapperRepository;
import com.github.brunomndantas.flashscore.api.logic.domain.player.Player;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.openqa.selenium.WebDriver;

public class PlayerScrapperRepository extends ScrapperRepository<PlayerKey, Player, PlayerPage> {

    public PlayerScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected PlayerPage getPage(WebDriver driver, PlayerKey key) {
        return new PlayerPage(driver, key);
    }

    @Override
    protected Player scrapEntity(WebDriver driver, PlayerPage page, PlayerKey playerKey) {
        Player player = new Player();

        player.setKey(playerKey);
        player.setName(page.getName());
        player.setBirthDate(page.getBirthDate());
        player.setRole(page.getRole());

        return player;
    }

}