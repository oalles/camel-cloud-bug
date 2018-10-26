package es.omarall.camelcloudbug;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Route3 extends SpringRouteBuilder {


    @Override
    public void configure() throws Exception {
        from("direct:c").to("log:com.mycompany.order?level=DEBUG").routeId("***** ROUTE 3 *****");
    }
}
