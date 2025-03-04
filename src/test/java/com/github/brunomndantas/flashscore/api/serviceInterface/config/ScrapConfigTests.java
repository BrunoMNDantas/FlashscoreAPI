package com.github.brunomndantas.flashscore.api.serviceInterface.config;

import com.github.brunomndantas.flashscore.api.logic.services.scrapService.IScrapService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ScrapConfigTests {

    @Autowired
    private IScrapService scrapService;

    @Test
    public void shouldCreateScrapService() {
        Assertions.assertNotNull(scrapService);
    }

}