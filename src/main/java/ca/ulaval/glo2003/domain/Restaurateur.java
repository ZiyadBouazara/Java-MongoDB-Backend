package ca.ulaval.glo2003.domain;

import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Restaurateur {
    private final String ownerId;
    private List<Restaurant> restaurants;
    private Map<String, Restaurant> restaurantIdMap;

    public Restaurateur() {
        this.ownerId = UUID.randomUUID().toString();
        this.restaurants = new ArrayList<>();
        this.restaurantIdMap = new HashMap<>();
    }

    public String getOwnerId() {
        return ownerId;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void addRestaurant(Restaurant restaurant) {
        if (!restaurantIdMap.containsKey(restaurant.getId())) {
            restaurants.add(restaurant);
            restaurantIdMap.put(restaurant.getId(), restaurant);
        } else {
            throw new IllegalArgumentException("Un restaurant avec le meme Id existe deja");
        }
    }

    public Restaurant getRestaurantById(String restaurantId) {
        if (restaurantIdMap.containsKey(restaurantId)) {
            return restaurantIdMap.get(restaurantId);
        } else {
            throw new NotFoundException("Restaurant introuvable avec le ID fourni: " + restaurantId);
        }
    }

    private Restaurateur getRestaurateurById(String ownerId, Map<String, Restaurateur> restaurateurMap) {
        if (restaurateurMap.containsKey(ownerId)) {
            return restaurateurMap.get(ownerId);
        } else {
            throw new NotFoundException("Restaurateur introuvable avec le ID fourni: " + ownerId);
        }
    }
}
