package ca.ulaval.glo2003.controllers;

import ca.ulaval.glo2003.Main;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.ReservationConfiguration;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.utils.ResourcesHandler;
import ca.ulaval.glo2003.domain.factories.RestaurantFactory;
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
import java.time.LocalTime;
import java.time.Duration;
import java.util.List;

import static ca.ulaval.glo2003.models.RestaurantRequest.verifyRestaurantOwnership;

@Path("restaurants")
public class RestaurantResource {
    private ResourcesHandler resourcesHandler;
    private RestaurantFactory restaurantFactory;

    public RestaurantResource() {
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
        verifyMissingHeader(ownerId);
        restaurantRequest.verifyParameters();

        Restaurant restaurant = restaurantFactory.buildRestaurant(ownerId, restaurantRequest);
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
    public Response createReservation(@PathParam("id") String restaurantId, ReservationRequest reservationRequest)
        throws NotFoundException, InvalidParameterException, MissingParameterException {
        verifyValidRestaurantIdPath(restaurantId);
        reservationRequest.verifyParameters();
        verifyValidReservationEndTime(reservationRequest, restaurantId);
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

    private void verifyValidRestaurantIdPath(String restaurantId) {
        if (resourcesHandler.getRestaurant(restaurantId) == null) {
            throw new NotFoundException("Restaurant not found with ID: " + restaurantId);
        }
    }

    private void verifyMissingHeader(String ownerId) throws MissingParameterException {
        if (ownerId == null) {
            throw new MissingParameterException("Missing 'Owner' header");
        }
    }

    private void verifyValidReservationEndTime(ReservationRequest reservationRequest, String restaurantId)
            throws InvalidParameterException {
        Restaurant restaurant = resourcesHandler.getRestaurant(restaurantId);
        ReservationConfiguration reservationConfiguration = restaurant.getRestaurantConfiguration();
        LocalTime reservationEndTime = calculateReservationEndTime(reservationRequest, reservationConfiguration);
        LocalTime closingTime = LocalTime.parse(restaurant.getHours().getClose());
        LocalTime openingTime = LocalTime.parse(restaurant.getHours().getOpen());
        verifyReservationWithinOperatingHours(reservationEndTime, closingTime, openingTime);
    }

    private LocalTime calculateReservationEndTime(
            ReservationRequest reservationRequest, ReservationConfiguration reservationConfiguration) {
        LocalTime reservationStartTime = LocalTime.parse(reservationRequest.getStartTime());
        Duration reservationDuration = Duration.ofMinutes(reservationConfiguration.getDuration());
        return reservationStartTime.plus(reservationDuration);
    }

    private void verifyReservationWithinOperatingHours(
            LocalTime reservationEndTime, LocalTime closingTime, LocalTime openingTime) throws InvalidParameterException {
        if (reservationEndTime.isAfter(closingTime) || reservationEndTime.isBefore(openingTime)) {
            throw new InvalidParameterException(
                    "Invalid reservation start time, the reservation exceeds the restaurant's closing time");
        }
    }
}
