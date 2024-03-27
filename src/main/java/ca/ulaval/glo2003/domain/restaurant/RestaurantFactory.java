package ca.ulaval.glo2003.domain.restaurant;

import ca.ulaval.glo2003.service.dtos.ReservationConfigurationDTO;
import ca.ulaval.glo2003.domain.hours.Hours;

public class RestaurantFactory {

    public Restaurant createRestaurant(String ownerId,
                                       String name,
                                       Integer capacity,
                                       Hours hours,
                                       ReservationConfigurationDTO reservations) {

        if (hasReservationConfiguration(reservations)) {
            return createRestaurantWithReservationConfiguration(
                ownerId,
                name,
                capacity,
                hours,
                reservations);
        } else {
            return createRestaurantWithoutReservationConfiguration(ownerId,
                name,
                capacity,
                hours);
        }
    }

    private boolean hasReservationConfiguration(ReservationConfigurationDTO reservations) {
        return reservations != null;
    }

    private Restaurant createRestaurantWithReservationConfiguration(
        String ownerId, String name, Integer capacity, Hours hours, ReservationConfigurationDTO reservations) {
        ReservationConfiguration reservationConfiguration = new ReservationConfiguration(reservations.duration());

        return new Restaurant(
            ownerId,
            name,
            capacity,
            hours,
            reservationConfiguration);
    }

    private Restaurant createRestaurantWithoutReservationConfiguration(
        String ownerId, String name, Integer capacity, Hours hours) {
        return new Restaurant(
            ownerId,
            name,
            capacity,
            hours);
    }
}

