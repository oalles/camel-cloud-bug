package es.omarall.camelcloudbug;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Route2 extends SpringRouteBuilder {

    private StoreUpdater client;

    public Route2(StoreUpdater client) {
        this.client = client;
    }

    @Override
    public void configure() throws Exception {
        from("direct:b").bean(client, "update").to("log:es.omarall?level=DEBUG").routeId("***** ROUTE 2 *****");;
    }
}
