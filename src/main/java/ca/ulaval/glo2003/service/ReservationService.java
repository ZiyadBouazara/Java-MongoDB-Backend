package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.models.CustomerDTO;
import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.customer.CustomerAssembler;
import ca.ulaval.glo2003.domain.repositories.RestaurantAndReservationRepository;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationFactory;
import jakarta.inject.Inject;


public class ReservationService {
    private final RestaurantAndReservationRepository restaurantAndReservationRepository;
    private final ReservationFactory reservationFactory;
    private final CustomerAssembler customerAssembler;
    @Inject
    public ReservationService(RestaurantAndReservationRepository restaurantAndReservationRepository,
                              ReservationFactory reservationFactory,
                              CustomerAssembler customerAssembler) {
        this.restaurantAndReservationRepository = restaurantAndReservationRepository;
        this.reservationFactory = reservationFactory;
        this.customerAssembler = customerAssembler;
    }
    public String createReservation(
            String restaurantId,
            String date,
            String startTime,
            Integer groupSize,
            CustomerDTO customerDTO) {

        Customer customer = customerAssembler.fromDTO(customerDTO);
        Reservation reservation = reservationFactory.buildReservation(restaurantId, date, startTime, groupSize, customer);
        restaurantAndReservationRepository.saveReservation(reservation);
        return reservation.getId();
    }
}
