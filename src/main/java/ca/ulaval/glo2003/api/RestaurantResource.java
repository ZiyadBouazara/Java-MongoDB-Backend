package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.exceptionMapping.ErrorResponse;
import ca.ulaval.glo2003.domain.InvalidParameterException;
import ca.ulaval.glo2003.domain.MissingParameterException;
import ca.ulaval.glo2003.domain.Restaurant;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;

import static ca.ulaval.glo2003.api.exceptionMapping.ErrorCode.INVALID_PARAMETER;
import static ca.ulaval.glo2003.api.exceptionMapping.ErrorCode.MISSING_PARAMETER;

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

    private void verifyRestaurantOwnership(String ownerId, Restaurant restaurant) throws NotFoundException {
        if (restaurant == null || !restaurant.getOwnerId().equals(ownerId)) {
            throw new NotFoundException("Le restaurant n'appartient pas au restaurateur");
        }
    }

    @GET
    @Path("/{restaurantId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurant(@HeaderParam("Owner") String ownerID, @PathParam("restaurantId") String restaurantId){
        try {
            verifyHeader(ownerID);

            Restaurant restaurant = restaurateur.getRestaurantById(restaurantId);

            verifyRestaurantOwnership(ownerID, restaurant);

            return Response.ok().entity(restaurant).build();
        }
        catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(MISSING_PARAMETER, e.getMessage()))
                    .build();
        }
        catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(INVALID_PARAMETER, e.getMessage()))
                    .build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(INVALID_PARAMETER, e.getMessage()))
                    .build();
        }
    }
}
