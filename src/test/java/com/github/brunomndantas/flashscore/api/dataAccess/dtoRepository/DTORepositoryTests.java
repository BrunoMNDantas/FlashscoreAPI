package com.github.brunomndantas.flashscore.api.dataAccess.dtoRepository;

import com.github.brunomndantas.repository4j.memory.MemoryRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.function.Function;

@SpringBootTest
public class DTORepositoryTests {

    @AllArgsConstructor
    @Data
    private static class Person {
        public int id;
        public int age;
    }

    @AllArgsConstructor
    @Data
    private static class PersonDTO {
        public String id;
        public String age;
    }


    private static final Function<Integer, String> KEY_TO_DTO_MAPPER = id -> Integer.toString(id);
    private static final Function<Person, PersonDTO> ENTITY_TO_DTO_MAPPER = entity -> new PersonDTO(entity.id+"", entity.age+"");
    private static final Function<PersonDTO, Person> DTO_TO_ENTITY_MAPPER = dto -> new Person(Integer.parseInt(dto.id), Integer.parseInt(dto.age));


    @Test
    public void shouldMapDTOsOnGetAll() throws Exception {
        MemoryRepository<String, PersonDTO> sourceRepository = new MemoryRepository<>(dto -> dto.id);
        DTORepository<Integer, Person, String, PersonDTO> repository = new DTORepository<>(
                sourceRepository, KEY_TO_DTO_MAPPER, ENTITY_TO_DTO_MAPPER, DTO_TO_ENTITY_MAPPER
        );

        Person personA = new Person(1, 10);
        Person personB = new Person(2, 20);

        sourceRepository.insert(ENTITY_TO_DTO_MAPPER.apply(personA));
        sourceRepository.insert(ENTITY_TO_DTO_MAPPER.apply(personB));

        Collection<Person> entities = repository.getAll();
        Assertions.assertEquals(2, entities.size());
        Assertions.assertTrue(entities.contains(personA));
        Assertions.assertTrue(entities.contains(personB));
    }

    @Test
    public void shouldMapDTOsOnGet() throws Exception {
        MemoryRepository<String, PersonDTO> sourceRepository = new MemoryRepository<>(dto -> dto.id);
        DTORepository<Integer, Person, String, PersonDTO> repository = new DTORepository<>(
                sourceRepository, KEY_TO_DTO_MAPPER, ENTITY_TO_DTO_MAPPER, DTO_TO_ENTITY_MAPPER
        );

        Person person = new Person(1, 10);
        sourceRepository.insert(ENTITY_TO_DTO_MAPPER.apply(person));

        Person entity = repository.get(person.id);
        Assertions.assertEquals(person, entity);
    }

    @Test
    public void shouldSkipMapIfGetReturnsNoEntity() throws Exception {
        MemoryRepository<String, PersonDTO> sourceRepository = new MemoryRepository<>(dto -> dto.id);
        DTORepository<Integer, Person, String, PersonDTO> repository = new DTORepository<>(
                sourceRepository, KEY_TO_DTO_MAPPER, ENTITY_TO_DTO_MAPPER, DTO_TO_ENTITY_MAPPER
        );

        Person entity = repository.get(1);
        Assertions.assertNull(entity);
    }

    @Test
    public void shouldMapEntityOnInsert() throws Exception {
        MemoryRepository<String, PersonDTO> sourceRepository = new MemoryRepository<>(dto -> dto.id);
        DTORepository<Integer, Person, String, PersonDTO> repository = new DTORepository<>(
                sourceRepository, KEY_TO_DTO_MAPPER, ENTITY_TO_DTO_MAPPER, DTO_TO_ENTITY_MAPPER
        );

        Person person = new Person(1, 10);
        repository.insert(person);

        PersonDTO dto = sourceRepository.get("1");
        Assertions.assertNotNull(dto);
    }

    @Test
    public void shouldMapEntityOnUpdate() throws Exception {
        MemoryRepository<String, PersonDTO> sourceRepository = new MemoryRepository<>(dto -> dto.id);
        DTORepository<Integer, Person, String, PersonDTO> repository = new DTORepository<>(
                sourceRepository, KEY_TO_DTO_MAPPER, ENTITY_TO_DTO_MAPPER, DTO_TO_ENTITY_MAPPER
        );

        Person person = new Person(1, 10);
        sourceRepository.insert(ENTITY_TO_DTO_MAPPER.apply(person));

        person.age = 100;
        repository.update(person);

        PersonDTO dto = sourceRepository.get("1");
        Assertions.assertNotNull(dto);
        Assertions.assertEquals("100", dto.age);
    }

    @Test
    public void shouldMapKeyOnDelete() throws Exception {
        MemoryRepository<String, PersonDTO> sourceRepository = new MemoryRepository<>(dto -> dto.id);
        DTORepository<Integer, Person, String, PersonDTO> repository = new DTORepository<>(
                sourceRepository, KEY_TO_DTO_MAPPER, ENTITY_TO_DTO_MAPPER, DTO_TO_ENTITY_MAPPER
        );

        Person person = new Person(1, 10);
        sourceRepository.insert(ENTITY_TO_DTO_MAPPER.apply(person));

        repository.delete(1);

        PersonDTO dto = sourceRepository.get("1");
        Assertions.assertNull(dto);
    }

}