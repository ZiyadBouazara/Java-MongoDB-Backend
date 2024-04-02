package ca.ulaval.glo2003.domain.restaurant;

import ca.ulaval.glo2003.service.assembler.HoursAssembler;
import ca.ulaval.glo2003.service.dtos.HoursDTO;
import ca.ulaval.glo2003.service.dtos.ReservationConfigurationDTO;
import ca.ulaval.glo2003.domain.utils.Hours;

public class RestaurantFactory {
    private final HoursAssembler hoursAssembler = new HoursAssembler();

    public Restaurant createRestaurant(String ownerId,
                                       String name,
                                       Integer capacity,
                                       HoursDTO hoursDTO,
                                       ReservationConfigurationDTO reservationDuration) {

        if (hasReservationConfiguration(reservationDuration)) {
            return createRestaurantWithReservationConfiguration(
                ownerId,
                name,
                capacity,
                hoursAssembler.fromDTO(hoursDTO),
                reservationDuration);
        } else {
            return createRestaurantWithoutReservationConfiguration(
                ownerId,
                name,
                capacity,
                hoursAssembler.fromDTO(hoursDTO)
            );
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

