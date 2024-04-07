package ca.ulaval.glo2003.infrastructure;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.infrastructure.reservation.InMemoryReservationRepository;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class InMemoryReservationRepositoryTest {

    private static final String RESTAURANT_ID = "2024-03-05";
    private static final String DATE = "2024/03/05";
    private static final String START_TIME = "12:00:00";
    private static final Integer GROUP_SIZE = 6;
    @Mock
    Customer customer;


    private InMemoryReservationRepository inMemoryReservationRepository;

    @BeforeEach
    public void setUpRepository() {
        inMemoryReservationRepository = new InMemoryReservationRepository();

    }

    @Test
    public void givenReservation_whenSaveReservation_thenReservationIsSaved() {
        Reservation reservation = new Reservation(RESTAURANT_ID, DATE, START_TIME, GROUP_SIZE, customer);
        inMemoryReservationRepository.saveReservation(reservation);

        Reservation savedReservation = inMemoryReservationRepository.findReservationById(reservation.getId());
        Assertions.assertEquals(reservation, savedReservation);
    }

    @Test
    public void givenReservationId_whenFindReservationById_thenReservationIsReturned() {
        Reservation reservation = new Reservation(RESTAURANT_ID, DATE, START_TIME, GROUP_SIZE, customer);
        inMemoryReservationRepository.saveReservation(reservation);

        Reservation savedReservation = inMemoryReservationRepository.findReservationById(reservation.getId());
        Assertions.assertEquals(reservation, savedReservation);
    }

    @Test
    public void givenReservationId_whenFindReservationById_thenReservationNotFoundExceptionIsThrown() {
        assertThrows(NotFoundException.class, () -> inMemoryReservationRepository.findReservationById("randomId"));
    }

    @Test
    public void givenRestaurantId_whenGetAllReservations_AndRestaurantIsNotValid_thenReservationNotFoundExceptionIsThrown() {
        assertThrows(NotFoundException.class, () -> inMemoryReservationRepository.getAllReservations("randomUnknownId"));
    }
}
