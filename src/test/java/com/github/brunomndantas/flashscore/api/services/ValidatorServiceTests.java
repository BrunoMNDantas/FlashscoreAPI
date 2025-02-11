package com.github.brunomndantas.flashscore.api.services;

import com.github.brunomndantas.flashscore.api.logic.services.ValidatorService;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValidatorServiceTests {

    public static class Person {
        @NotNull
        @NotEmpty
        public String name;

        @NotNull
        @NotEmpty
        public String address;

        public Person(String name, String address) {
            this.name = name;
            this.address = address;
        }

    }


    @Test
    public void shouldThrowExceptionIfEntityIsNotValid() {
        Person entity = new Person("Name", "");
        Assertions.assertThrows(RepositoryException.class, () -> ValidatorService.validateEntity(entity));
    }

    @Test
    public void shouldIncludePropertyNameOnException() {
        Person entity = new Person("Name", "");
        Exception exception = Assertions.assertThrows(RepositoryException.class, () -> ValidatorService.validateEntity(entity));
        Assertions.assertTrue(exception.getMessage().contains("address"));
        Assertions.assertTrue(exception.getMessage().contains("empty"));
    }

    @Test
    public void shouldNotThrowExceptionIfEntityIsValid() throws RepositoryException {
        Person entity = new Person("Name", "Address");
        ValidatorService.validateEntity(entity);
    }

}
