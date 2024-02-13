package ca.ulaval.glo2003;

import ca.ulaval.glo2003.controllers.HealthResource;
import ca.ulaval.glo2003.controllers.RestaurantResource;
import ca.ulaval.glo2003.domain.exception.mapper.InvalidParamExceptionMapper;
import ca.ulaval.glo2003.domain.exception.mapper.MissingParamExceptionMapper;
import ca.ulaval.glo2003.domain.exception.mapper.NotFoundExceptionMapper;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Main {
    public static final String BASE_URI = "http://0.0.0.0:8080/";

    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig()
            .register(new HealthResource())
            .register(new RestaurantResource())
            .register(new InvalidParamExceptionMapper())
            .register(new MissingParamExceptionMapper())
            .register(new NotFoundExceptionMapper());

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) {
        startServer();
        System.out.printf("Jersey app started with endpoints available at %s%n", BASE_URI);
    }
}
