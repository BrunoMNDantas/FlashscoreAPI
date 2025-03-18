package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.competition;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.FlashscorePage;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;

public class CompetitionPage extends FlashscorePage {

    public static final By NAME_SELECTOR = By.xpath("//*[@class='heading__name']");
    public static final By SEASONS_LINKS_SELECTOR = By.xpath("//*[@class = 'archive__row']//*[@class = 'archive__season']//a");


    private CompetitionKey competitionKey;


    public CompetitionPage(WebDriver driver, CompetitionKey competitionKey) {
        super(driver, FlashscoreURLs.getCompetitionURL(competitionKey));
        this.competitionKey = competitionKey;
    }


    public String getName() {
        return getValue(NAME_SELECTOR, null, WebElement::getText);
    }

    public Collection<SeasonKey> getSeasonKeys() {
        return getValues(SEASONS_LINKS_SELECTOR, element -> {
            String text = element.getText().trim();
            String[] parts = text.split(" ");
            String season = parts[parts.length - 1];
            String seasonId = season.replace("/", "-");
            return new SeasonKey(competitionKey.getSportId(), competitionKey.getRegionId(), competitionKey.getCompetitionId(), seasonId);
        });
    }

}