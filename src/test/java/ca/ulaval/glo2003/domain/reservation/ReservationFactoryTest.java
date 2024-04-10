package ca.ulaval.glo2003.domain.reservation;

import ca.ulaval.glo2003.domain.customer.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class ReservationFactoryTest {
    private final String RESTAURANT_ID = "restaurant1";
    private final String DATE = "2024-04-10";
    private final String START_TIME = "18:30";
    private final int GROUP_SIZE = 4;
    Customer customer;

    @Test
    public void shouldCreateReservationWithCorrectRestaurantId() {
        customer = mock(Customer.class);

        Reservation reservation = new ReservationFactory().createReservation(RESTAURANT_ID, DATE, START_TIME, GROUP_SIZE, customer);

        Assertions.assertThat(reservation.getRestaurantId()).isEqualTo(RESTAURANT_ID);
    }

    @Test
    public void shouldCreateReservationWithCorrectDate() {
        customer = mock(Customer.class);

        Reservation reservation = new ReservationFactory().createReservation(RESTAURANT_ID, DATE, START_TIME, GROUP_SIZE, customer);

        Assertions.assertThat(reservation.getDate()).isEqualTo(DATE);
    }

    @Test
    public void shouldCreateReservationWithCorrectStartTime() {
        customer = mock(Customer.class);

        Reservation reservation = new ReservationFactory().createReservation(RESTAURANT_ID, DATE, START_TIME, GROUP_SIZE, customer);

        Assertions.assertThat(reservation.getStartTime()).isEqualTo(START_TIME);
    }

    @Test
    public void shouldCreateReservationWithCorrectGroupSize() {
        customer = mock(Customer.class);

        Reservation reservation = new ReservationFactory().createReservation(RESTAURANT_ID, DATE, START_TIME, GROUP_SIZE, customer);

        Assertions.assertThat(reservation.getGroupSize()).isEqualTo(GROUP_SIZE);
    }

    @Test
    public void shouldCreateReservationWithCorrectCustomer() {
        customer = mock(Customer.class);

        Reservation reservation = new ReservationFactory().createReservation(RESTAURANT_ID, DATE, START_TIME, GROUP_SIZE, customer);

        Assertions.assertThat(reservation.getCustomer()).isEqualTo(customer);
    }
}
