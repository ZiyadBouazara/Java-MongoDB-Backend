package ca.ulaval.glo2003.infrastructure;

import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantRepository;
import jakarta.ws.rs.NotFoundException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRestaurantRepository implements RestaurantRepository {
    private Map<String, Restaurant> restaurants;

    public InMemoryRestaurantRepository() {this.restaurants = new HashMap<>();}

    public void saveRestaurant(Restaurant restaurant) throws NotFoundException {
        restaurants.put(restaurant.getId(), restaurant);
    }
}
