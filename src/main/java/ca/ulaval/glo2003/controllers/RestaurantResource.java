package ca.ulaval.glo2003.controllers;

import ca.ulaval.glo2003.Main;
import ca.ulaval.glo2003.domain.Reservation;
import ca.ulaval.glo2003.domain.Restaurant;
import ca.ulaval.glo2003.domain.InvalidParameterException;
import ca.ulaval.glo2003.domain.MissingParameterException;
import ca.ulaval.glo2003.domain.ResourcesHandler;
import ca.ulaval.glo2003.models.ReservationRequest;
import ca.ulaval.glo2003.models.RestaurantRequest;
import ca.ulaval.glo2003.models.RestaurantResponse;
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
    private ResourcesHandler resourcesHandler;

    public RestaurantResource() {
        this.resourcesHandler = new ResourcesHandler();
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
        verifyMissingHeader(ownerId);
        restaurantRequest.verifyParameters();
        Restaurant restaurant = new Restaurant(
            ownerId,
            restaurantRequest.getName(),
            restaurantRequest.getCapacity(),
            restaurantRequest.getHours());

        resourcesHandler.addRestaurant(restaurant);
        URI newProductURI = UriBuilder.fromResource(RestaurantResource.class).path(restaurant.getId()).build();
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
    public Response createReservation(@PathParam("id") String restaurantId, ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(
                restaurantId,
                reservationRequest.getDate(),
                reservationRequest.getStartTime(),
                reservationRequest.getGroupSize(),
                reservationRequest.getCustomer());

        resourcesHandler.addReservation(reservation);
        URI newReservationURI = UriBuilder.fromPath(Main.BASE_URI)
                .path("reservations")
                .path(reservation.getId())
                .build();
        return Response.created(newReservationURI).build();
    }

    private void verifyMissingHeader(String ownerId) throws MissingParameterException {
        if (ownerId == null) {
            throw new MissingParameterException("Missing 'Owner' header");
        }
    }

    private void verifyRestaurantOwnership(String expectedOwnerId, String actualOwnerId) throws NotFoundException {
        if (!expectedOwnerId.equals(actualOwnerId)) {
            throw new NotFoundException();
        }
    }
}
