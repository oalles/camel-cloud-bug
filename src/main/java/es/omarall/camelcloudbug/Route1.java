package es.omarall.camelcloudbug;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Route1 extends SpringRouteBuilder {

    private StoreClient client;

    public Route1(StoreClient client) {
        this.client = client;
    }

    @Override
    public void configure() throws Exception {
        from("direct:a").bean(client, "getStores").to("log:com.mycompany.order?level=DEBUG").routeId("***** ROUTE 1 *****");;
    }
}
