package com.lotte4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class LotteonTeam4Application {

    public static void main(String[] args) {
        SpringApplication.run(LotteonTeam4Application.class, args);
    }

}
