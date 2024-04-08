package ca.ulaval.glo2003.infrastructure.restaurant;

import ca.ulaval.glo2003.domain.repositories.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryRestaurantRepository implements RestaurantRepository {
    private List<Restaurant> restaurants;

    public InMemoryRestaurantRepository() {
        this.restaurants = new ArrayList<>();
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    @Override
    public List<Restaurant> findRestaurantsByOwnerId(String ownerId) {
        return restaurants.stream()
            .filter(restaurant -> restaurant.getOwnerId().equals(ownerId))
            .collect(Collectors.toList());
    }

    @Override
    public Restaurant findRestaurantById(String restaurantId) throws NotFoundException {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getId().equals(restaurantId)) {
                return restaurant;
            }
        }
        throw new NotFoundException("Restaurant not found with ID: " + restaurantId);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurants;
    }
}
