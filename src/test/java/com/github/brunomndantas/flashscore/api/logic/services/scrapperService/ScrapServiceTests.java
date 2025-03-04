package com.github.brunomndantas.flashscore.api.logic.services.scrapperService;

import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapperException;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.IScrapService;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.Report;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.ScrapService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
public class ScrapServiceTests {

   @Test
    public void shouldScrapAllEntities() throws EntityScrapperException {
        AtomicInteger counter = new AtomicInteger();

       IScrapService sportScrapService = report -> Assertions.assertEquals(0, counter.getAndIncrement());
       IScrapService regionScrapService = report -> Assertions.assertEquals(1, counter.getAndIncrement());
       IScrapService competitionScrapService = report -> Assertions.assertEquals(2, counter.getAndIncrement());
       IScrapService seasonScrapService = report -> Assertions.assertEquals(3, counter.getAndIncrement());
       IScrapService matchScrapService = report -> Assertions.assertEquals(4, counter.getAndIncrement());
       IScrapService teamScrapService = report -> Assertions.assertEquals(5, counter.getAndIncrement());
       IScrapService playerScrapService = report -> Assertions.assertEquals(6, counter.getAndIncrement());

       ScrapService service = new ScrapService(
               sportScrapService, regionScrapService, competitionScrapService, seasonScrapService,
               matchScrapService, teamScrapService, playerScrapService
       );

       service.scrap(new Report());
   }

}