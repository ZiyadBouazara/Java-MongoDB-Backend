package ca.ulaval.glo2003.infrastructure.assemblers;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationMongo;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class ReservationAssemblerTest {
    private static final String RESERVATION_ID = "SAMPLE_RESERVATION_ID";
    private static final String RESTAURANT_ID = "SAMPLE_RESTAURANT_ID";
    private static final String DATE = "2024-04-09";
    private static final String START_TIME = "18:00";
    private static final int GROUP_SIZE = 4;
    private static final String CUSTOMER_NAME = "John Doe";
    private static final String CUSTOMER_EMAIL = "john.doe@example.com";
    private static final String CUSTOMER_PHONE = "555-555-5555";

    @Test
    public void givenReservation_whenToReservationMongo_shouldMapAllFields() {
        Reservation reservation = createReservation();

        ReservationMongo reservationMongo = ReservationAssembler.toReservationMongo(reservation);

        assertThat(fieldsMatch(reservationMongo, reservation)).isTrue();
    }

    @Test
    public void givenReservationMongo_whenFromReservationMongo_shouldMapAllFields() {
        ReservationMongo reservationMongo = createReservationMongo();

        Reservation reservation = ReservationAssembler.fromReservationMongo(reservationMongo);

        assertThat(fieldsMatch(reservationMongo, reservation)).isTrue();
    }

    @Test
    public void givenReservationMongoList_whenFromReservationMongoList_shouldMapAllReservations() {
        List<ReservationMongo> reservationMongoList = List.of(createReservationMongo(), createReservationMongo());

        List<Reservation> reservations = ReservationAssembler.fromReservationMongoList(reservationMongoList);

        Assertions.assertThat(reservations)
            .hasSize(2)
            .allSatisfy(reservation -> assertThat(fieldsMatch(reservationMongoList.get(0), reservation)).isTrue());
    }

    private boolean fieldsMatch(ReservationMongo reservationMongo, Reservation reservation) {
        return reservationMongo.getId().equals(reservation.getId()) &&
            reservationMongo.getRestaurantId().equals(reservation.getRestaurantId()) &&
            reservationMongo.getDate().equals(reservation.getDate()) &&
            reservationMongo.getStartTime().equals(reservation.getStartTime()) &&
            reservationMongo.getGroupSize() == reservation.getGroupSize() &&
            reservationMongo.getCustomerName().equals(reservation.getCustomer().getName()) &&
            reservationMongo.getCustomerEmail().equals(reservation.getCustomer().getEmail()) &&
            reservationMongo.getCustomerPhone().equals(reservation.getCustomer().getPhoneNumber());
    }

    private Reservation createReservation() {
        return new Reservation(RESERVATION_ID, RESTAURANT_ID, DATE, START_TIME, GROUP_SIZE,
            new Customer(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE));
    }

    private ReservationMongo createReservationMongo() {
        return new ReservationMongo(RESERVATION_ID, RESTAURANT_ID, DATE, START_TIME, GROUP_SIZE, CUSTOMER_NAME, CUSTOMER_EMAIL,
            CUSTOMER_PHONE);
    }
}
