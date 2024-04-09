package ca.ulaval.glo2003.infrastructure.reservation;

import ca.ulaval.glo2003.domain.repositories.ReservationRepository;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationMongo;
import ca.ulaval.glo2003.infrastructure.DatastoreProvider;
import ca.ulaval.glo2003.infrastructure.assemblers.ReservationAssembler;
import com.mongodb.client.result.DeleteResult;
import dev.morphia.Datastore;
import dev.morphia.DeleteOptions;
import dev.morphia.query.Query;
import dev.morphia.query.filters.Filters;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;


import java.util.List;


public class MongoReservationRepository implements ReservationRepository {
    private final Datastore datastore;

    @Inject
    public MongoReservationRepository(DatastoreProvider datastoreProvider) {
        this.datastore = datastoreProvider.provide();
    }

    @Override
    public void saveReservation(Reservation reservation) throws NotFoundException {
        datastore.save(ReservationAssembler.toReservationMongo(reservation));
    }

    @Override
    public void deleteReservation(String reservationId) throws NotFoundException {
        DeleteResult deleteResult = datastore.find(ReservationMongo.class)
            .filter(Filters.eq("id", reservationId))
            .delete(new DeleteOptions());

        if (deleteResult.getDeletedCount() == 0) {
            throw new NotFoundException("Reservation to delete not found with reservationId: " + reservationId);
        }
    }

    @Override
    public void deleteReservationsWithRestaurantId(String restaurantId) {
        datastore.find(ReservationMongo.class)
            .filter(Filters.eq("restaurantId", restaurantId))
            .delete(new DeleteOptions().multi(true));
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
    public List<Reservation> getAllRestaurantReservations(String restaurantId) {
        Query<ReservationMongo> query = datastore.find(ReservationMongo.class).filter(Filters.eq("restaurantId", restaurantId));
        List<ReservationMongo> reservationMongoList = query.iterator().toList();
        return ReservationAssembler.fromReservationMongoList(reservationMongoList);
    }

    @Override
    public List<Reservation> getReservationsByDate(String restaurantId, String date) {
        Query<ReservationMongo> query = datastore.find(ReservationMongo.class)
                .filter(Filters.eq("restaurantId", restaurantId))
                .filter(Filters.eq("date", date));
        List<ReservationMongo> reservationMongoList = query.iterator().toList();
        return ReservationAssembler.fromReservationMongoList(reservationMongoList);
    }
}
