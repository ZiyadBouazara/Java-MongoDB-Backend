package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.assemblers.AvailabilitiesResponseAssembler;
import ca.ulaval.glo2003.controllers.assemblers.FuzzySearchResponseAssembler;
import ca.ulaval.glo2003.controllers.assemblers.RestaurantResponseAssembler;
import ca.ulaval.glo2003.controllers.requests.FuzzySearchRequest;
import ca.ulaval.glo2003.controllers.requests.RestaurantRequest;
import ca.ulaval.glo2003.controllers.responses.AvailabilitiesResponse;
import ca.ulaval.glo2003.controllers.responses.FuzzySearchResponse;
import ca.ulaval.glo2003.controllers.responses.RestaurantResponseWithReviews;
import ca.ulaval.glo2003.domain.repositories.ReservationRepository;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.utils.Availabilities;
import ca.ulaval.glo2003.domain.repositories.RestaurantRepository;
import ca.ulaval.glo2003.service.assembler.FuzzySearchAssembler;
import ca.ulaval.glo2003.service.assembler.HoursAssembler;
import ca.ulaval.glo2003.service.validators.ReservationValidator;
import ca.ulaval.glo2003.service.validators.HeaderValidator;
import ca.ulaval.glo2003.service.validators.GetRestaurantValidator;
import ca.ulaval.glo2003.service.validators.CreateRestaurantValidator;
import ca.ulaval.glo2003.service.validators.SearchRestaurantValidator;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.utils.FuzzySearch;
import ca.ulaval.glo2003.controllers.responses.RestaurantResponse;
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
    private final ReservationValidator reservationValidator;
    private final SearchRestaurantValidator restaurantSearchValidator = new SearchRestaurantValidator();
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final RestaurantFactory restaurantFactory;
    private final HoursAssembler hoursAssembler;
    private final RestaurantResponseAssembler restaurantResponseAssembler;
    private final FuzzySearchAssembler fuzzySearchAssembler;
    private final AvailabilitiesResponseAssembler availabilitiesResponseAssembler = new AvailabilitiesResponseAssembler();
    private final FuzzySearchResponseAssembler fuzzySearchResponseAssembler;

    @Inject
    public RestaurantService(RestaurantRepository restaurantRepository,
                             RestaurantFactory restaurantFactory,
                             HoursAssembler hoursAssembler, RestaurantResponseAssembler restaurantResponseAssembler,
                             FuzzySearchAssembler fuzzySearchAssembler, FuzzySearchResponseAssembler fuzzySearchResponseAssembler,
                             ReservationRepository reservationRepository, ReservationValidator reservationValidator) {
        this.restaurantRepository = restaurantRepository;
        this.reservationRepository = reservationRepository;
        this.restaurantFactory = restaurantFactory;
        this.hoursAssembler = hoursAssembler;
        this.restaurantResponseAssembler = restaurantResponseAssembler;
        this.fuzzySearchAssembler = fuzzySearchAssembler;
        this.fuzzySearchResponseAssembler = fuzzySearchResponseAssembler;
        this.reservationValidator = reservationValidator;
    }

    public String createRestaurant(String ownerId, RestaurantRequest restaurantRequest)
        throws InvalidParameterException, MissingParameterException {

        headerValidator.verifyMissingHeader(ownerId);
        createRestaurantValidator.validate(ownerId, restaurantRequest);

        Restaurant restaurant = restaurantFactory.createRestaurant(ownerId, restaurantRequest.name(), restaurantRequest.capacity(),
            restaurantRequest.hours(), restaurantRequest.reservations());
        restaurantRepository.saveRestaurant(restaurant);
        return restaurant.getId();
    }

    public void deleteRestaurant(String ownerId, String restaurantId) throws MissingParameterException, NotFoundException {
        headerValidator.verifyMissingHeader(ownerId);
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);
        getRestaurantValidator.validateRestaurantOwnership(ownerId, restaurant.getOwnerId());
        restaurantRepository.deleteRestaurant(ownerId, restaurantId);
        reservationRepository.deleteReservationsWithRestaurantId(restaurantId);
    }

    public List<RestaurantResponse> getRestaurantsForOwnerId(String ownerId) throws MissingParameterException {
        headerValidator.verifyMissingHeader(ownerId);

        List<Restaurant> ownerRestaurants = restaurantRepository.findRestaurantsByOwnerId(ownerId);
        return ownerRestaurants.stream().map(restaurantResponseAssembler::toDTO).collect(Collectors.toList());
    }

    public List<RestaurantResponseWithReviews> getRestaurantsWithReviewsForOwnerId(String ownerId) throws MissingParameterException {
        headerValidator.verifyMissingHeader(ownerId);

        List<Restaurant> ownerRestaurants = restaurantRepository.findRestaurantsByOwnerId(ownerId);
        return ownerRestaurants.stream().map(restaurantResponseAssembler::toDTOv2).collect(Collectors.toList());
    }

    public RestaurantResponse getRestaurant(String ownerId, String restaurantId) throws MissingParameterException {
        headerValidator.verifyMissingHeader(ownerId);
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);
        getRestaurantValidator.validateRestaurantOwnership(ownerId, restaurant.getOwnerId());
        return restaurantResponseAssembler.toDTO(restaurant);
    }

    public RestaurantResponseWithReviews getRestaurantWithReviews(String ownerId, String restaurantId)
            throws MissingParameterException {
        headerValidator.verifyMissingHeader(ownerId);
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);
        getRestaurantValidator.validateRestaurantOwnership(ownerId, restaurant.getOwnerId());
        return restaurantResponseAssembler.toDTOv2(restaurant);
    }

    public List<FuzzySearchResponse> getAllRestaurantsForSearch(FuzzySearchRequest search)
            throws InvalidParameterException {
        restaurantSearchValidator.verifyFuzzySearchValidParameters(search);

        List<FuzzySearchResponse> searchedRestaurants = new ArrayList<>();

        for (Restaurant restaurant : restaurantRepository.getAllRestaurants()) {
            if (shouldMatchRestaurantName(search, restaurant) && shouldMatchRestaurantHours(search, restaurant)) {
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
        return fuzzySearchResponseAssembler.toDTO(restaurant);
    }

    public List<AvailabilitiesResponse> getAvailabilitiesForRestaurant(String restaurantId, String date)
            throws MissingParameterException, InvalidParameterException {
        reservationValidator.verifySearchAvailabilities(date);

        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);
        Availabilities availabilities = new Availabilities();
        List<Reservation> restaurantReservationList = reservationRepository.getReservationsByDate(restaurantId, date);
        List<Availabilities> availabilitiesForRestaurant = availabilities.
                getAvailabilitiesForRestaurant(restaurant, restaurantReservationList, date);

        return availabilitiesForRestaurant.stream()
                .map(availabilitiesResponseAssembler::toDTO)
                .collect(Collectors.toList());
    }
}
