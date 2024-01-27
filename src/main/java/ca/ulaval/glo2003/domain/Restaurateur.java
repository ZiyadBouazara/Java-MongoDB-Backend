package ca.ulaval.glo2003.domain;

import java.util.ArrayList;
import java.util.List;

public class Restaurateur {
    private final String id;
    private List<Restaurant> restaurants;

    public Restaurateur(String id) {
        this.id = id;
        this.restaurants = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }
}
