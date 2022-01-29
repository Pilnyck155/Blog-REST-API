package com.pilnyck.blogrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class BlogRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogRestApiApplication.class, args);
    }
}
