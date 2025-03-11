package com.github.brunomndantas.flashscore.api;

import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapperException;
import com.github.brunomndantas.flashscore.api.logic.services.reportViewer.IReportViewer;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.IScrapService;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.Report;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
public class FlashscoreScrapperTests {

    private static class DummyScrapService implements IScrapService {

        public AtomicBoolean scrapCalled = new AtomicBoolean();


        @Override
        public void scrap(Report report) throws EntityScrapperException {
            scrapCalled.set(true);
        }

    }

    private static class DummyReportViewer implements IReportViewer {

        public AtomicBoolean showProgressCalled = new AtomicBoolean();
        public AtomicBoolean showResultCalled = new AtomicBoolean();


        @Override
        public void showProgress(Report report) {
            showProgressCalled.set(true);
        }

        @Override
        public void showResult(Report report) {
            showResultCalled.set(true);
        }

    }


    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) { }
    }


    @Test
    public void shouldSkipScrap() throws Exception {
        DummyScrapService scrapService = new DummyScrapService();
        DummyReportViewer reportViewer = new DummyReportViewer();

        FlashscoreScrapper scrapper = new FlashscoreScrapper(false, 1000, scrapService, reportViewer);
        scrapper.run();

        Assertions.assertFalse(scrapService.scrapCalled.get());
        Assertions.assertFalse(reportViewer.showProgressCalled.get());
        Assertions.assertFalse(reportViewer.showResultCalled.get());
    }

    @Test
    public void shouldShowProgressAndResult() throws Exception {
        DummyScrapService scrapService = new DummyScrapService();
        DummyReportViewer reportViewer = new DummyReportViewer();

        FlashscoreScrapper scrapper = new FlashscoreScrapper(true, 1000, scrapService, reportViewer);
        scrapper.run();

        Assertions.assertTrue(scrapService.scrapCalled.get());
        Assertions.assertTrue(reportViewer.showProgressCalled.get());
        Assertions.assertTrue(reportViewer.showResultCalled.get());
    }

    @Test
    public void shouldShowProgressWhileScrapNotFinished() throws Exception {
        AtomicInteger progressCallCounter = new AtomicInteger(0);
        IScrapService scrapService = report -> sleep(3000);
        IReportViewer reportViewer = new IReportViewer() {
            @Override
            public void showProgress(Report report) {
                progressCallCounter.incrementAndGet();
            }

            @Override
            public void showResult(Report report) { }
        };

        FlashscoreScrapper scrapper = new FlashscoreScrapper(true, 500, scrapService, reportViewer);
        scrapper.run();

        Assertions.assertTrue(progressCallCounter.get() > 1);
    }

}