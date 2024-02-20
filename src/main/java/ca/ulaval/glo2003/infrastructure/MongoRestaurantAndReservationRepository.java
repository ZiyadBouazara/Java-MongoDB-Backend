package ca.ulaval.glo2003.infrastructure;

import ca.ulaval.glo2003.domain.repositories.RestaurantAndReservationRepository;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public class MongoRestaurantAndReservationRepository implements RestaurantAndReservationRepository {
    @Override
    public void saveRestaurant(Restaurant restaurant) {}

    @Override
    public void saveReservation(Reservation reservation) {}
}
