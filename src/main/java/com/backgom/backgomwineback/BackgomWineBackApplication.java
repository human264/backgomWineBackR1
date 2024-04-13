package com.backgom.backgomwineback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.backgom.backgomwineback.domain")
public class BackgomWineBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackgomWineBackApplication.class, args);
    }

}
