package ca.ulaval.glo2003.infrastructure;

import ca.ulaval.glo2003.domain.repositories.RestaurantAndReservationRepository;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

import java.util.List;

public class MongoRestaurantAndReservationRepository implements RestaurantAndReservationRepository {
    @Override
    public void saveRestaurant(Restaurant restaurant) { }

    @Override
    public void saveReservation(Reservation reservation) { }

    @Override
    public List<Restaurant> findRestaurantsByOwnerId(String ownerId) {
        return null;
    }

    @Override
    public Restaurant findRestaurantByRestaurantId(String restaurantId) {
        return null;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {return null;}
}
