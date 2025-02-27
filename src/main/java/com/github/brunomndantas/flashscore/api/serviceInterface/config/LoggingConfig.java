package com.github.brunomndantas.flashscore.api.serviceInterface.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingConfig {

    public static final String ENTER_MESSAGE_TEMPLATE = "Entering method: {} with arguments: {}";
    public static final String EXIT_MESSAGE_TEMPLATE = "Exiting method: {} with return value: {}";
    public static final String EXCEPTION_MESSAGE_TEMPLATE = "Exception in method: {} - {}";

    private static Logger LOG = LogManager.getLogger(LoggingConfig.class);


    @Before("execution(* com.github.brunomndantas.flashscore.api..*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        LOG.debug(ENTER_MESSAGE_TEMPLATE, joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    @AfterReturning(value = "execution(* com.github.brunomndantas.flashscore.api..*(..))", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        LOG.debug(EXIT_MESSAGE_TEMPLATE, joinPoint.getSignature().toShortString(), result);
    }

    @AfterThrowing(value = "execution(* com.github.brunomndantas.flashscore.api..*(..))", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Exception exception) {
        LOG.debug(EXCEPTION_MESSAGE_TEMPLATE, joinPoint.getSignature().toShortString(), exception.getMessage());
    }

}