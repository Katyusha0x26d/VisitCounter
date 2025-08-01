package me.katyusha.visitcounter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VisitCounterApplication {

    public static void main(String[] args) {
        SpringApplication.run(VisitCounterApplication.class, args);
    }

}
