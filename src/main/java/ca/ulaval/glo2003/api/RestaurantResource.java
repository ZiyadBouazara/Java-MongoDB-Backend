package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.exceptionMapping.ErrorResponse;
import ca.ulaval.glo2003.domain.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;

import static ca.ulaval.glo2003.api.exceptionMapping.ErrorCode.INVALID_PARAMETER;
import static ca.ulaval.glo2003.api.exceptionMapping.ErrorCode.MISSING_PARAMETER;

@Path("restaurants")
public class RestaurantResource {
    private ResourcesHandler resourcesHandler;

    public RestaurantResource() {
        this.resourcesHandler = new ResourcesHandler();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRestaurant(@HeaderParam("Owner") String ownerId, RestaurantRequest restaurantRequest)
            throws InvalidParameterException, MissingParameterException, NotFoundException {
        verifyMissingHeader(ownerId);
        verifyParameters(restaurantRequest);
        Restaurant restaurant = new Restaurant(
                ownerId,
                restaurantRequest.getName(),
                restaurantRequest.getCapacity(),
                restaurantRequest.getHours());

        resourcesHandler.addRestaurant(restaurant); // store in map to access it without having to create Restaurateur object
        URI newProductURI = UriBuilder.fromResource(RestaurantResource.class).path(restaurant.getId()).build();
        return Response.created(newProductURI).build();
    }

    @GET
    @Path("/{restaurantId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurant(@HeaderParam("Owner") String ownerID, @PathParam("restaurantId") String restaurantId)
            throws MissingParameterException, NotFoundException{
        verifyMissingHeader(ownerID);
        Restaurant restaurant = resourcesHandler.getRestaurant(restaurantId);
        verifyRestaurantOwnership(restaurant.getOwnerId(), ownerID);
        return Response.ok(new RestaurantResponse(restaurant)).build();
    }

    private void verifyMissingHeader(String ownerId) throws MissingParameterException {
        if (ownerId == null) {
            throw new MissingParameterException("Missing 'Owner' header");
        }
    }

    private void verifyParameters(RestaurantRequest restaurantRequest)
            throws InvalidParameterException, MissingParameterException {
        restaurantRequest.verifyMissingParameters();
        restaurantRequest.verifyValidParameters();
    }

    private void verifyRestaurantOwnership(String expectedOwnerId, String actualOwnerId) throws NotFoundException {
        if (!expectedOwnerId.equals(actualOwnerId)) {
            throw new NotFoundException(); // the restaurant is not owned by the restaurateur
        }
    }
}
