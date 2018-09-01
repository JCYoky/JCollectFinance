package me.hjc.updatedividends;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class UpdateDividendsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpdateDividendsApplication.class, args);
    }
}
