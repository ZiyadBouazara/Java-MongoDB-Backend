package ca.ulaval.glo2003.controllers.models;

import ca.ulaval.glo2003.domain.utils.Hours;
import ca.ulaval.glo2003.domain.restaurant.ReservationConfiguration;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public record RestaurantResponse (String id,
                                  String name,
                                  Integer capacity,
                                  Hours hours,
                                  ReservationConfiguration reservations) {
    public RestaurantResponse(Restaurant restaurant) {
        this(restaurant.getId(),
                restaurant.getName(),
                restaurant.getCapacity(),
                restaurant.getHours(),
                restaurant.getRestaurantConfiguration());
    }

}
