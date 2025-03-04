package com.github.brunomndantas.flashscore.api.logic.services.entityScrapper;

import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.memory.MemoryRepository;
import com.github.brunomndantas.repository4j.threadSafe.ThreadSafeRepository;
import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.task.ParallelTask;
import com.github.brunomndantas.tpl4j.task.pool.TaskPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityScrapperTests {

    protected static final TaskPool TASK_POOL = new TaskPool(2);


    @AfterAll
    public static void finish() {
        TASK_POOL.close();
    }


    @Test
    public void shouldLoadAllEntities() throws Exception {
        IRepository<String,String> repository = new ThreadSafeRepository<>(new MemoryRepository<>(key -> key));
        IEntityScrapper<String,String> scrapper = new EntityScrapper<>(repository, TASK_POOL);
        EntityReport<String,String> entityReport = new EntityReport<>();

        entityReport.addEntityToLoad("1");
        entityReport.addEntityToLoad("2");
        entityReport.addEntityToLoad("3");

        scrapper.scrap(entityReport);

        Assertions.assertEquals(entityReport.getEntitiesToLoad().size(), entityReport.getSucceededLoads().size());
    }

    @Test
    public void shouldReportFailedLoadsAllEntities() throws Exception {
        IRepository<String,String> repository = new ThreadSafeRepository<>(new MemoryRepository<>(key -> key){
            @Override
            public String get(String key) {
                throw new RuntimeException(key);
            }
        });
        IEntityScrapper<String,String> scrapper = new EntityScrapper<>(repository, TASK_POOL);
        EntityReport<String,String> report = new EntityReport<>();
        String key1 = "1";
        String key2 = "2";

        report.addEntityToLoad(key1);
        report.addEntityToLoad(key2);

        scrapper.scrap(report);

        Assertions.assertTrue(report.getFailedLoads().containsKey(key1));
        Assertions.assertEquals(key1, report.getFailedLoads().get(key1).getMessage());
        Assertions.assertTrue(report.getFailedLoads().containsKey(key2));
        Assertions.assertEquals(key2, report.getFailedLoads().get(key2).getMessage());
    }

    @Test
    public void shouldReportSucceededLoadsAllEntities() throws Exception {
        IRepository<String,String> repository = new ThreadSafeRepository<>(new MemoryRepository<>(key -> key));
        IEntityScrapper<String,String> scrapper = new EntityScrapper<>(repository, TASK_POOL);
        EntityReport<String,String> report = new EntityReport<>();
        String key1 = "1";
        String key2 = "2";

        repository.insert(key1);
        repository.insert(key2);

        report.addEntityToLoad(key1);
        report.addEntityToLoad(key2);

        scrapper.scrap(report);

        Assertions.assertTrue(report.getSucceededLoads().containsKey(key1));
        Assertions.assertSame(key1, report.getSucceededLoads().get(key1));
        Assertions.assertTrue(report.getSucceededLoads().containsKey(key2));
        Assertions.assertSame(key2, report.getSucceededLoads().get(key2));
    }

    @Test
    public void shouldLoadEntitiesInParallel() throws Exception {
        long sleepTime = 3 * 1000;
        IRepository<String,String> repository = new ThreadSafeRepository<>(new MemoryRepository<>(key -> key){
            @Override
            public String get(String key) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) { }
                return key;
            }
        });
        IEntityScrapper<String,String> scrapper = new EntityScrapper<>(repository, TASK_POOL);
        EntityReport<String,String> report = new EntityReport<>();
        String key1 = "1";
        String key2 = "2";

        report.addEntityToLoad(key1);
        report.addEntityToLoad(key2);

        long timeBefore = System.currentTimeMillis();
        scrapper.scrap(report);
        long timeAfter = System.currentTimeMillis();
        long time = timeAfter - timeBefore;

        Assertions.assertTrue(time < sleepTime * report.getEntitiesToLoad().size());
    }

    @Test
    public void shouldWrapTaskException() {
        RuntimeException exception = new RuntimeException("Error");
        TaskPool taskPool = new TaskPool(1){
            @Override
            public <T, K> ParallelTask<T, K> forEach(String taskId, Iterable<T> elements, IParallelAction<T, K> action, ICancellationToken cancellationToken, Option... options) {
                throw exception;
            }
        };

        IEntityScrapper<String,String> scrapper = new EntityScrapper<>(null, taskPool);

        try {
            Exception returnedException = Assertions.assertThrows(EntityScrapperException.class, () -> scrapper.scrap(new EntityReport<>()));
            Assertions.assertSame(exception, returnedException.getCause());
        } finally {
           taskPool.close();
        }
    }

}