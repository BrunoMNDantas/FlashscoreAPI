package com.github.brunomndantas.flashscore.api.logic.services.entityScrapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EntityScrapperExceptionTests {

    @Test
    public void shouldHaveNoMessageAndNoCause() {
        EntityScrapperException exception = new EntityScrapperException();

        Assertions.assertNull(exception.getMessage());
        Assertions.assertNull(exception.getCause());
    }

    @Test
    public void shouldByPassMessage() {
        String message = "Message";

        EntityScrapperException exception = new EntityScrapperException(message);

        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    public void shouldByMessageAndCause() {
        String message = "Message";
        Throwable cause = new Exception();

        EntityScrapperException exception = new EntityScrapperException(message, cause);

        Assertions.assertEquals(message, exception.getMessage());
        Assertions.assertEquals(cause, exception.getCause());
    }

}
