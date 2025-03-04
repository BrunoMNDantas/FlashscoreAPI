package com.github.brunomndantas.flashscore.api.serviceInterface.config;

import com.github.brunomndantas.flashscore.api.logic.services.reportViewer.IReportViewer;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.IScrapService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ScrapConfigTests {

    @Autowired
    private IScrapService scrapService;

    @Autowired
    private IReportViewer reportViewer;


    @Test
    public void shouldCreateScrapService() {
        Assertions.assertNotNull(scrapService);
    }

    @Test
    public void shouldCreateReportViewer() {
        Assertions.assertNotNull(reportViewer);
    }

}