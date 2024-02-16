package com.example.mingkhparser;

import com.example.mingkhparser.models.HouseInfo;
import com.example.mingkhparser.service.Parser;
import com.example.mingkhparser.service.export.XlsExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {
    /*
        dom.mingkh.ru parser
     */

    private final Parser parser;
    private final XlsExportService exportService;

    public Application(Parser parser, XlsExportService exportService) {
        this.parser = parser;
        this.exportService = exportService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args).close();
    }

    @Override
    public void run(String... args) {
//        List<String> addresses = parser.getHouses("https://dom.mingkh.ru/ivanovskaya-oblast/furmanov/");
        List<String> addresses = List.of("https://dom.mingkh.ru/ivanovskaya-oblast/furmanov/1343465");
//        addresses = processNext(addresses);

        Long startTime = System.currentTimeMillis();
        addresses.forEach(parser::process); //takes 61285 / 61.285 sec
//        addresses.stream().parallel().forEach(a -> parser.process(a)); //takes 63532 / 63.532 sec
//        executorService(addresses);

        Long endTime = System.currentTimeMillis();
        List<HouseInfo> result = parser.getResult();
        log.info("result: {}", result);
        exportService.export(result);
        log.trace("process takes: {}", endTime - startTime);
    }

    private List<String> processNext(final List<String> addresses) {
        int index = addresses.size();
        for (int i = 0; i < addresses.size(); i++) {
            if (addresses.get(i).equals("https://dom.mingkh.ru/ivanovskaya-oblast/furmanov/1343465")) {
                log.info("processed {}", i);
                index = i;
                break;
            }
        }
        return addresses.subList(index, addresses.size());
    }

    private void executorService(final List<String> addresses) {
        //        ExecutorService executorService = Executors.newFixedThreadPool(4); //takes 77.31 sec
        ExecutorService executorService = Executors.newFixedThreadPool(10); //takes 68.40 sec
        for (String s : addresses) {
            executorService.execute(() -> parser.process(s));
        }
        executorService.shutdown();
    }
}
