package com.github.brunomndantas.flashscore.api.dataAccess.constraintViolationRepository;

import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import com.github.brunomndantas.repository4j.validator.ValidatorRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.util.Set;

public class ConstraintViolationRepository<K,E> extends ValidatorRepository<K,E> {

    private static <E> void getConstraintViolation(E entity) throws ConstraintViolationException {
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();

        try(factory) {
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<E>> violations = validator.validate(entity);

            if(!violations.isEmpty()) {
                ConstraintViolation<E> violation = violations.iterator().next();
                String message = violation.getPropertyPath() + ": " + violation.getMessage();
                throw new ConstraintViolationException(message);
            }
        }
    }


    public ConstraintViolationRepository(IRepository<K,E> sourceRepository) {
        super(sourceRepository, ConstraintViolationRepository::getConstraintViolation);
    }


    @Override
    public E get(K key) throws RepositoryException {
        E entity = super.get(key);

        super.validator.validate(entity);

        return entity;
    }

}
