package ca.ulaval.glo2003.domain.repositories;

import ca.ulaval.glo2003.domain.reservation.Reservation;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

public interface ReservationRepository {
    void saveReservation(Reservation reservation);
    Reservation findReservationById(String reservationId) throws NotFoundException;
    List<Reservation> findReservationByRestaurant(String restaurantID) throws NotFoundException;

}
