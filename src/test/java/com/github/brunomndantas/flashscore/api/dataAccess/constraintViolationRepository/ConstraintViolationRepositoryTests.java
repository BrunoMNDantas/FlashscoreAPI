package com.github.brunomndantas.flashscore.api.dataAccess.constraintViolationRepository;

import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import com.github.brunomndantas.repository4j.memory.MemoryRepository;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConstraintViolationRepositoryTests {

    public static class Person {

        @NotNull
        public String name;


        public Person(String name) {
            this.name = name;
        }

    }

    @Test
    public void shouldReturnEntityIfNoViolationIsFound() throws RepositoryException  {
        Person person = new Person("name");
        IRepository<String, Person> sourceRepository = new MemoryRepository<>(p -> p.name);
        IRepository<String, Person> repository = new ConstraintViolationRepository<>(sourceRepository);

        sourceRepository.insert(person);

        Person returnedPerson = repository.get(person.name);
        Assertions.assertSame(person, returnedPerson);
    }

    @Test
    public void shouldThrowExceptionIfViolationIsFound() throws RepositoryException {
        Person person = new Person(null);
        IRepository<String, Person> sourceRepository = new MemoryRepository<>(p -> p.name);
        IRepository<String, Person> repository = new ConstraintViolationRepository<>(sourceRepository);

        sourceRepository.insert(person);

        Exception exception = Assertions.assertThrows(ConstraintViolationException.class, () -> repository.get(person.name));
        Assertions.assertTrue(exception.getMessage().contains("name"));
        Assertions.assertTrue(exception.getMessage().contains("null"));
    }

}