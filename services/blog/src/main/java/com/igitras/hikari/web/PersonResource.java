package com.igitras.hikari.web;

import com.igitras.hikari.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @author mason
 */
@RestController
@RequestMapping("persons")
public class PersonResource {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> search() {
        return personService.findAll();
    }

    @GetMapping(value = "{id}")
    public Person find(@PathVariable Long id) {
        return personService.findOne(id);
    }

    @PostMapping
    public Person create(@RequestBody Person person) {
        return personService.save(person);
    }

    @PutMapping(value = "{id}")
    public Person update(@PathVariable Long id, @RequestBody Person entity) {
        return Optional.ofNullable(personService.findOne(id))
                .map(blogEntity -> {
                    blogEntity.setAge(entity.getAge())
                            .setBrief(entity.getBrief())
                            .setName(entity.getName());
                    return personService.saveAndFlush(blogEntity);
                })
                .orElseGet(() -> null);
    }

    @PatchMapping("{id}")
    public Person patch(@PathVariable Long id, @RequestBody Person entity) {
        return Optional.ofNullable(personService.findOne(id))
                .map(blogEntity -> {
                    if (entity.getName() != null) {
                        blogEntity.setName(entity.getName());
                    }

                    if (entity.getBrief() != null) {
                        blogEntity.setBrief(entity.getBrief());
                    }

                    return personService.saveAndFlush(blogEntity);
                })
                .orElseGet(() -> null);
    }

}
