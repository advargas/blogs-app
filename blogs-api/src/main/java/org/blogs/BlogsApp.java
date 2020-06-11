package org.blogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogsApp {

    public static void main(final String[] args) {
        SpringApplication application = new SpringApplication(BlogsApp.class);
        application.run(args);
    }
}
