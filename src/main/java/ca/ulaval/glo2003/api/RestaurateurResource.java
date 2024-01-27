package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.RestaurantRequest;
import ca.ulaval.glo2003.domain.InvalidParameterException;
import ca.ulaval.glo2003.domain.MissingParameterException;
import ca.ulaval.glo2003.domain.Restaurant;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("restaurants")
public class RestaurateurResource {

    @POST
    @Path("restaurants")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRestaurant(@HeaderParam("Owner") String ownerId, RestaurantRequest restaurantRequest) throws InvalidParameterException, MissingParameterException, NotFoundException {
        verifyHeader(ownerId);
        verifyParameters(restaurantRequest);
        Restaurant restaurant = new Restaurant(
                ownerId,
                restaurantRequest.getName(),
                restaurantRequest.getCapacity(),
                restaurantRequest.getHours());
        return Response.ok().build();//TODO

        //On valid les entrée:
//        try {
//            if (restaurantRequest == null || ownerId == null || !restaurantRequest.hasValidParameters()){
//                return Response.status(Response.Status.BAD_REQUEST)
//                        .entity(new ErrorResponse("INVALID_REQUEST", "Invalid or missing request parameters"))
//                        .build();
//            }
//        }
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
