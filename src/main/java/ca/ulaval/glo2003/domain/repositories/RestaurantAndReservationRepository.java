package ca.ulaval.glo2003.domain.repositories;

import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

import java.util.List;

public interface RestaurantAndReservationRepository {
    void saveRestaurant(Restaurant restaurant);
    void saveReservation(Reservation reservation);
    List<Restaurant> findRestaurantsByOwnerId(String ownerId);
    Restaurant findRestaurantByRestaurantId(String restaurantId);
    Restaurant findRestaurantByReservationId(String reservationId);
    List<Restaurant> getAllRestaurants();
}
