package com.igitras.hikari;

import com.igitras.hikari.domain.BlogEntity;
import com.igitras.hikari.domain.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author mason
 */
@SpringBootApplication
@EnableEurekaClient
@EnableJpaAuditing
@EnableJpaRepositories
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Autowired
    private BlogRepository repository;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            repository.findAll().forEach(System.out::println);
            repository.save(new BlogEntity().setAuthor("mason1")
                    .setContent("content1")
                    .setSummary("summary1")
                    .setTitle("title1"));
        };
    }
}
