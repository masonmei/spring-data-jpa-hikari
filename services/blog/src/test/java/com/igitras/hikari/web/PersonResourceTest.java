package com.igitras.hikari.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.stubVoid;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.igitras.hikari.service.PersonService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

/**
 * Test cases for class PersonResource.
 *
 * @author mason
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PersonResource.class)
@TestPropertySource(locations = "classpath:config/bootstrap-test.yml")
@AutoConfigureTestEntityManager
@AutoConfigureDataJpa
@AutoConfigureRestDocs
public class PersonResourceTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        reset(personService);
    }

    @org.junit.Test
    public void search() throws Exception {
        Person person1 = new Person().setName("Mason1")
                .setAge(30)
                .setBrief("This is mason1")
                .setId(1L);
        Person person2 = new Person().setName("Mason2")
                .setAge(30)
                .setBrief("This is mason2")
                .setId(2L);
        given(personService.findAll()).willReturn(Arrays.asList(person1, person2));
        this.mvc.perform(get("/persons").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @org.junit.Test
    public void find() throws Exception {
        Person person = new Person().setName("Mason")
                .setAge(30)
                .setBrief("This is mason")
                .setId(1L);
        given(personService.findOne(person.getId())).willReturn(person);
        this.mvc.perform(get("/persons/{id}", person.getId()).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Mason"));
    }

    @org.junit.Test
    public void create() throws Exception {
        Person person = new Person().setName("Mason")
                .setAge(30)
                .setBrief("This is mason");
        Person savedPerson = new Person().setName("Mason")
                .setAge(30)
                .setBrief("This is mason")
                .setId(1L);
        given(personService.save(any(Person.class))).willReturn(savedPerson);
        this.mvc.perform(post("/persons").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(person)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mason"))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @org.junit.Test
    public void update() throws Exception {
        Person person = new Person().setName("Mason")
                .setAge(30)
                .setBrief("This is mason")
                .setId(1L);
        Person updated = new Person().setName("title1")
                .setAge(30)
                .setBrief("This is mason")
                .setId(1L);
        given(personService.findOne(any())).willReturn(person);
        given(personService.saveAndFlush(any(Person.class))).willReturn(updated);
        this.mvc.perform(put("/persons/{id}", person.getId()).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(updated)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("title1"))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @org.junit.Test
    public void patchUpdate() throws Exception {
        Person person = new Person().setName("Mason")
                .setAge(30)
                .setBrief("This is mason")
                .setId(1L);
        Person updated = new Person().setName("author1")
                .setAge(30)
                .setBrief("This is mason")
                .setId(1L);
        given(personService.findOne(any())).willReturn(person);
        given(personService.saveAndFlush(any(Person.class))).willReturn(updated);
        this.mvc.perform(patch("/persons/{id}", 1).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(updated)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("author1"))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }


}