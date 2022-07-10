package com.app.vple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(VpleApplication.class, args);
    }

}
