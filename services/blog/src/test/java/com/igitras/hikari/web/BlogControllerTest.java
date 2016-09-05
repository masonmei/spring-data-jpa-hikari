package com.igitras.hikari.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.igitras.hikari.domain.BlogEntity;
import com.igitras.hikari.domain.BlogRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Test cases for class .
 *
 * @author mason
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureRestDocs(value = "target/generated-snippets", uriHost = "igitras.com", uriPort = 80)
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:config/bootstrap-test.yml")
public class BlogControllerTest {

    private MockMvc mvc;

    @Autowired
    public WebApplicationContext context;

    @Autowired
    private BlogRepository repository;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        BlogEntity blogEntity = new BlogEntity().setAuthor("Mason")
                .setContent("Content")
                .setSummary("Summary")
                .setTitle("Title");
        repository.saveAndFlush(blogEntity);
    }

    @org.junit.Test
    public void search() throws Exception {
        this.mvc.perform(get("/").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @org.junit.Test
    public void find() throws Exception {
        BlogEntity blogEntity = new BlogEntity().setAuthor("Mason")
                .setContent("Content")
                .setSummary("Summary")
                .setTitle("Title");
        blogEntity = repository.saveAndFlush(blogEntity);
        this.mvc.perform(get("/{id}", blogEntity.getId()).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Title"));
    }

    @org.junit.Test
    public void create() throws Exception {
        BlogEntity blogEntity = new BlogEntity().setAuthor("Mason")
                .setContent("Content")
                .setSummary("Summary")
                .setTitle("Title");
        this.mvc.perform(post("/").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(blogEntity)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.id").isNotEmpty());

    }

    @org.junit.Test
    public void update() throws Exception {
        BlogEntity blogEntity = new BlogEntity().setAuthor("Mason")
                .setContent("Content")
                .setSummary("Summary")
                .setTitle("Title");
        blogEntity = repository.saveAndFlush(blogEntity);
        blogEntity.setTitle("title1");
        this.mvc.perform(put("/{id}", blogEntity.getId()).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(blogEntity)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title1"))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @org.junit.Test
    public void patchUpdate() throws Exception {
        BlogEntity blogEntity = new BlogEntity().setAuthor("Mason")
                .setContent("Content")
                .setSummary("Summary")
                .setTitle("Title1");
        blogEntity = repository.saveAndFlush(blogEntity);
        blogEntity.setAuthor("author1");
        this.mvc.perform(patch("/{id}", 1).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(blogEntity)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("author1"))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @org.junit.Test
    public void remove() throws Exception {
        BlogEntity blogEntity = new BlogEntity().setAuthor("Mason")
                .setContent("Content")
                .setSummary("Summary")
                .setTitle("Title");
        blogEntity = repository.saveAndFlush(blogEntity);
        this.mvc.perform(delete("/{id}", blogEntity.getId()).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

}