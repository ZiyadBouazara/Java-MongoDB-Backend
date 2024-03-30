package ca.ulaval.glo2003.controllers;

import ca.ulaval.glo2003.controllers.requests.FuzzySearchRequest;
import ca.ulaval.glo2003.controllers.responses.FuzzySearchResponse;
import ca.ulaval.glo2003.controllers.validators.CreateRestaurantValidator;
import ca.ulaval.glo2003.controllers.validators.GetRestaurantValidator;
import ca.ulaval.glo2003.controllers.validators.HeaderValidator;
import ca.ulaval.glo2003.controllers.validators.SearchRestaurantValidator;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.controllers.requests.RestaurantRequest;
import ca.ulaval.glo2003.controllers.responses.RestaurantResponse;
import ca.ulaval.glo2003.domain.utils.FuzzySearch;
import ca.ulaval.glo2003.service.RestaurantService;
import ca.ulaval.glo2003.service.assembler.FuzzySearchAssembler;
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
    private final CreateRestaurantValidator createRestaurantValidator;
    private final GetRestaurantValidator getRestaurantValidator;
    private final HeaderValidator headerValidator;
    private final SearchRestaurantValidator restaurantSearchValidator;
    private final FuzzySearchAssembler fuzzySearchAssembler;

    @Inject
    public RestaurantResource(RestaurantService restaurantService,
                              HeaderValidator headerValidator,
                              CreateRestaurantValidator createRestaurantValidator,
                              GetRestaurantValidator getRestaurantValidator,
                              SearchRestaurantValidator searchRestaurantValidator,
                              FuzzySearchAssembler fuzzySearchAssembler) {
        this.restaurantService = restaurantService;
        this.headerValidator = headerValidator;
        this.createRestaurantValidator = createRestaurantValidator;
        this.getRestaurantValidator = getRestaurantValidator;
        this.restaurantSearchValidator = searchRestaurantValidator;
        this.fuzzySearchAssembler = fuzzySearchAssembler;
    }

    @GET
    @Path("restaurants")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RestaurantResponse> getRestaurants(@HeaderParam("Owner") String ownerId) throws MissingParameterException {
        headerValidator.verifyMissingHeader(ownerId);
        return restaurantService.getRestaurantsForOwnerId(ownerId);
    }

    @POST
    @Path("restaurants")
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
    @Path("restaurants/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestaurantResponse getRestaurant(@HeaderParam("Owner") String ownerId, @PathParam("id") String restaurantId)
        throws MissingParameterException, NotFoundException {
        headerValidator.verifyMissingHeader(ownerId);
        RestaurantResponse response = restaurantService.getRestaurant(restaurantId);
        getRestaurantValidator.validateRestaurantOwnership(ownerId, response.ownerId());
        return response;
    }

    @POST
    @Path("search/restaurants")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<FuzzySearchResponse> searchRestaurants(FuzzySearchRequest search) throws InvalidParameterException {
        restaurantSearchValidator.verifyFuzzySearchValidParameters(search);

        //TODO: Confirm if the switch should be done on controller layer or service layer
        FuzzySearch fuzzySearch = fuzzySearchAssembler.fromDTO(search);
        return restaurantService.getAllRestaurantsForSearch(fuzzySearch);
    }
}

