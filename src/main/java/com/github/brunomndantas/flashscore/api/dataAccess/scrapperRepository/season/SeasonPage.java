package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.season;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.FlashscorePage;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SeasonPage extends FlashscorePage {

    public static final By YEARS_SELECTOR = By.xpath("//*[@class='heading__info']");


    private SeasonKey seasonKey;


    public SeasonPage(WebDriver driver, SeasonKey seasonKey) {
        super(driver, FlashscoreURLs.getSeasonURL(seasonKey));
        this.seasonKey = seasonKey;
    }


    public int getInitYear() {
        return getValue(YEARS_SELECTOR, -1, element -> {
            String text = element.getText();
            String year = text.contains("/") ? text.split("/")[0] : text;
            return Integer.parseInt(year);
        });
    }

    public int getEndYear() {
        return getValue(YEARS_SELECTOR, -1, element -> {
            String text = element.getText();
            String year = text.contains("/") ? text.split("/")[1] : text;
            return Integer.parseInt(year);
        });
    }

}