package ca.ulaval.glo2003.domain.repositories;

import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public interface RestaurantAndReservationRepository {
    public void saveRestaurant(Restaurant restaurant);
    public void saveReservation(Reservation reservation);
}
