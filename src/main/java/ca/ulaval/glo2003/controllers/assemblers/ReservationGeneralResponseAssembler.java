package ca.ulaval.glo2003.controllers.assemblers;

import ca.ulaval.glo2003.controllers.responses.ReservationGeneralResponse;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.service.dtos.CustomerDTO;
import ca.ulaval.glo2003.service.dtos.TimeDTO;

public class ReservationGeneralResponseAssembler {

    //TODO: Make sure the number here is the same as the id of the reservation or not

    public ReservationGeneralResponse toDTO(Reservation reservation, Restaurant restaurant) {
        return new ReservationGeneralResponse(
                reservation.getId(),
                reservation.getDate(),
                createTimeDTO(reservation, restaurant),
                reservation.getGroupSize(),
                createCustomerDTO(reservation));
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
}
