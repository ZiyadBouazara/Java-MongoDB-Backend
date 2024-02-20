package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.models.HoursDTO;
import ca.ulaval.glo2003.controllers.models.ReservationConfigurationDTO;
import ca.ulaval.glo2003.domain.restaurant.ReservationConfiguration;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.domain.factories.RestaurantFactory;
import ca.ulaval.glo2003.domain.utils.Hours;

public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantFactory restaurantFactory;
    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantFactory factory){
        this.restaurantRepository = restaurantRepository;
        this.restaurantFactory = factory;
    }

    public String createRestaurant(String ownerId,
                                 String name,
                                 Integer capacity,
                                 HoursDTO hoursDTO,
                                 ReservationConfigurationDTO reservationsDTO) {
        Hours hours = new Hours(hoursDTO.open(), hoursDTO.close());
        ReservationConfiguration reservations = new ReservationConfiguration(reservationsDTO.duration());
        Restaurant restaurant = restaurantFactory.buildRestaurant(ownerId, name, capacity, hours, reservations);
        restaurantRepository.saveRestaurant(restaurant);
        return restaurant.getId();
    }
}
