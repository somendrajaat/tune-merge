package com.tunemerge;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TuneMergeApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();
        SpringApplication.run(TuneMergeApplication.class, args);
    }

}
