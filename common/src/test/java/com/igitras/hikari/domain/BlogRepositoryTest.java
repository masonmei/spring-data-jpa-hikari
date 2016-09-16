package com.igitras.hikari.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test cases for class .
 *
 * @author mason
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:config/bootstrap-test.yml")
public class BlogRepositoryTest {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findAllByTagsNameContains() throws Exception {
        BlogEntity blogEntity = new BlogEntity().setAuthor("Mason")
                .setContent("Content")
                .setSummary("Summary")
                .setTitle("Title");
        entityManager.persist(blogEntity);
        Page<BlogEntity> blogEntities = blogRepository.findAllByTagsNameContains(new PageRequest(0, 10), "Mason");
        assertTrue(blogEntities.isFirst());
        assertTrue(blogEntities.isLast());
        assertNotNull(blogEntities.getContent());
        assertEquals(blogEntities.getTotalElements(), 0);
        assertEquals(blogEntities.getTotalPages(), 0);
        assertEquals(blogEntities.getNumberOfElements(), 0);

        entityManager.merge(blogEntity
                .setTags(Sets.newHashSet(new Tag().setName("Mason").setBlogs(Sets.newHashSet(blogEntity)))));
        blogEntities = blogRepository.findAllByTagsNameContains(new PageRequest(0, 10), "Mason");
        assertTrue(blogEntities.isFirst());
        assertTrue(blogEntities.isLast());
        assertNotNull(blogEntities.getContent());
        assertEquals(blogEntities.getTotalElements(), 1);
        assertEquals(blogEntities.getTotalPages(), 1);
        assertEquals(blogEntities.getNumberOfElements(), 1);
    }

}