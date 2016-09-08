package com.igitras.hikari.service;

import com.igitras.hikari.web.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mason
 */
@Component
public class PersonService {
    private final Map<Long, Person> personRepository = new ConcurrentHashMap<>();

    public List<Person> findAll() {
        Collection<Person> values = personRepository.values();
        return new ArrayList<>(values);
    }

    public Person findOne(Long id) {
        return personRepository.get(id);
    }

    public Person save(Person person) {
        if (person.getId() != null && personRepository.containsKey(person.getId())) {
            personRepository.put(person.getId(), person);
        } else {
            Long keys = (long) (personRepository.size() + 1);
            person.setId(keys);
            personRepository.put(keys, person);
        }
        return person;
    }

    public Person saveAndFlush(Person person) {
        return save(person);
    }
}
