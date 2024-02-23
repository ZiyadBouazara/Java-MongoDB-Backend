package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.repositories.RestaurantAndReservationRepository;
import ca.ulaval.glo2003.domain.reservation.Reservation;


public class ReservationService {
    private final RestaurantAndReservationRepository restaurantAndReservationRepository;
    public ReservationService(RestaurantAndReservationRepository restaurantAndReservationRepository) {
        this.restaurantAndReservationRepository = restaurantAndReservationRepository;
    }
    public String createReservation(
            String restaurantId,
            String date,
            String startTime,
            Integer groupSize,
            String customerName,
            String customerEmail,
            String customerPhoneNumber) {

        Customer customer = new Customer(customerName, customerEmail, customerPhoneNumber);
        Reservation reservation = new Reservation(restaurantId, date, startTime, groupSize, customer);
        restaurantAndReservationRepository.saveReservation(reservation);
        return reservation.getId();
    }

}
