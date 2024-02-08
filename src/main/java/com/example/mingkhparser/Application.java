package com.example.mingkhparser;

import com.example.mingkhparser.service.Parser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {
    /*
        dom.mingkh.ru parser
     */

    private final String URL = "https://dom.mingkh.ru/ivanovskaya-oblast/furmanov/1150084";

    private Parser parser;

    public Application(Parser parser) {
        this.parser = parser;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        ExecutorService executorService = Executors.newFixedThreadPool(4);
        List<String> addresses = new ArrayList<>();
//        addresses.add(URL);
        addresses.add("https://dom.mingkh.ru/ivanovskaya-oblast/furmanov/1267290");
        addresses.forEach(a -> parser.process(a));

//        parser.process(URL);
    }
}
