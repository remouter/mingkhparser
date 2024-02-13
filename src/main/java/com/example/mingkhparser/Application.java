package com.example.mingkhparser;

import com.example.mingkhparser.service.Parser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

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
        SpringApplication.run(Application.class, args).close();
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> addresses = parser.getHouses("https://dom.mingkh.ru/ivanovskaya-oblast/furmanov/");

        int index = addresses.size();
        for (int i = 0; i < addresses.size(); i++) {
            if (addresses.get(i).equals("https://dom.mingkh.ru/ivanovskaya-oblast/furmanov/1129504")) {
                log.info("processed {}", i);
                index = i;
                break;
            }
        }

        addresses = addresses.subList(index, addresses.size());
        addresses.forEach(a -> parser.process(a)); //1218


//        addresses.stream().parallel().forEach(a -> parser.process(a)); //683

//        ExecutorService executorService = Executors.newFixedThreadPool(4);
//        for (String s : addresses) {
//            executorService.execute(() -> parser.process(s)); //748
//        }
    }
}
