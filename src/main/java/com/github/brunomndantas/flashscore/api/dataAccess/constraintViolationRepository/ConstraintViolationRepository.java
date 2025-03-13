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
import java.util.function.Function;

public class ConstraintViolationRepository<K,E,DTO> extends ValidatorRepository<K,E> {

    private static <E, DTO> void getConstraintViolation(E entity, Function<E,DTO> dtoMapper) throws ConstraintViolationException {
        DTO dto = dtoMapper.apply(entity);

        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();

        try(factory) {
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<DTO>> violations = validator.validate(dto);

            if(!violations.isEmpty()) {
                ConstraintViolation<DTO> violation = violations.iterator().next();
                String message = violation.getPropertyPath() + ": " + violation.getMessage();
                throw new ConstraintViolationException(message);
            }
        }
    }


    public ConstraintViolationRepository(IRepository<K,E> sourceRepository, Function<E,DTO> dtoMapper) {
        super(sourceRepository, entity -> getConstraintViolation(entity, dtoMapper));
    }


    @Override
    public E get(K key) throws RepositoryException {
        E entity = super.get(key);

        super.validator.validate(entity);

        return entity;
    }

}
