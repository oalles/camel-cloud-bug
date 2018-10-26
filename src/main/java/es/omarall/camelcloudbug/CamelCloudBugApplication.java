package es.omarall.camelcloudbug;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Ordered;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

@SpringBootApplication
@EnableDiscoveryClient
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
