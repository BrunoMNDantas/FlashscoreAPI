package com.github.brunomndantas.flashscore.api.logic.services;

import com.github.brunomndantas.repository4j.exception.RepositoryException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.util.Set;

public class ValidatorService {

    public static <E> void validateEntity(E entity) throws RepositoryException {
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();

        try(factory) {
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<E>> violations = validator.validate(entity);

            if(!violations.isEmpty()) {
                ConstraintViolation<E> violation = violations.iterator().next();
                throw new RepositoryException(violation.getPropertyPath() + ": " + violation.getMessage());
            }
        }
    }

}
