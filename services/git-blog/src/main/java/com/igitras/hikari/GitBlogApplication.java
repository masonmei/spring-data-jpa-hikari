package com.igitras.hikari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * Git base blog application.
 *
 * @author mason
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableSpringDataWebSupport
@EnableCaching(proxyTargetClass = true)
public class GitBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitBlogApplication.class, args);
    }
}
