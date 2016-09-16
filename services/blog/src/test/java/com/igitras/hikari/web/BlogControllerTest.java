package com.igitras.hikari.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
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
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test cases for class .
 *
 * @author mason
 */
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@WebMvcTest(BlogController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets", uriHost = "igitras.com", uriPort = 80,
        uriScheme = "https")
@TestPropertySource(locations = "classpath:config/bootstrap-test.yml",
        properties = "spring.jackson.serialization.indent-output:true")
public class BlogControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
    }

    @org.junit.Test
    public void search() throws Exception {
        BlogEntity blogEntity = new BlogEntity().setAuthor("Mason")
                .setContent("Content")
                .setSummary("Summary")
                .setTitle("Title");
        entityManager.persist(blogEntity);
        this.mvc.perform(get("/blogs").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andDo(document("{class-name}/{method-name}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @org.junit.Test
    public void find() throws Exception {

        BlogEntity blogEntity = new BlogEntity().setAuthor("Mason")
                .setContent("Content")
                .setSummary("Summary")
                .setTitle("Title");
        entityManager.persist(blogEntity);
        this.mvc.perform(get("/blogs/{id}", blogEntity.getId()).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andDo(document("{class-name}/{method-name}"))
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
        this.mvc.perform(post("/blogs/").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(blogEntity)))
                .andDo(print())
                .andDo(document("{class-name}/{method-name}"))
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
        entityManager.persist(blogEntity);
        blogEntity.setTitle("title1");
        this.mvc.perform(put("/blogs/{id}", blogEntity.getId()).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(blogEntity)))
                .andDo(print())
                .andDo(document("{class-name}/{method-name}"))
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
        entityManager.persist(blogEntity);
        blogEntity.setAuthor("author1");
        this.mvc.perform(patch("/blogs/{id}", blogEntity.getId()).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(blogEntity)))
                .andDo(print())
                .andDo(document("{class-name}/{method-name}"))
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
        entityManager.persist(blogEntity);
        this.mvc.perform(delete("/blogs/{id}", blogEntity.getId()).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andDo(document("{class-name}/{method-name}"))
                .andExpect(status().isOk());
    }

}