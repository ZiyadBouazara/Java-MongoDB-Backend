package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.restaurant.ReservationConfiguration;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.utils.Hours;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RestaurantTest {
    private Restaurant restaurant;
    @Mock
    private ReservationConfiguration mockReservationConfiguration;
    @Mock
    private Reservation mockReservation;
    @Mock
    private Hours mockHours;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurant = new Restaurant("ownerId", "Restaurant Name", 100, mockHours, mockReservationConfiguration);
    }

    @Test
    public void whenAddingReservation_shouldIncrementReservationsMapSizeByOne() {
        restaurant.addReservation(mockReservation);
        assertEquals(1, restaurant.getReservationsById().size());
    }

    @Test
    public void whenAddingReservation_shouldAddCorrectReservationToMap() {
        String reservationId = UUID.randomUUID().toString();
        when(mockReservation.getId()).thenReturn(reservationId);

        restaurant.addReservation(mockReservation);
        Reservation actualReservation = restaurant.getReservationsById().get(reservationId);

        assertEquals(mockReservation, actualReservation);
    }
}
