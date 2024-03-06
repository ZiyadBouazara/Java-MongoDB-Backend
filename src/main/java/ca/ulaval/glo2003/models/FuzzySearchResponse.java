package ca.ulaval.glo2003.models;

import ca.ulaval.glo2003.domain.utils.Hours;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public class FuzzySearchResponse {

    public String id;

    public String name;
    public Integer capacity;
    public Hours hours;

    public FuzzySearchResponse(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.capacity = restaurant.getCapacity();
        this.hours = restaurant.getHours();
    }


    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public Hours getHours() {
        return this.hours;
    }
}
