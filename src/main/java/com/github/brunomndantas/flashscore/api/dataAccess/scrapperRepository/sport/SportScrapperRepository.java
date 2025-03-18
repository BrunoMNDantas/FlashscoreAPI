package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.sport;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.ScrapperRepository;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.openqa.selenium.WebDriver;

public class SportScrapperRepository extends ScrapperRepository<SportKey, Sport, SportPage> {

    public SportScrapperRepository(IDriverPool driverPool, String logDirectory) {
        super(driverPool, logDirectory);
    }


    @Override
    protected SportPage getPage(WebDriver driver, SportKey key) {
        return new SportPage(driver, key);
    }

    @Override
    protected Sport scrapEntity(WebDriver driver, SportPage page, SportKey sportKey) {
        Sport sport = new Sport();

        sport.setKey(sportKey);
        sport.setName(page.getName());
        sport.setRegionsKeys(page.getRegionsKeys());

        return sport;
    }

}