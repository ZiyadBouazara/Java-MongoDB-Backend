package ca.ulaval.glo2003.domain.reservation;

import ca.ulaval.glo2003.domain.customer.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

public class ReservationFactoryTest {
    private final static String RESTAURANT_ID = "restaurant1";
    private final static String DATE = "2024-04-10";
    private final static String START_TIME = "18:30";
    private final static int GROUP_SIZE = 4;
    Customer customer;
    @Test
    public void whenCreateReservation_shouldCreateReservationWithCorrectValues() {
        customer = mock(Customer.class);

        Reservation reservation = new ReservationFactory().createReservation(RESTAURANT_ID, DATE, START_TIME, GROUP_SIZE, customer);

        Assertions.assertThat(reservation.getRestaurantId()).isEqualTo(RESTAURANT_ID);
        Assertions.assertThat(reservation.getDate()).isEqualTo(DATE);
        Assertions.assertThat(reservation.getStartTime()).isEqualTo(START_TIME);
        Assertions.assertThat(reservation.getGroupSize()).isEqualTo(GROUP_SIZE);
        Assertions.assertThat(reservation.getCustomer()).isEqualTo(customer);
    }
}
