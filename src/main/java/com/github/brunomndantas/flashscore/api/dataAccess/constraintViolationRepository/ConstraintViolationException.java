package com.github.brunomndantas.flashscore.api.dataAccess.constraintViolationRepository;

import com.github.brunomndantas.repository4j.exception.RepositoryException;

public class ConstraintViolationException extends RepositoryException {

    public ConstraintViolationException() {
        super();
    }

    public ConstraintViolationException(String message) {
        super(message);
    }

    public ConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
    }

}