package com.igitras.hikari;

import com.igitras.hikari.domain.BlogEntity;
import com.igitras.hikari.domain.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by mason on 9/1/16.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private BlogRepository repository;

    @Bean
    public CommandLineRunner initCommand() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                repository.save(new BlogEntity().setAuthor("mason")
                        .setContent("content")
                        .setSummary("summary")
                        .setTitle("title"));
            }
        };
    }
}
