package ca.ulaval.glo2003.infrastructure;

import ca.ulaval.glo2003.domain.repositories.RestaurantAndReservationRepository;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import jakarta.ws.rs.NotFoundException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRestaurantAndReservationRepository implements RestaurantAndReservationRepository {
    private Map<String, Restaurant> restaurants;
    public InMemoryRestaurantAndReservationRepository() {
        this.restaurants = new HashMap<>();
    }

    @Override
    public void saveReservation(Reservation reservation) {
        String restaurantId = reservation.getRestaurantId();
        Restaurant restaurant = restaurants.get(restaurantId);

        restaurant.addReservation(reservation);
    }
    @Override
    public void saveRestaurant(Restaurant restaurant) throws NotFoundException {
        restaurants.put(restaurant.getId(), restaurant);
    }


//    public Restaurant getRestaurant(String restaurantId) throws NotFoundException {
//        Restaurant restaurant = restaurants.get(restaurantId);
//        if (restaurant == null) throw new NotFoundException();
//        return restaurant;
//    }
//
//    public List<RestaurantResponse> getAllRestaurantsForOwner(String ownerId) {
//        List<RestaurantResponse> ownerRestaurants = new ArrayList<>();
//        for (Restaurant restaurant : restaurants.values()) {
//            if (restaurant.getOwnerId().equals(ownerId)) {
//                ownerRestaurants.add(new RestaurantResponse(restaurant));
//            }
//        }
//        return ownerRestaurants;
//    }
}
