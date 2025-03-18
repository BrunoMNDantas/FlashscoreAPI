package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.team;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.FlashscorePage;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TeamPage extends FlashscorePage {

    public static final By NAME_SELECTOR = By.xpath("//*[@class='heading__name']");
    public static final By STADIUM_SELECTOR = By.xpath("//*[@class='heading__info']/*[1]");
    public static final By STADIUM_CAPACITY_SELECTOR = By.xpath("//*[@class='heading__info']/*[2]");


    private TeamKey teamKey;


    public TeamPage(WebDriver driver, TeamKey teamKey) {
        super(driver, FlashscoreURLs.getTeamURL(teamKey));
        this.teamKey = teamKey;
    }


    public String getName() {
        return getValue(NAME_SELECTOR, null, WebElement::getText);
    }

    public String getStadium() {
        return getValue(STADIUM_SELECTOR, null, element -> {
            String text = element.getText();
            text = text.replace("Stadium:", "").trim();
            return text;
        });
    }

    public int getStadiumCapacity() {
        return getValue(STADIUM_CAPACITY_SELECTOR, -1, element -> {
            String text = element.getText();
            text = text.replace("Capacity:", "");
            text = text.replace(" ", "").trim();
            return Integer.parseInt(text);
        });
    }

}