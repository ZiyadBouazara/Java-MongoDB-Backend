package ca.ulaval.glo2003.infrastructure.reservation;

import ca.ulaval.glo2003.domain.repositories.ReservationRepository;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationMongo;
import ca.ulaval.glo2003.infrastructure.assemblers.ReservationAssembler;
import dev.morphia.Datastore;
import dev.morphia.query.Query;
import dev.morphia.query.filters.Filters;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;


public class MongoReservationRepository implements ReservationRepository {
    private final Datastore datastore;

    public MongoReservationRepository(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public void saveReservation(Reservation reservation) throws NotFoundException {
        datastore.save(ReservationAssembler.toReservationMongo(reservation));
    }

    @Override
    public Reservation findReservationById(String reservationId) throws NotFoundException {
        Query<ReservationMongo> query = datastore.find(ReservationMongo.class).filter(Filters.eq("id", reservationId));
        ReservationMongo reservationMongo = query.first();
        if (reservationMongo == null) {
            throw new NotFoundException("No restaurant found for restaurant with ID: " + reservationId);
        }
        return ReservationAssembler.fromReservationMongo(reservationMongo);
    }

    @Override
    public List<Reservation> getAllReservations(String restaurantId) throws NotFoundException {
        List<Reservation> reservations = new ArrayList<>();
        Query<ReservationMongo> query = datastore.find(ReservationMongo.class)
                .filter(Filters.eq("restaurantId", restaurantId));
        List<ReservationMongo> reservationsMongo = query.iterator().toList();
        for (ReservationMongo reservation : reservationsMongo) {
            reservations.add(ReservationAssembler.fromReservationMongo(reservation));
        }
        if (reservations.isEmpty()) {
            throw new NotFoundException("No reservations found for restaurant with ID: " + restaurantId);
        }
        return reservations;
    }
}
