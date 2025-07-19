package me.katyusha.visitcounter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VisitCounterApplication {

    public static void main(String[] args) {
        SpringApplication.run(VisitCounterApplication.class, args);
    }

}
