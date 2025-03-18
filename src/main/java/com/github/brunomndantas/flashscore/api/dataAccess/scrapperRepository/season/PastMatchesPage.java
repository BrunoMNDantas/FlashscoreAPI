package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.season;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.FlashscorePage;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreURLs;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.utils.FlashscoreUtils;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Collection;

public class PastMatchesPage extends FlashscorePage {

    public static final By MATCHES_SELECTOR = By.xpath("//*[contains(@class, 'event__match')]/a[@class = 'eventRowLink']");


    private SeasonKey seasonKey;


    public PastMatchesPage(WebDriver driver, SeasonKey seasonKey) {
        super(driver, FlashscoreURLs.getSeasonPastMatchesURL(seasonKey));
        this.seasonKey = seasonKey;
    }


    public Collection<MatchKey> getMatchesKeys() {
        FlashscoreUtils.loadAllMatches(driver);
        return getValues(MATCHES_SELECTOR, element -> {
            String href = element.getAttribute("href");
            return FlashscoreURLs.getMatchKey(href);
        });
    }

}