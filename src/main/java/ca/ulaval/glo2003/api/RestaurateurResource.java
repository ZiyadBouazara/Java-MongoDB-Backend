package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.RestaurantRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
public class RestaurateurResource {

    @POST
    @Path("restaurants")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRestaurant(@HeaderParam("Owner") String ownerId, RestaurantRequest restaurantRequest){
        return Response.ok().build();//TODO
    }
}
