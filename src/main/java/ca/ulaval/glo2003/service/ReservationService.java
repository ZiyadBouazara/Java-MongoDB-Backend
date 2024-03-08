package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.customer.CustomerFactory;
import ca.ulaval.glo2003.domain.repositories.RestaurantAndReservationRepository;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationFactory;
import jakarta.inject.Inject;


public class ReservationService {
    private final RestaurantAndReservationRepository restaurantAndReservationRepository;
    private final ReservationFactory reservationFactory;
    private final CustomerFactory customerFactory;
    @Inject
    public ReservationService(RestaurantAndReservationRepository restaurantAndReservationRepository,
                              ReservationFactory reservationFactory,
                              CustomerFactory customerFactory) {
        this.restaurantAndReservationRepository = restaurantAndReservationRepository;
        this.reservationFactory = reservationFactory;
        this.customerFactory = customerFactory;
    }
    public String createReservation(
            String restaurantId,
            String date,
            String startTime,
            Integer groupSize,
            String customerName,
            String customerEmail,
            String customerPhoneNumber) {

        Customer customer = customerFactory.build(customerName, customerEmail, customerPhoneNumber);
        Reservation reservation = reservationFactory.build(restaurantId, date, startTime, groupSize, customer);
        restaurantAndReservationRepository.saveReservation(reservation);
        return reservation.getId();
    }

}
