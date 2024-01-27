package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.domain.InvalidParameterException;
import ca.ulaval.glo2003.domain.MissingParameterException;
import ca.ulaval.glo2003.domain.Restaurant;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;

@Path("restaurants")
public class RestaurantResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRestaurant(@HeaderParam("Owner") String ownerId, RestaurantRequest restaurantRequest) throws InvalidParameterException, MissingParameterException, NotFoundException {
        verifyHeader(ownerId);
        verifyParameters(restaurantRequest);
        Restaurant restaurant = new Restaurant(
                ownerId,
                restaurantRequest.getName(),
                restaurantRequest.getCapacity(),
                restaurantRequest.getHours());
        URI newProductURI = UriBuilder.fromResource(RestaurantResource.class).path(restaurant.getId()).build();
        return Response.created(newProductURI).build();
    }

    private void verifyParameters(RestaurantRequest restaurantRequest) throws InvalidParameterException, MissingParameterException {
        restaurantRequest.verifyMissingParameters();
        restaurantRequest.verifyValidParameters();
    }

    private void verifyHeader(String ownerId) throws NotFoundException {
        if (ownerId == null) {
            throw new NotFoundException("Missing 'Owner' header");
        }
    }
}
