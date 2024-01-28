package ca.ulaval.glo2003.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Restaurateur {
    private final String ownerId;
    private List<Restaurant> restaurants;

    public Restaurateur(String ownerId) {
        this.ownerId = UUID.randomUUID().toString();
        this.restaurants = new ArrayList<>();
    }

    public String getOwnerId() {
        return ownerId;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void addRestaurant(Restaurant restaurant) {
//        restaurants.add(restaurant);
        if (!restaurants.contains(restaurant)) {
            restaurants.add(restaurant);
        } else {
            //maybe validate the id before adding?
            throw new IllegalArgumentException("Un restaurant avec le meme ID existe deja");
        }
    }
}
