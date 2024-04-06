package ca.ulaval.glo2003.infrastructure;

import ca.ulaval.glo2003.domain.repositories.ReservationRepository;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationMongo;
import ca.ulaval.glo2003.infrastructure.assemblers.ReservationAssembler;
import dev.morphia.Datastore;
import dev.morphia.query.Query;
import dev.morphia.query.filters.Filters;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


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
    public List<Reservation> findReservationByRestaurant(String restaurantID) {
        Query<ReservationMongo> query = datastore.find(ReservationMongo.class)
                .filter(Filters.eq("restaurantId", restaurantID));

        Iterator<ReservationMongo> iterator = query.iterator();
        List<ReservationMongo> reservationMongo = new ArrayList<>();
        iterator.forEachRemaining(reservationMongo::add);

        return reservationMongo.stream()
                .map(ReservationAssembler::fromReservationMongo)
                .collect(Collectors.toList());
    }
}
