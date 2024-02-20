package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.models.HoursDTO;
import ca.ulaval.glo2003.controllers.models.ReservationConfigurationDTO;
import ca.ulaval.glo2003.controllers.models.RestaurantResponse;
import ca.ulaval.glo2003.domain.repositories.RestaurantAndReservationRepository;
import ca.ulaval.glo2003.domain.restaurant.ReservationConfiguration;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.utils.Hours;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantService {
    private final RestaurantAndReservationRepository restaurantAndReservationRepository;
    public RestaurantService(RestaurantAndReservationRepository restaurantAndReservationRepository){
        this.restaurantAndReservationRepository = restaurantAndReservationRepository;
    }

    public String createRestaurant(String ownerId,
                                 String name,
                                 Integer capacity,
                                 HoursDTO hoursDTO,
                                 ReservationConfigurationDTO reservationsDTO) {
        Hours hours = new Hours(hoursDTO.open(), hoursDTO.close());
        ReservationConfiguration reservations = constructRestaurantBasedOnReservationConfiguration(reservationsDTO);
        Restaurant restaurant = new Restaurant(ownerId, name, capacity, hours, reservations);
        restaurantAndReservationRepository.saveRestaurant(restaurant);
        return restaurant.getId();
    }

    public List<RestaurantResponse> getRestaurantsForOwnerId(String ownerId) {
        List<Restaurant> ownerRestaurants = restaurantAndReservationRepository.findRestaurantsByOwnerId(ownerId);
        return ownerRestaurants.stream()
                .map(RestaurantResponse::new)
                .collect(Collectors.toList());
    }

    private ReservationConfiguration constructRestaurantBasedOnReservationConfiguration(ReservationConfigurationDTO reservationsDTO) {
        ReservationConfiguration reservations;
        if (reservationsDTO != null && reservationsDTO.duration() != null) {
            reservations = new ReservationConfiguration(reservationsDTO.duration());
        } else {
            reservations = new ReservationConfiguration();
        }
        return reservations;
    }
}
