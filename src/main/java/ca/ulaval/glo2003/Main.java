package ca.ulaval.glo2003;

import ca.ulaval.glo2003.controllers.HealthResource;
import ca.ulaval.glo2003.controllers.RestaurantResource;
import ca.ulaval.glo2003.controllers.validators.CreateReservationValidator;
import ca.ulaval.glo2003.controllers.validators.CreateRestaurantValidator;
import ca.ulaval.glo2003.controllers.validators.GetRestaurantsValidator;
import ca.ulaval.glo2003.domain.exceptions.mapper.InvalidParamExceptionMapper;
import ca.ulaval.glo2003.domain.exceptions.mapper.MissingParamExceptionMapper;
import ca.ulaval.glo2003.domain.exceptions.mapper.NotFoundExceptionMapper;
import ca.ulaval.glo2003.domain.repositories.RestaurantAndReservationRepository;
import ca.ulaval.glo2003.infrastructure.InMemoryRestaurantAndReservationRepository;
import ca.ulaval.glo2003.service.ReservationService;
import ca.ulaval.glo2003.service.RestaurantService;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Main {
    public static final String BASE_URI = "http://0.0.0.0:8080/";

    public static HttpServer startServer() {
        RestaurantAndReservationRepository restaurantAndReservationRepository = new InMemoryRestaurantAndReservationRepository();
        RestaurantService restaurantService = new RestaurantService(restaurantAndReservationRepository);
        ReservationService reservationService = new ReservationService(restaurantAndReservationRepository);
        CreateReservationValidator createReservationValidator = new CreateReservationValidator();
        CreateRestaurantValidator createRestaurantValidator = new CreateRestaurantValidator();
        GetRestaurantsValidator getRestaurantsValidator = new GetRestaurantsValidator();
        final ResourceConfig rc = new ResourceConfig()
            .register(new HealthResource())
            .register(new RestaurantResource(restaurantService, reservationService, createRestaurantValidator, createReservationValidator, getRestaurantsValidator))
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
