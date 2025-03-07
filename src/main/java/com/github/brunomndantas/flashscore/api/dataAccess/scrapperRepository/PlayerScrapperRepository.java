package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreSelectors;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
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

public class PlayerScrapperRepository extends ScrapperRepository<PlayerKey, Player> {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");


    public PlayerScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected String getUrlOfEntity(PlayerKey playerKey) {
        return FlashscoreURLs.getPlayerURL(playerKey);
    }

    @Override
    protected Player scrapEntity(WebDriver driver, PlayerKey playerKey) throws RepositoryException {
        Player player = new Player();

        player.setKey(playerKey);
        player.setName(scrapName(driver));
        player.setBirthDate(scrapBirthDate(driver));
        player.setRole(scrapRole(driver));

        return player;
    }

    protected String scrapName(WebDriver driver) {
        WebElement element = driver.findElement(FlashscoreSelectors.PLAYER_NAME_SELECTOR);
        return element.getText();
    }

    protected Date scrapBirthDate(WebDriver driver) throws RepositoryException {
        WebElement element = driver.findElement(FlashscoreSelectors.PLAYER_BIRTHDATE_SELECTOR);
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

    protected String scrapRole(WebDriver driver) {
        WebElement element = driver.findElement(FlashscoreSelectors.PLAYER_ROLE_SELECTOR);
        return element.getText().trim();
    }

}