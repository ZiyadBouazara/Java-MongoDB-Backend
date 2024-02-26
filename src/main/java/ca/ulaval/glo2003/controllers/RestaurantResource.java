package ca.ulaval.glo2003.controllers;

import ca.ulaval.glo2003.Main;
import ca.ulaval.glo2003.controllers.validators.CreateReservationValidator;
import ca.ulaval.glo2003.controllers.validators.CreateRestaurantValidator;
import ca.ulaval.glo2003.controllers.validators.GetRestaurantValidator;
import ca.ulaval.glo2003.controllers.validators.GetAllRestaurantsValidator;
import ca.ulaval.glo2003.controllers.validators.HeaderValidator;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.controllers.models.ReservationRequest;
import ca.ulaval.glo2003.controllers.models.RestaurantRequest;
import ca.ulaval.glo2003.controllers.models.RestaurantResponse;
import ca.ulaval.glo2003.service.ReservationService;
import ca.ulaval.glo2003.service.RestaurantService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;
import java.util.List;



@Path("restaurants")
public class RestaurantResource {
    private final RestaurantService restaurantService;
    private final ReservationService reservationService;
    private final CreateRestaurantValidator createRestaurantValidator;
    private final CreateReservationValidator createReservationValidator;
    private final GetAllRestaurantsValidator getAllRestaurantsValidator;
    private final GetRestaurantValidator getRestaurantValidator;
    private final HeaderValidator headerValidator;

    @Inject
    public RestaurantResource(RestaurantService restaurantService,
                              ReservationService reservationService,
                              HeaderValidator headerValidator,
                              CreateRestaurantValidator createRestaurantValidator,
                              CreateReservationValidator createReservationValidator,
                              GetAllRestaurantsValidator getAllRestaurantsValidator,
                              GetRestaurantValidator getRestaurantValidator) {

        this.restaurantService = restaurantService;
        this.reservationService = reservationService;
        this.headerValidator = headerValidator;
        this.createRestaurantValidator = createRestaurantValidator;
        this.createReservationValidator = createReservationValidator;
        this.getAllRestaurantsValidator = getAllRestaurantsValidator;
        this.getRestaurantValidator = getRestaurantValidator;
        System.out.println("InMemoryRestaurantAndReservationRepository instance created");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RestaurantResponse> getRestaurants(@HeaderParam("Owner") String ownerId) throws MissingParameterException {
        headerValidator.verifyMissingHeader(ownerId);
        return restaurantService.getRestaurantsForOwnerId(ownerId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRestaurant(@HeaderParam("Owner") String ownerId, RestaurantRequest restaurantRequest)
        throws InvalidParameterException, MissingParameterException, NotFoundException {
        headerValidator.verifyMissingHeader(ownerId);
        createRestaurantValidator.validate(ownerId, restaurantRequest);

        String restaurantId = restaurantService.createRestaurant(
                ownerId,
                restaurantRequest.name(),
                restaurantRequest.capacity(),
                restaurantRequest.hours(),
                restaurantRequest.reservations());

        URI newProductURI = UriBuilder.fromResource(RestaurantResource.class).path(restaurantId).build();
        return Response.created(newProductURI).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurant(@HeaderParam("Owner") String ownerId, @PathParam("id") String restaurantId)
        throws MissingParameterException, NotFoundException {
        headerValidator.verifyMissingHeader(ownerId);
        RestaurantResponse response = restaurantService.getRestaurant(restaurantId);
        getRestaurantValidator.validateRestaurantOwnership(ownerId, response.ownerId());
        return Response.ok(response).build();
    }

    @POST
    @Path("/{id}/reservations")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReservation(@PathParam("id") String restaurantId, ReservationRequest reservationRequest)
            throws InvalidParameterException, MissingParameterException {
        createReservationValidator.validateReservationRequest(reservationRequest);
        String createdReservationId = reservationService.createReservation(
                restaurantId,
                reservationRequest.date(),
                reservationRequest.startTime(),
                reservationRequest.groupSize(),
                reservationRequest.customer().name(),
                reservationRequest.customer().email(),
                reservationRequest.customer().phoneNumber()
                );

        URI newReservationURI = UriBuilder.fromPath(Main.BASE_URI)
            .path("reservations")
            .path(createdReservationId)
            .build();
        return Response.created(newReservationURI).build();
    }


}
