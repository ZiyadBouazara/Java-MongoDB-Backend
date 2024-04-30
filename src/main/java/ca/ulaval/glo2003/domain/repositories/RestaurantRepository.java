package ca.ulaval.glo2003.domain.repositories;

import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.review.Review;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

public interface RestaurantRepository {
    void saveRestaurant(Restaurant restaurant);

    void deleteRestaurant(String ownerId, String restaurantId) throws NotFoundException;

    List<Restaurant> findRestaurantsByOwnerId(String ownerId);

    Restaurant findRestaurantById(String restaurantId) throws NotFoundException;

    List<Restaurant> getAllRestaurants();

    void updateReviews(Review review);
}
