package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.domain.Hours;
import ca.ulaval.glo2003.domain.Restaurant;

public class RestaurantResponse {
    public String id;
    public String name;
    public Integer capacity;
    public Hours hours;

    public RestaurantResponse(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.capacity = restaurant.getCapacity();
        this.hours = restaurant.getHours();
    }
}
