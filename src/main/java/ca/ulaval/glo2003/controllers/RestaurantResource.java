package ca.ulaval.glo2003.controllers;

import ca.ulaval.glo2003.Main;
import ca.ulaval.glo2003.controllers.validators.CreateReservationValidator;
import ca.ulaval.glo2003.controllers.validators.CreateRestaurantValidator;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.utils.ResourcesHandler;
import ca.ulaval.glo2003.domain.factories.RestaurantFactory;
import ca.ulaval.glo2003.controllers.models.ReservationRequest;
import ca.ulaval.glo2003.controllers.models.RestaurantRequest;
import ca.ulaval.glo2003.controllers.models.RestaurantResponse;
import ca.ulaval.glo2003.service.ReservationService;
import ca.ulaval.glo2003.service.RestaurantService;
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

import static ca.ulaval.glo2003.controllers.models.RestaurantRequest.verifyRestaurantOwnership;

@Path("restaurants")
public class RestaurantResource {
    private ResourcesHandler resourcesHandler;
    private RestaurantFactory restaurantFactory;
    private RestaurantService restaurantService;
    private ReservationService reservationService;
    private CreateRestaurantValidator createRestaurantValidator;
    private CreateReservationValidator createReservationValidator;

    public RestaurantResource(RestaurantService restaurantService,
                              ReservationService reservationService,
                              CreateRestaurantValidator createRestaurantValidator,
                              CreateReservationValidator createReservationValidator) {

        this.resourcesHandler = new ResourcesHandler();
        this.restaurantFactory = new RestaurantFactory();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RestaurantResponse> getRestaurants(@HeaderParam("Owner") String ownerId) throws MissingParameterException {
        verifyMissingHeader(ownerId);
        return resourcesHandler.getAllRestaurantsForOwner(ownerId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRestaurant(@HeaderParam("Owner") String ownerId, RestaurantRequest restaurantRequest)
        throws InvalidParameterException, MissingParameterException, NotFoundException {
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
    public Response getRestaurant(@HeaderParam("Owner") String ownerID, @PathParam("id") String restaurantId)
        throws MissingParameterException, NotFoundException {
        verifyMissingHeader(ownerID);
        Restaurant restaurant = resourcesHandler.getRestaurant(restaurantId);
        verifyRestaurantOwnership(restaurant.getOwnerId(), ownerID);
        return Response.ok(new RestaurantResponse(restaurant)).build();
    }

    @POST
    @Path("/{id}/reservations")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReservation(@PathParam("id") String restaurantId, ReservationRequest reservationRequest)
            throws InvalidParameterException, MissingParameterException {
        createReservationValidator.validateReservationRequest(reservationRequest);
        String createdReservationId = reservationService.createReservation(
                reservationRequest.restaurantId(),
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
