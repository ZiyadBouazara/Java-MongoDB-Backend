package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.assemblers.FuzzySearchResponseAssembler;
import ca.ulaval.glo2003.controllers.assemblers.RestaurantResponseAssembler;
import ca.ulaval.glo2003.controllers.requests.FuzzySearchRequest;
import ca.ulaval.glo2003.controllers.requests.RestaurantRequest;
import ca.ulaval.glo2003.controllers.responses.FuzzySearchResponse;
import ca.ulaval.glo2003.controllers.validators.CreateRestaurantValidator;
import ca.ulaval.glo2003.controllers.validators.GetRestaurantValidator;
import ca.ulaval.glo2003.controllers.validators.HeaderValidator;
import ca.ulaval.glo2003.controllers.validators.SearchRestaurantValidator;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.utils.FuzzySearch;
import ca.ulaval.glo2003.service.assembler.FuzzySearchAssembler;
import ca.ulaval.glo2003.controllers.responses.RestaurantResponse;
import ca.ulaval.glo2003.service.assembler.HoursAssembler;
import ca.ulaval.glo2003.domain.repositories.RestaurantAndReservationRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantFactory;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantService {

    private final CreateRestaurantValidator createRestaurantValidator = new CreateRestaurantValidator();
    private final GetRestaurantValidator getRestaurantValidator = new GetRestaurantValidator();
    private final HeaderValidator headerValidator = new HeaderValidator();
    private final SearchRestaurantValidator restaurantSearchValidator = new SearchRestaurantValidator();
    private final RestaurantAndReservationRepository restaurantAndReservationRepository;
    private final RestaurantFactory restaurantFactory;
    private final HoursAssembler hoursAssembler;
    private final RestaurantResponseAssembler restaurantResponseAssembler;
    private final FuzzySearchAssembler fuzzySearchAssembler;

    private final FuzzySearchResponseAssembler fuzzySearchResponseAssembler;

    @Inject
    public RestaurantService(RestaurantAndReservationRepository restaurantAndReservationRepository,
                             RestaurantFactory restaurantFactory, HoursAssembler hoursAssembler,
                             RestaurantResponseAssembler restaurantResponseAssembler,
                             FuzzySearchAssembler fuzzySearchAssembler,
                             FuzzySearchResponseAssembler fuzzySearchResponseAssembler) {

        this.restaurantAndReservationRepository = restaurantAndReservationRepository;
        this.restaurantFactory = restaurantFactory;
        this.hoursAssembler = hoursAssembler;
        this.restaurantResponseAssembler = restaurantResponseAssembler;
        this.fuzzySearchAssembler = fuzzySearchAssembler;
        this.fuzzySearchResponseAssembler = fuzzySearchResponseAssembler;
    }

    public String createRestaurant(String ownerId,
                                   RestaurantRequest restaurantRequest)
            throws InvalidParameterException, MissingParameterException {

        headerValidator.verifyMissingHeader(ownerId);
        createRestaurantValidator.validate(ownerId, restaurantRequest);

        Restaurant restaurant = restaurantFactory.createRestaurant(ownerId, restaurantRequest);
        restaurantAndReservationRepository.saveRestaurant(restaurant);
        return restaurant.getId();
    }

    public List<RestaurantResponse> getRestaurantsForOwnerId(String ownerId)
            throws MissingParameterException {
        headerValidator.verifyMissingHeader(ownerId);

        List<Restaurant> ownerRestaurants = restaurantAndReservationRepository.findRestaurantsByOwnerId(ownerId);
        return ownerRestaurants.stream()
            .map(restaurantResponseAssembler::toDTO)
            .collect(Collectors.toList());
    }

    public RestaurantResponse getRestaurant(String ownerId, String restaurantId)
            throws MissingParameterException {
        headerValidator.verifyMissingHeader(ownerId);
        Restaurant restaurant = restaurantAndReservationRepository.findRestaurantByRestaurantId(restaurantId);
        if (restaurant == null) {
            throw new NotFoundException("Restaurant with ID " + restaurantId + " not found");
        }

        getRestaurantValidator.validateRestaurantOwnership(ownerId, restaurant.getOwnerId());
        return restaurantResponseAssembler.toDTO(restaurant);
    }

    public List<FuzzySearchResponse> getAllRestaurantsForSearch(FuzzySearchRequest search)
            throws InvalidParameterException {
        restaurantSearchValidator.verifyFuzzySearchValidParameters(search);

        List<FuzzySearchResponse> searchedRestaurants = new ArrayList<>();

        for (Restaurant restaurant : restaurantAndReservationRepository.getAllRestaurants()) {
            if (shouldMatchRestaurantName(search, restaurant) &&
                    shouldMatchRestaurantHours(search, restaurant)) {
                searchedRestaurants.add(getFuzzySearchResponseForRestaurant(restaurant));
            }
        }
        return searchedRestaurants;
    }

    //TODO: (possibility to move these elsewhere in utils of service layer)
    public boolean shouldMatchRestaurantName(FuzzySearchRequest search, Restaurant restaurant) {
        return search.name() == null || FuzzySearch.isFuzzySearchOnNameSuccessful(search.name(), restaurant.getName());
    }

    public boolean shouldMatchRestaurantHours(FuzzySearchRequest search, Restaurant restaurant) {
        if (search.opened() == null) {
            return true;
        }

        return FuzzySearch.isFromTimeMatching(search.opened().from(), restaurant.getHours().getOpen()) &&
                FuzzySearch.isToTimeMatching(search.opened().to(), restaurant.getHours().getClose());
    }

    public FuzzySearchResponse getFuzzySearchResponseForRestaurant(Restaurant restaurant) {
        /*return new FuzzySearchResponse(
            restaurant.getId(),
            restaurant.getName(),
            restaurant.getCapacity(),
            hoursAssembler.toDTO(restaurant.getHours()));*/
       return fuzzySearchResponseAssembler.toDTO(restaurant);
    }
}
