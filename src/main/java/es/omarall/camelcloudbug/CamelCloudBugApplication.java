package es.omarall.camelcloudbug;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class CamelCloudBugApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamelCloudBugApplication.class, args);
    }

    @Bean
    ApplicationRunner runner(StoreClient client, StoreUpdater updater) {
        return (args) -> {
            log.warn("Done!");
        };
    }
}
