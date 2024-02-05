package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.api.RestaurantResponse;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourcesHandler {
    private final Map<String, Restaurateur> restaurateurs; // map {ownerId : Restaurateur}, TODO: will be useful for GET/restaurateurs
    private final Map<String, Restaurant> restaurants; // map {id : Restaurant}

    public ResourcesHandler() {
        this.restaurateurs = new HashMap<>();
        this.restaurants = new HashMap<>();
    }

    public void addRestaurant(Restaurant restaurant) throws NotFoundException {
        restaurants.put(restaurant.getId(), restaurant);
    }

    public Restaurant getRestaurant(String restaurantId) throws NotFoundException {
        Restaurant restaurant = restaurants.get(restaurantId);
        if (restaurant == null) throw new NotFoundException(); // the restaurant does not exist
        return restaurant;
    }

    public List<RestaurantResponse> getAllRestaurantsForOwner(String ownerId) {
        List<RestaurantResponse> ownerRestaurants = new ArrayList<>();
        for (Restaurant restaurant : restaurants.values()) {
            if (restaurant.getOwnerId().equals(ownerId)) {
                ownerRestaurants.add(new RestaurantResponse(restaurant));
            }
        }
        return ownerRestaurants;
    }

}
