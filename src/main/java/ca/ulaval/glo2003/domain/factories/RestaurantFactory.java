package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.controllers.models.RestaurantRequest;

public class RestaurantFactory {

    public Restaurant buildRestaurant(String ownerId, RestaurantRequest restaurantRequest) {
        if (hasReservationConfiguration(restaurantRequest)) {
            return buildRestaurantWithReservationConfiguration(ownerId, restaurantRequest);
        } else {
            return buildRestaurantWithoutReservationConfiguration(ownerId, restaurantRequest);
        }
    }

    private boolean hasReservationConfiguration(RestaurantRequest restaurantRequest) {
        return restaurantRequest.getRestaurantConfiguration() != null;
    }

    private Restaurant buildRestaurantWithReservationConfiguration(String ownerId, RestaurantRequest restaurantRequest) {
        return new Restaurant(
            ownerId,
            restaurantRequest.getName(),
            restaurantRequest.getCapacity(),
            restaurantRequest.getHours(),
            restaurantRequest.getRestaurantConfiguration());
    }

    private Restaurant buildRestaurantWithoutReservationConfiguration(String ownerId, RestaurantRequest restaurantRequest) {
        return new Restaurant(
            ownerId,
            restaurantRequest.getName(),
            restaurantRequest.getCapacity(),
            restaurantRequest.getHours());
    }
}

