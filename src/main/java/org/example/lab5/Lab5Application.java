package org.example.lab5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@EntityScan(basePackages = {"from_lab_2", "org.example.lab5"})
@SpringBootApplication
public class Lab5Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab5Application.class, args);
    }

}
