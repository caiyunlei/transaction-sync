package com.cyl.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@SpringBootApplication
@EnableJpaRepositories()
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
    }
}
