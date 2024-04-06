package ca.ulaval.glo2003.infrastructure.Reservation;

import ca.ulaval.glo2003.domain.repositories.ReservationRepository;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;


public class InMemoryReservationRepository implements ReservationRepository {
    private List<Reservation> reservations;

    public InMemoryReservationRepository() {
        this.reservations = new ArrayList<>();
    }

    @Override
    public void saveReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    @Override
    public Reservation findReservationById(String reservationId) throws NotFoundException {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(reservationId)) {
                return reservation;
            }
        }
        throw new NotFoundException("Reservation not found with ID: " + reservationId);
    }

    @Override
    public List<Reservation> getAllReservations(String restaurantId) throws NotFoundException {
        List<Reservation> restaurantReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getRestaurantId().equals(restaurantId)) {
                restaurantReservations.add(reservation);
            }
        }
        return restaurantReservations;
    }


}
