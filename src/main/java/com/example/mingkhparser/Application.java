package com.example.mingkhparser;

import com.example.mingkhparser.service.Parser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {
    /*
        dom.mingkh.ru parser
     */


    private Parser parser;

    public Application(Parser parser) {
        this.parser = parser;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        parser.process();
    }
}
