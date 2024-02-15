package ca.ulaval.glo2003.models;

import ca.ulaval.glo2003.domain.Hours;
import ca.ulaval.glo2003.domain.ReservationConfiguration;
import ca.ulaval.glo2003.domain.Restaurant;

public class RestaurantResponse {
    public String id;
    public String name;
    public Integer capacity;
    public Hours hours;
    public ReservationConfiguration reservations;

    public RestaurantResponse(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.capacity = restaurant.getCapacity();
        this.hours = restaurant.getHours();
        this.reservations = restaurant.getRestaurantConfiguration();
    }
}
