package ca.ulaval.glo2003;

import ca.ulaval.glo2003.controllers.HealthResource;
import ca.ulaval.glo2003.controllers.ReservationResource;
import ca.ulaval.glo2003.controllers.RestaurantResource;
import ca.ulaval.glo2003.controllers.ReviewResource;
import ca.ulaval.glo2003.domain.exceptions.mapper.InvalidParamExceptionMapper;
import ca.ulaval.glo2003.domain.exceptions.mapper.MissingParamExceptionMapper;
import ca.ulaval.glo2003.domain.exceptions.mapper.NotFoundExceptionMapper;
import ca.ulaval.glo2003.domain.exceptions.mapper.SentryExceptionMapper;
import ca.ulaval.glo2003.injection.ApplicationBinder;
import io.sentry.Sentry;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Main {
    public static String BASE_URI = "http://0.0.0.0:";

    public static HttpServer startServer() {
        final String port = getServerPort();
        BASE_URI = String.format("%s%s/", BASE_URI, port);

        final ResourceConfig rc = new ResourceConfig()
            .register(new ApplicationBinder())
            .register(HealthResource.class)
            .register(RestaurantResource.class)
            .register(ReservationResource.class)
            .register(ReviewResource.class)
            .register(InvalidParamExceptionMapper.class)
            .register(MissingParamExceptionMapper.class)
            .register(NotFoundExceptionMapper.class)
            .register(SentryExceptionMapper.class);

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static String getServerPort() {
        String portString = System.getenv("PORT");
        int port = 8080;

        if (portString != null) {
            try {
                port = Integer.parseInt(portString);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port format in environment variable PORT. Using default port 8080.");
            }
        }

        return String.valueOf(port);
    }

    public static void main(String[] args) {
        Sentry.init("https://d69c2df512ce6cd8b13e04393cdb3a65@o4507176762212352.ingest.us.sentry.io/4507176818835456");
        startServer();
        System.out.printf("Jersey app started with endpoints available at %s%n", BASE_URI);
    }
}
