package com.github.brunomndantas.flashscore.api.dataAccess.s3Repository;

import com.github.brunomndantas.repository4j.exception.DuplicatedEntityException;
import com.github.brunomndantas.repository4j.exception.NonExistentEntityException;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.UUID;
import java.util.function.Function;

@SpringBootTest

public class S3RepositoryTests {

    public static class Person {

        public String name;
        public int age;


        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public Person() { }

    }


    private static final String DIRECTORY = "Test";
    private static final Class<Person> KLASS = Person.class;
    private static final Function<Person, String> KEY_EXTRACTOR = person -> person.name;
    private static S3Client CLIENT;


    @Value("${s3.bucket-name}")
    private String s3BucketName;

    @Value("${s3.region}")
    private String s3Region;

    @Value("${s3.access-key}")
    private String s3AccessKey;

    @Value("${s3.secret-key}")
    private String s3SecretKey;


    @BeforeEach
    public void init() {
        if(CLIENT == null) {
            CLIENT = S3Client.builder()
                    .region(software.amazon.awssdk.regions.Region.of(s3Region))
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(s3AccessKey, s3SecretKey)
                    ))
                    .build();
        }
    }

    @AfterAll
    public static void finish() {
        CLIENT.close();
    }


    @Test
    public void shouldThrowUnsupportedOperationOnGetAll() {
        S3Repository<String, Person> repository = new S3Repository<>(CLIENT, s3BucketName, DIRECTORY, KLASS, KEY_EXTRACTOR);
        Assertions.assertThrows(UnsupportedOperationException.class, repository::getAll);
    }

    @Test
    public void shouldInsertAndGetEntity() throws RepositoryException {
        Person entity = new Person(UUID.randomUUID().toString(),1);
        S3Repository<String, Person> repository = new S3Repository<>(CLIENT, s3BucketName, DIRECTORY, KLASS, KEY_EXTRACTOR);

        repository.insert(entity);
        Person returnedEntity = repository.get(entity.name);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(entity.name, returnedEntity.name);
        Assertions.assertEquals(entity.age, returnedEntity.age);
    }

    @Test
    public void shouldUpdateEntity() throws RepositoryException {
        Person entity = new Person(UUID.randomUUID().toString(), 1);
        S3Repository<String, Person> repository = new S3Repository<>(CLIENT, s3BucketName, DIRECTORY, KLASS, KEY_EXTRACTOR);

        repository.insert(entity);
        entity.age++;
        repository.update(entity);
        Person returnedEntity = repository.get(entity.name);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(entity.name, returnedEntity.name);
        Assertions.assertEquals(entity.age, returnedEntity.age);
    }

    @Test
    public void shouldDeleteEntity() throws RepositoryException {
        Person entity = new Person(UUID.randomUUID().toString(), 1);
        S3Repository<String, Person> repository = new S3Repository<>(CLIENT, s3BucketName, DIRECTORY, KLASS, KEY_EXTRACTOR);

        repository.insert(entity);
        repository.delete(entity.name);
        Person returnedEntity = repository.get(entity.name);

        Assertions.assertNull(returnedEntity);
    }

    @Test
    public void shouldReturnNullIfNoEntityIsFoundOnGet() throws RepositoryException {
        S3Repository<String, Person> repository = new S3Repository<>(CLIENT, s3BucketName, DIRECTORY, KLASS, KEY_EXTRACTOR);

        Person returnedEntity = repository.get(UUID.randomUUID().toString());

        Assertions.assertNull(returnedEntity);
    }

    @Test
    public void shouldThrowDuplicatedExceptionOnInsert() throws RepositoryException {
        Person entity = new Person(UUID.randomUUID().toString(),1);
        S3Repository<String, Person> repository = new S3Repository<>(CLIENT, s3BucketName, DIRECTORY, KLASS, KEY_EXTRACTOR);

        repository.insert(entity);
        Assertions.assertThrows(DuplicatedEntityException.class, () -> repository.insert(entity));
    }

    @Test
    public void shouldThrowNonExistentEntityOnUpdate() {
        Person entity = new Person(UUID.randomUUID().toString(),1);
        S3Repository<String, Person> repository = new S3Repository<>(CLIENT, s3BucketName, DIRECTORY, KLASS, KEY_EXTRACTOR);

        Assertions.assertThrows(NonExistentEntityException.class, () -> repository.update(entity));
    }

    @Test
    public void shouldWrapExceptionOnGet() {
        Person entity = new Person(UUID.randomUUID().toString(),1);
        S3Repository<String, Person> repository = new S3Repository<>(CLIENT, "non existent bucket", DIRECTORY, KLASS, KEY_EXTRACTOR);

        Exception exception = Assertions.assertThrows(RepositoryException.class, () -> repository.get(entity.name));
        Assertions.assertNotNull(exception.getCause());
    }

    @Test
    public void shouldWrapExceptionOnInsert() {
        Person entity = new Person(UUID.randomUUID().toString(),1);
        S3Repository<String, Person> repository = new S3Repository<>(CLIENT, "non existent bucket", DIRECTORY, KLASS, KEY_EXTRACTOR);

        Exception exception = Assertions.assertThrows(RepositoryException.class, () -> repository.insert(entity));
        Assertions.assertNotNull(exception.getCause());
    }

    @Test
    public void shouldWrapExceptionOnUpdate() {
        Person entity = new Person(UUID.randomUUID().toString(),1);
        S3Repository<String, Person> repository = new S3Repository<>(CLIENT, "non existent bucket", DIRECTORY, KLASS, KEY_EXTRACTOR);

        Exception exception = Assertions.assertThrows(RepositoryException.class, () -> repository.update(entity));
        Assertions.assertNotNull(exception.getCause());
    }

    @Test
    public void shouldWrapExceptionOnDelete() {
        Person entity = new Person(UUID.randomUUID().toString(),1);
        S3Repository<String, Person> repository = new S3Repository<>(CLIENT, "non existent bucket", DIRECTORY, KLASS, KEY_EXTRACTOR);

        Exception exception = Assertions.assertThrows(RepositoryException.class, () -> repository.delete(entity.name));
        Assertions.assertNotNull(exception.getCause());
    }

}