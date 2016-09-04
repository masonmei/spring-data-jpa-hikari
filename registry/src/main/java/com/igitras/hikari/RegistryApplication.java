package com.igitras.hikari;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author mason
 */
@SpringBootApplication
@EnableEurekaServer
public class RegistryApplication {

    public static void main(String[] args) {
        run(RegistryApplication.class, args);
    }
}
