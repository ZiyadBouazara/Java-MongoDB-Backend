package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.models.HoursDTO;
import ca.ulaval.glo2003.controllers.models.ReservationConfigurationDTO;
import ca.ulaval.glo2003.controllers.models.RestaurantResponse;
import ca.ulaval.glo2003.domain.hours.HoursAssembler;
import ca.ulaval.glo2003.domain.repositories.RestaurantAndReservationRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.domain.hours.Hours;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantService {
    private final RestaurantAndReservationRepository restaurantAndReservationRepository;
    private final RestaurantFactory restaurantFactory;
    private final HoursAssembler hoursAssembler;

    @Inject
    public RestaurantService(RestaurantAndReservationRepository restaurantAndReservationRepository,
                             RestaurantFactory restaurantFactory,
                             HoursAssembler hoursAssembler) {

        this.restaurantAndReservationRepository = restaurantAndReservationRepository;
        this.restaurantFactory = restaurantFactory;
        this.hoursAssembler = hoursAssembler;
    }

    public String createRestaurant(String ownerId,
                                   String name,
                                   Integer capacity,
                                   HoursDTO hoursDto,
                                   ReservationConfigurationDTO reservationsDuration) {
        Hours hours = hoursAssembler.fromDTO(hoursDto);
        Restaurant restaurant = restaurantFactory.buildRestaurant(ownerId, name, capacity, hours, reservationsDuration);
        restaurantAndReservationRepository.saveRestaurant(restaurant);
        return restaurant.getId();
    }

    public List<RestaurantResponse> getRestaurantsForOwnerId(String ownerId) {
        List<Restaurant> ownerRestaurants = restaurantAndReservationRepository.findRestaurantsByOwnerId(ownerId);
        return ownerRestaurants.stream()
                .map(RestaurantResponse::new)
                .collect(Collectors.toList());
    }

    public RestaurantResponse getRestaurant(String restaurantId) {
        Restaurant restaurant = restaurantAndReservationRepository.findRestaurantByRestaurantId(restaurantId);
        if (restaurant == null) {
            throw new NotFoundException("Restaurant with ID " + restaurantId + " not found");
        }
        return new RestaurantResponse(restaurant);
    }
}
