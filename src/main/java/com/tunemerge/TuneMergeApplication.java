package com.tunemerge;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
//@Configuration
@SpringBootApplication
public class TuneMergeApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();
        SpringApplication.run(TuneMergeApplication.class, args);
    }

//    @Bean
//    public RestTemplate restTemplate(){
//        return new RestTemplate();
//    }

}
