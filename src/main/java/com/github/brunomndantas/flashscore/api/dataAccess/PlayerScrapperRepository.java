package com.github.brunomndantas.flashscore.api.dataAccess;

import com.github.brunomndantas.flashscore.api.logic.domain.player.Player;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.brunomndantas.flashscore.api.dataAccess.FlashscoreConstants.*;

public class PlayerScrapperRepository extends ScrapperRepository<PlayerKey, Player> {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");


    public PlayerScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected String getUrlOfEntity(PlayerKey playerKey) {
        return PLAYER_URL.replace(PLAYER_ID_PLACEHOLDER, playerKey.getPlayerId());
    }

    @Override
    protected Player scrapEntity(WebDriver driver, PlayerKey playerKey) throws RepositoryException {
        Player player = new Player();

        player.setKey(playerKey);
        player.setName(scrapName(driver));
        player.setBirthDate(scrapBirthDate(driver));

        return player;
    }

    protected String scrapName(WebDriver driver) {
        WebElement element = driver.findElement(PLAYER_NAME_SELECTOR);
        return element.getText();
    }

    protected Date scrapBirthDate(WebDriver driver) throws RepositoryException {
        WebElement element = driver.findElement(PLAYER_BIRTHDATE_SELECTOR);
        String text = element.getText();
        text = text.trim();
        text = text.replace("(", "");
        text = text.replace(")", "");

        try {
            return DATE_FORMAT.parse(text);
        } catch (ParseException e) {
            throw new RepositoryException("Error parsing date", e);
        }
    }

}