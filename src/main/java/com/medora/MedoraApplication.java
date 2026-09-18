package com.medora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MedoraApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedoraApplication.class, args);
    }
}
