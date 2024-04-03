package ca.ulaval.glo2003.controllers.assemblers;

import ca.ulaval.glo2003.controllers.responses.ReservationResponse;
import ca.ulaval.glo2003.controllers.responses.RestaurantResponseWithoutConfiguration;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.service.dtos.CustomerDTO;
import ca.ulaval.glo2003.service.dtos.HoursDTO;
import ca.ulaval.glo2003.service.dtos.TimeDTO;

public class ReservationResponseAssembler {

    public ReservationResponse toDTO(Reservation reservation, Restaurant restaurant) {

        return new ReservationResponse(
            reservation.getId(),
            reservation.getDate(),
            createTimeDTO(reservation, restaurant),
            reservation.getGroupSize(),
            createCustomerDTO(reservation),
            createRestaurantResponseWithoutConfiguration(restaurant));
    }

    private TimeDTO createTimeDTO(Reservation reservation, Restaurant restaurant) {
        return new TimeDTO(reservation.getStartTime(), restaurant.getRestaurantConfiguration().getDuration());
    }

    private CustomerDTO createCustomerDTO(Reservation reservation) {
        return new CustomerDTO(
            reservation.getCustomer().getName(),
            reservation.getCustomer().getEmail(),
            reservation.getCustomer().getPhoneNumber());
    }

    private RestaurantResponseWithoutConfiguration createRestaurantResponseWithoutConfiguration(Restaurant restaurant) {
        return new RestaurantResponseWithoutConfiguration(
            restaurant.getId(),
            restaurant.getOwnerId(),
            restaurant.getName(),
            restaurant.getCapacity(),
            new HoursDTO(restaurant.getHours().getOpen(), restaurant.getHours().getClose()));
    }
}

