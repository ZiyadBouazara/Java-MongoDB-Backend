package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.utils.Hours;
import ca.ulaval.glo2003.domain.restaurant.ReservationConfiguration;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

public class RestaurantFactory {

    public Restaurant buildRestaurant(String ownerId,
                                      String name,
                                      Integer capacity,
                                      Hours hours,
                                      ReservationConfiguration reservations) {
        if (hasReservationConfiguration(reservations)) {
            return buildRestaurantWithReservationConfiguration(
                    ownerId,
                    name,
                    capacity,
                    hours,
                    reservations);
        } else {
            return buildRestaurantWithoutReservationConfiguration(ownerId,
                    name,
                    capacity,
                    hours);
        }
    }

    private boolean hasReservationConfiguration(ReservationConfiguration reservations) {
        return reservations != null;
    }

    private Restaurant buildRestaurantWithReservationConfiguration(
            String ownerId, String name, Integer capacity, Hours hours, ReservationConfiguration reservations) {

        return new Restaurant(
            ownerId,
            name,
            capacity,
            hours,
            reservations);
    }

    private Restaurant buildRestaurantWithoutReservationConfiguration(
            String ownerId, String name, Integer capacity, Hours hours) {
        return new Restaurant(
            ownerId,
            name,
            capacity,
            hours);
    }
}

