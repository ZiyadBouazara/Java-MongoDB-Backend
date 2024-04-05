package ca.ulaval.glo2003.domain.repositories;

import ca.ulaval.glo2003.domain.reservation.Reservation;
import jakarta.ws.rs.NotFoundException;

public interface ReservationRepository {
    void saveReservation(Reservation reservation);

    void deleteReservation(String reservationId) throws NotFoundException;

    void deleteReservationsWithRestaurantId(String restaurantId);

    Reservation findReservationById(String reservationId) throws NotFoundException;

}
