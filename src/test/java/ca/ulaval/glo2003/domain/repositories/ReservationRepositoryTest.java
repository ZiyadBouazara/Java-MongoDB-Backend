package ca.ulaval.glo2003.domain.repositories;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.utils.Hours;
import jakarta.ws.rs.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public abstract class ReservationRepositoryTest {
    private static final String RESTAURANT_ID = "restaurant1";
    private static final String WRONG_RESTAURANT_ID = "restaurant1";
    private static final String RESERVATION_ID = "reservation1";
    private static final String CUSTOMER_NAME = "John Deer";
    private static final String CUSTOMER_EMAIL = "john.deer@gmail.com";
    private static final String CUSTOMER_PHONE_NUMBER = "1234567890";
    private static final String OWNER_ID = "owner";
    private static final String NAME = "name";
    private static final int CAPACITY = 5;
    private static final Hours HOURS = new Hours("11:00:00", "19:30:00");
    private static final String DATE = "2024-03-31";
    private static final String WRONG_DATE = "2024-03-1";
    private static final String START_TIME = "20:46:00";
    private static final int GROUP_SIZE = 5;
    private ReservationRepository reservationRepository;
    private Restaurant restaurant;

    protected abstract ReservationRepository createRepository();

    @BeforeEach
    public void setUp() {
        reservationRepository = createRepository();
        restaurant = new Restaurant(OWNER_ID, NAME, CAPACITY, HOURS);
    }

    @Test
    public void givenSavedReservations_whenDeletingWithRestaurantId_shouldDeleteAllForRestaurant() {
        Reservation reservation1 = createAndSaveReservation();
        Reservation reservation2 = createAndSaveReservation();

        reservationRepository.deleteReservationsWithRestaurantId(RESTAURANT_ID);
        var foundReservations = reservationRepository.getAllRestaurantReservations(RESTAURANT_ID);

        Assertions.assertThat(foundReservations).isEmpty();
    }

    @Test
    public void givenSavedReservation_whenDeleting_shouldDeleteSavedReservation() throws NotFoundException {
        Reservation reservation = createAndSaveReservation();

        reservationRepository.deleteReservation(reservation.getId());

        Assertions.assertThatThrownBy(() -> reservationRepository.findReservationById(reservation.getId()))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void whenDeletingNonExistentReservation_shouldThrowNotFoundException() throws NotFoundException {
        Assertions.assertThatThrownBy(
                () -> reservationRepository.deleteReservation(RESERVATION_ID))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void givenNoSavedReservations_whenFindingReservationById_shouldThrowNotFoundException() throws NotFoundException {
        Assertions.assertThatThrownBy(
                () -> reservationRepository.findReservationById(RESERVATION_ID))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void givenSavedReservation_whenFindingReservationById_shouldReturnSavedReservation() {
        Reservation reservation = createAndSaveReservation();

        Reservation foundReservation = reservationRepository.findReservationById(reservation.getId());

        Assertions.assertThat(foundReservation.getId()).isEqualTo(reservation.getId());
    }

    @Test
    public void givenSavedReservations_whenGettingAllRestaurantReservations_shouldReturnSavedReservation() {
        Reservation reservation1 = createAndSaveReservation();
        Reservation reservation2 = createAndSaveReservation();

        List<Reservation> foundReservations = reservationRepository.getAllRestaurantReservations(restaurant.getId());

        Assertions.assertThat(foundReservations)
            .extracting(Reservation::getId)
            .containsExactlyInAnyOrder(reservation1.getId(), reservation2.getId());
    }

    @Test
    public void givenNoSavedReservationsForRestaurant_whenGettingAllRestaurantReservations_shouldReturnEmptyList() {
        List<Reservation> foundReservations = reservationRepository.getAllRestaurantReservations(restaurant.getId());
        Assertions.assertThat(foundReservations).isEmpty();
    }

    @Test
    public void givenSavedReservations_whenGettingReservationsByDate_shouldReturnReservationsForDate() {
        Reservation reservation1 = createAndSaveReservation();
        Reservation reservation2 = createAndSaveReservation();
        List<Reservation> foundReservations = reservationRepository.getReservationsByDate(restaurant.getId(), DATE);

        Assertions.assertThat(foundReservations)
            .extracting(Reservation::getId)
            .containsExactlyInAnyOrder(reservation1.getId(), reservation2.getId());
    }

    @Test
    public void givenSavedReservations_whenGettingReservationsByDate_WithWrongDate_shouldReturnEmptyList() {
        Reservation reservation1 = createAndSaveReservation();
        Reservation reservation2 = createAndSaveReservation();
        List<Reservation> foundReservations = reservationRepository.getReservationsByDate(restaurant.getId(), WRONG_DATE);

        Assertions.assertThat(foundReservations).isEmpty();
    }

    @Test
    public void givenSavedReservations_whenGettingReservationsByDate_WithWrongRestaurantId_shouldReturnEmptyList() {
        Reservation reservation1 = createAndSaveReservation();
        Reservation reservation2 = createAndSaveReservation();
        List<Reservation> foundReservations = reservationRepository.getReservationsByDate(WRONG_RESTAURANT_ID, DATE);

        Assertions.assertThat(foundReservations).isEmpty();
    }

    @Test
    public void givenNoSavedReservationsForRestaurant_whenGettingReservationsByDate_shouldReturnEmptyList() {
        List<Reservation> foundReservations = reservationRepository.getReservationsByDate(restaurant.getId(), DATE);
        Assertions.assertThat(foundReservations).isEmpty();
    }

    private Reservation createAndSaveReservation() {
        Customer customer = new Customer(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE_NUMBER);
        Reservation reservation = new Reservation(restaurant.getId(), DATE, START_TIME, GROUP_SIZE, customer);
        reservationRepository.saveReservation(reservation);
        return reservation;
    }
}
