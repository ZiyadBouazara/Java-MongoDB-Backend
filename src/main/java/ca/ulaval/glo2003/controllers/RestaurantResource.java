package ca.ulaval.glo2003.controllers;

import ca.ulaval.glo2003.controllers.requests.FuzzySearchRequest;
import ca.ulaval.glo2003.controllers.responses.AvailabilitiesResponse;
import ca.ulaval.glo2003.controllers.responses.FuzzySearchResponse;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.controllers.requests.RestaurantRequest;
import ca.ulaval.glo2003.controllers.responses.RestaurantResponse;
import ca.ulaval.glo2003.domain.utils.Availabilities;
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


@Path("/")
public class RestaurantResource {
    private final RestaurantService restaurantService;

    @Inject
    public RestaurantResource(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GET
    @Path("restaurants")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RestaurantResponse> getRestaurants(@HeaderParam("Owner") String ownerId) throws MissingParameterException {
        return restaurantService.getRestaurantsForOwnerId(ownerId);
    }

    @POST
    @Path("restaurants")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRestaurant(@HeaderParam("Owner") String ownerId, RestaurantRequest restaurantRequest)
        throws NotFoundException, InvalidParameterException, MissingParameterException {

        String restaurantId = restaurantService.createRestaurant(
            ownerId, restaurantRequest);

        URI newProductURI = UriBuilder.fromResource(RestaurantResource.class).path(restaurantId).build();
        return Response.created(newProductURI).build();
    }

    @GET
    @Path("restaurants/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestaurantResponse getRestaurant(@HeaderParam("Owner") String ownerId, @PathParam("id") String restaurantId)
        throws MissingParameterException, NotFoundException {
        return restaurantService.getRestaurant(ownerId, restaurantId);
    }

    @POST
    @Path("search/restaurants")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<FuzzySearchResponse> searchRestaurants(FuzzySearchRequest search) throws InvalidParameterException {
        return restaurantService.getAllRestaurantsForSearch(search);
    }

    @GET
    @Path("restaurants/{id}/availabilities")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AvailabilitiesResponse> getAvailabilities(@PathParam("id") String restaurantId, @QueryParam("availabilities") String date)
        throws MissingParameterException, InvalidParameterException, NotFoundException {
        return restaurantService.getAvailabilitiesForRestaurant(restaurantId, date);
    }
}

