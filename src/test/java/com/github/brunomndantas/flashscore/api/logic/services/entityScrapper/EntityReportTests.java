package com.github.brunomndantas.flashscore.api.logic.services.entityScrapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
public class EntityReportTests {

    @Test
    public void shouldAddEntityToLoad() {
        EntityReport<String, String> report = new EntityReport<>();

        report.addEntityToLoad("1");

        Assertions.assertEquals(1, report.getEntitiesToLoad().size());
    }

    @Test
    public void shouldNotDuplicateEntities() {
        EntityReport<String, String> report = new EntityReport<>();

        report.addEntityToLoad("1");
        report.addEntityToLoad("1");

        Assertions.assertEquals(1, report.getEntitiesToLoad().size());
    }

    @Test
    public void shouldAddFailedLoad() {
        EntityReport<String, String> report = new EntityReport<>();
        String key = "1";
        Exception error = new Exception();

        report.addFailedLoad(key, error);

        Assertions.assertEquals(1, report.getFailedLoads().size());
        Assertions.assertSame(error, report.getFailedLoads().get(key));
    }

    @Test
    public void shouldAddSucceededLoad() {
        EntityReport<String, String> report = new EntityReport<>();
        String key = "1";
        String entity = "One";

        report.addSucceededLoad(key, entity);

        Assertions.assertEquals(1, report.getSucceededLoads().size());
        Assertions.assertSame(entity, report.getSucceededLoads().get(key));
    }

    @Test
    public void shouldNotAllowNullKeyOnAddEntityToLoad() {
        EntityReport<String, String> report = new EntityReport<>();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> report.addEntityToLoad(null));

        Assertions.assertEquals("Key cannot be null!", exception.getMessage());
    }

    @Test
    public void shouldNotAllowNullKeyOnAddSucceededLoad() {
        EntityReport<String, String> report = new EntityReport<>();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> report.addSucceededLoad(null, ""));

        Assertions.assertEquals("Key cannot be null!", exception.getMessage());
    }

    @Test
    public void shouldNotAllowNullKeyOnAddFailedLoad() {
        EntityReport<String, String> report = new EntityReport<>();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> report.addFailedLoad(null, new Exception()));

        Assertions.assertEquals("Key cannot be null!", exception.getMessage());
    }

    @Test
    public void shouldReturnClonesOfEntitiesToLoad() {
        EntityReport<String, String> report = new EntityReport<>();

        Collection<String> entitiesToLoad1 = report.getEntitiesToLoad();
        Collection<String> entitiesToLoad2 = report.getEntitiesToLoad();

        Assertions.assertEquals(entitiesToLoad1.size(), entitiesToLoad2.size());
        Assertions.assertNotSame(entitiesToLoad1, entitiesToLoad2);
    }

    @Test
    public void shouldReturnClonesOfFailedLoads() {
        EntityReport<String, String> report = new EntityReport<>();

        Map<String, Exception> failedLoads1 = report.getFailedLoads();
        Map<String, Exception> failedLoads2 = report.getFailedLoads();

        Assertions.assertEquals(failedLoads1.size(), failedLoads2.size());
        Assertions.assertNotSame(failedLoads1, failedLoads2);
    }

    @Test
    public void shouldReturnClonesOfSucceededLoads() {
        EntityReport<String, String> report = new EntityReport<>();

        Map<String, String> succeededLoads1 = report.getSucceededLoads();
        Map<String, String> succeededLoads2 = report.getSucceededLoads();

        Assertions.assertEquals(succeededLoads1.size(), succeededLoads2.size());
        Assertions.assertNotSame(succeededLoads1, succeededLoads2);
    }

    @Test
    public void shouldBeThreadSafe() throws InterruptedException {
        int numberOfIterations = 1000;
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(numberOfThreads);
        EntityReport<String, String> report = new EntityReport<>();

        for(int i=0; i<numberOfThreads; ++i) {
            final int id = i;
            new Thread(() -> {
                try {
                    for(int j=0; j<numberOfIterations; ++j) {
                        report.addEntityToLoad(id + "-" + j);
                        report.addFailedLoad(id + "-" + j, new Exception());
                        report.addSucceededLoad(id + "-" + j, id + "-" + j);
                    }
                } finally {
                    end.countDown();
                }
            }).start();
        }

        start.countDown();
        end.await();

        Assertions.assertEquals(numberOfThreads * numberOfIterations, report.getEntitiesToLoad().size());
        Assertions.assertEquals(numberOfThreads * numberOfIterations, report.getFailedLoads().size());
        Assertions.assertEquals(numberOfThreads * numberOfIterations, report.getSucceededLoads().size());
    }

}