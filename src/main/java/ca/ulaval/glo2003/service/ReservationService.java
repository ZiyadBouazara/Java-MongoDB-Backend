package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationRepository;
import ca.ulaval.glo2003.domain.utils.ResourcesHandler;
import ca.ulaval.glo2003.infrastructure.InMemoryReservationRepository;

public class ReservationService {
    private final ReservationRepository reservationRepository;
    public ReservationService(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }
    public String createReservation(String restaurantId, String date, String startTime, Integer groupSize, Customer customer) {
        Reservation reservation = new Reservation(restaurantId, date, startTime, groupSize, customer);
        reservationRepository.saveReservation(reservation);
        return reservation.getId();
    }

}
