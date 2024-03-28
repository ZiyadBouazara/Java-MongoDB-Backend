package ca.ulaval.glo2003.controllers;

import ca.ulaval.glo2003.Main;
import ca.ulaval.glo2003.controllers.requests.FuzzySearchRequest;
import ca.ulaval.glo2003.controllers.requests.ReservationRequest;
import ca.ulaval.glo2003.controllers.responses.FuzzySearchResponse;
import ca.ulaval.glo2003.controllers.validators.CreateReservationValidator;
import ca.ulaval.glo2003.controllers.validators.CreateRestaurantValidator;
import ca.ulaval.glo2003.controllers.validators.GetRestaurantValidator;
import ca.ulaval.glo2003.controllers.validators.HeaderValidator;
import ca.ulaval.glo2003.controllers.validators.SearchRestaurantValidator;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.controllers.requests.RestaurantRequest;
import ca.ulaval.glo2003.controllers.responses.RestaurantResponse;
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
    private final ReservationService reservationService;
    private final RestaurantService restaurantService;
    private final CreateRestaurantValidator createRestaurantValidator;
    private final GetRestaurantValidator getRestaurantValidator;
    private final HeaderValidator headerValidator;
    private final CreateReservationValidator createReservationValidator;
    private final SearchRestaurantValidator restaurantSearchValidator;

    @Inject
    public RestaurantResource(ReservationService reservationService, RestaurantService restaurantService,
                              HeaderValidator headerValidator,
                              CreateRestaurantValidator createRestaurantValidator,
                              GetRestaurantValidator getRestaurantValidator,
                              SearchRestaurantValidator restaurantSearchValidator,
                              CreateReservationValidator createReservationValidator) {
        this.reservationService = reservationService;
        this.restaurantService = restaurantService;
        this.headerValidator = headerValidator;
        this.createRestaurantValidator = createRestaurantValidator;
        this.getRestaurantValidator = getRestaurantValidator;
        this.restaurantSearchValidator = restaurantSearchValidator;
        this.createReservationValidator = createReservationValidator;
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
    public RestaurantResponse getRestaurant(@HeaderParam("Owner") String ownerId, @PathParam("id") String restaurantId)
        throws MissingParameterException, NotFoundException {
        headerValidator.verifyMissingHeader(ownerId);
        RestaurantResponse response = restaurantService.getRestaurant(restaurantId);
        getRestaurantValidator.validateRestaurantOwnership(ownerId, response.ownerId());
        return response;
    }

    @POST
    @Path("/search/restaurants")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<FuzzySearchResponse> searchRestaurants(FuzzySearchRequest search) throws InvalidParameterException {
        SearchRestaurantValidator.verifyFuzzySearchValidParameters(search);
        return restaurantService.getAllRestaurantsForSearch(search);
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
            reservationRequest.customer());

        URI newReservationURI = UriBuilder.fromPath(Main.BASE_URI)
            .path("reservations")
            .path(createdReservationId)
            .build();
        return Response.created(newReservationURI).build();
    }
}

