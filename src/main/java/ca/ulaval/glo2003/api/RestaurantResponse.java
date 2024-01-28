package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.domain.Hours;
import ca.ulaval.glo2003.domain.Restaurant;

import java.util.UUID;

public class RestaurantResponse {
    // public attributes on purpose so that
    // we can access them and return them in RestaurantResource
    public String id;
    public String name;
    public int capacity;
    public Hours hours;

    public RestaurantResponse(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.capacity = restaurant.getCapacity();
        this.hours = restaurant.getHours();
    }
}
