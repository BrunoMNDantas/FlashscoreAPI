package com.github.brunomndantas.flashscore.api.serviceInterface.config;

import com.github.brunomndantas.repository4j.exception.NonExistentEntityException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Log4j2
public class GlobalDefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        log.error("Error on request", ex);
        return handleExceptionInternal(ex, "There was an error. Please try again later.", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(NonExistentEntityException.class)
    public ResponseEntity<Object> handleNotFound(NonExistentEntityException ex, WebRequest request) {
        log.error("Non existent entity requested", ex);
        return handleExceptionInternal(ex, "", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}