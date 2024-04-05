package ca.ulaval.glo2003.infrastructure;

import ca.ulaval.glo2003.domain.repositories.ReservationRepository;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class InMemoryReservationRepository implements ReservationRepository {
    private List<Reservation> reservations;

    public InMemoryReservationRepository() {
        this.reservations = new ArrayList<>();
    }

    public void saveReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    @Override
    public void deleteReservation(String reservationId) throws NotFoundException {
        Optional<Reservation> reservationOptional = reservations.stream()
            .filter(reservation -> reservation.getId().equals(reservationId)).findFirst();

        if (reservationOptional.isPresent()) {
            reservations.remove(reservationOptional.get());
        } else {
            throw new NotFoundException("Reservation not found with ID: " + reservationId);
        }
    }

    @Override
    public void deleteReservationsWithRestaurantId(String restaurantId) {
        reservations.removeIf(reservation -> reservation.getRestaurantId().equals(restaurantId));
    }


    public Reservation findReservationById(String reservationId) throws NotFoundException {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(reservationId)) {
                return reservation;
            }
        }
        throw new NotFoundException("Reservation not found with ID: " + reservationId);
    }
}
