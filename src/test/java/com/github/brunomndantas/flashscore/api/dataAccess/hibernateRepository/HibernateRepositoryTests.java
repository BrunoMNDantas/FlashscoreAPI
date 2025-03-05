package com.github.brunomndantas.flashscore.api.dataAccess.hibernateRepository;

import com.github.brunomndantas.repository4j.exception.DuplicatedEntityException;
import com.github.brunomndantas.repository4j.exception.NonExistentEntityException;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import jakarta.persistence.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class HibernateRepositoryTests {

    @Entity
    @Table(name = "Person")
    public static class Person {

        @Id
        public String name;

        public int age;


        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public Person() { }

    }


    @PersistenceContext
    private EntityManager entityManager;

    private HibernateRepository<String, Person> repository;


    @BeforeEach
    public void setUp() {
        repository = new HibernateRepository<>(Person.class, entityManager, person -> person.name);
    }


    @Test
    public void shouldInsertAndGetEntity() throws RepositoryException {
        Person entity = new Person("A", 1);

        repository.insert(entity);
        Person retrieved = repository.get(entity.name);

        Assertions.assertNotNull(retrieved);
        Assertions.assertEquals(entity.name, retrieved.name);
        Assertions.assertEquals(entity.age, retrieved.age);
    }

    @Test
    public void shouldThrowDuplicatedEntityExceptionOnInsert() throws RepositoryException {
        Person entity = new Person("A", 1);
        repository.insert(entity);

        Exception exception = Assertions.assertThrows(DuplicatedEntityException.class, () -> repository.insert(entity));

        Assertions.assertTrue(exception.getMessage().contains(entity.name));
    }

    @Test
    public void shouldUpdateEntity() throws RepositoryException {
        Person entity = new Person("A", 0);

        repository.insert(entity);
        entity.age = 1;
        repository.update(entity);

        Person updated = repository.get(entity.name);
        Assertions.assertEquals(1, updated.age);
    }

    @Test
    public void shouldThrowNonExistentEntityOnUpdate() {
        Person entity = new Person("A", 1);

        Exception exception = Assertions.assertThrows(NonExistentEntityException.class, () -> repository.update(entity));

        Assertions.assertTrue(exception.getMessage().contains(entity.name));
    }

    @Test
    public void shouldDeleteEntity() throws RepositoryException {
        Person entity = new Person("A", 1);

        repository.insert(entity);

        repository.delete(entity.name);

        Person deleted = repository.get(entity.name);
        Assertions.assertNull(deleted);
    }

}