package co.wadcorp.waiting.api;

import co.wadcorp.waiting.data.domain.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "co.wadcorp.waiting")
public class CatchWaitingApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatchWaitingApiApplication.class, args);
    }
}