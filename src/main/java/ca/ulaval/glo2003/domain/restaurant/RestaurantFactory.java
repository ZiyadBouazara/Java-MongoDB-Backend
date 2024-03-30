package ca.ulaval.glo2003.domain.restaurant;

import ca.ulaval.glo2003.controllers.requests.RestaurantRequest;
import ca.ulaval.glo2003.controllers.validators.CreateRestaurantValidator;
import ca.ulaval.glo2003.controllers.validators.GetRestaurantValidator;
import ca.ulaval.glo2003.controllers.validators.HeaderValidator;
import ca.ulaval.glo2003.controllers.validators.SearchRestaurantValidator;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.service.assembler.HoursAssembler;
import ca.ulaval.glo2003.service.dtos.HoursDTO;
import ca.ulaval.glo2003.service.dtos.ReservationConfigurationDTO;
import ca.ulaval.glo2003.domain.utils.Hours;

public class RestaurantFactory {
    private final HoursAssembler hoursAssembler = new HoursAssembler();
    public Restaurant createRestaurant(String ownerId,
                                       RestaurantRequest restaurantRequest) {

        if (hasReservationConfiguration(restaurantRequest.reservations())) {
            return createRestaurantWithReservationConfiguration(
                ownerId,
                restaurantRequest.name(),
                restaurantRequest.capacity(),
                hoursAssembler.fromDTO(restaurantRequest.hours()),
                restaurantRequest.reservations());
        } else {
            return createRestaurantWithoutReservationConfiguration(ownerId,
                restaurantRequest.name(),
                restaurantRequest.capacity(),
                hoursAssembler.fromDTO(restaurantRequest.hours())
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

