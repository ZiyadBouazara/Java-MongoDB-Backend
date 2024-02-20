package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.models.HoursDTO;
import ca.ulaval.glo2003.controllers.models.ReservationConfigurationDTO;
import ca.ulaval.glo2003.domain.repositories.RestaurantAndReservationRepository;
import ca.ulaval.glo2003.domain.restaurant.ReservationConfiguration;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.domain.factories.RestaurantFactory;
import ca.ulaval.glo2003.domain.utils.Hours;

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
