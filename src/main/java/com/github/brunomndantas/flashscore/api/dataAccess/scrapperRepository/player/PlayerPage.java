package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.player;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.FlashscorePage;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerPage extends FlashscorePage {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public static final By NAME_SELECTOR = By.xpath("//*[@class='playerHeader__nameWrapper']");
    public static final By ROLE_SELECTOR = By.xpath("//*[@data-testid='wcl-scores-simpleText-01']");
    public static final By BIRTHDATE_SELECTOR = By.xpath("//*[@class='playerInfoItem']/span[2]");


    private PlayerKey playerKey;


    public PlayerPage(WebDriver driver, PlayerKey playerKey) {
        super(driver, FlashscoreURLs.getPlayerURL(playerKey));
        this.playerKey = playerKey;
    }


    public String getName() {
        return getValue(NAME_SELECTOR, null, WebElement::getText);
    }

    public Date getBirthDate() {
        return getValue(BIRTHDATE_SELECTOR, null, element -> {
            String text = element.getText();
            text = text.trim();
            text = text.replace("(", "");
            text = text.replace(")", "");

            try {
                return DATE_FORMAT.parse(text);
            } catch (ParseException e) {
                throw new RuntimeException("Error parsing date", e);
            }
        });
    }

    public String getRole() {
        return getValue(ROLE_SELECTOR, null, element -> element.getText().trim());
    }

}