package com.github.brunomndantas.flashscore.api.dataAccess.constraintViolationRepository;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConstraintViolationExceptionTests {

    @Test
    public void shouldHaveNoMessageAndNoCause() {
        ConstraintViolationException exception = new ConstraintViolationException();

        Assertions.assertNull(exception.getMessage());
        Assertions.assertNull(exception.getCause());
    }

    @Test
    public void shouldByPassMessage() {
        String message = "Message";

        ConstraintViolationException exception = new ConstraintViolationException(message);

        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    public void shouldByMessageAndCause() {
        String message = "Message";
        Throwable cause = new Exception();

        ConstraintViolationException exception = new ConstraintViolationException(message, cause);

        Assertions.assertEquals(message, exception.getMessage());
        Assertions.assertEquals(cause, exception.getCause());
    }

}

