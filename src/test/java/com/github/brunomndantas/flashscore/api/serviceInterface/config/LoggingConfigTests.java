package com.github.brunomndantas.flashscore.api.serviceInterface.config;

import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoggingConfigTests {


    private static final String SHORT_NAME = "mockedMethod()";
    private static final Object[] ARGUMENTS = new Object[]{"arg1", 123};


    @Mock
    private Logger mockLogger;
    @InjectMocks
    private LoggingConfig loggingConfig;
    @Mock
    private JoinPoint joinPoint;
    @Mock
    private Signature signature;


    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field field = loggingConfig.getClass().getDeclaredField("LOG");
        field.setAccessible(true);
        field.set(loggingConfig, mockLogger);
    }

    @Test
    public void shouldLogMethodEntry() {
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.toShortString()).thenReturn(SHORT_NAME);
        when(joinPoint.getArgs()).thenReturn(ARGUMENTS);

        loggingConfig.logMethodEntry(joinPoint);

        verify(mockLogger).debug(LoggingConfig.ENTER_MESSAGE_TEMPLATE, SHORT_NAME, ARGUMENTS);
    }

    @Test
    public void shouldLogMethodExit() {
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.toShortString()).thenReturn(SHORT_NAME);

        String result = "Success";
        loggingConfig.logMethodExit(joinPoint, result);

        verify(mockLogger).debug(LoggingConfig.EXIT_MESSAGE_TEMPLATE, SHORT_NAME, result);
    }

    @Test
    void shouldLogMethodException() {
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.toShortString()).thenReturn(SHORT_NAME);

        Exception exception = new RuntimeException("Test Exception");

        loggingConfig.logMethodException(joinPoint, exception);

        verify(mockLogger).debug(LoggingConfig.EXCEPTION_MESSAGE_TEMPLATE, SHORT_NAME, exception.getMessage());
    }

}