package ca.ulaval.glo2003.domain.utils;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.ReservationConfiguration;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AvailabilitiesTest {
    public static final String VALID_DATE = "2024-04-08";
    public static final String VALID_START_RESERVATION = "11:00:00";
    public static final String OWNER_ID = "1";
    public static final String RESTAURANT_NAME = "Restaurant Name";
    private static final int VALID_RESTAURANTS_CAPACITY = 10;
    private static final int VALID_RESTAURANTS_CONFIGURATION = 60;
    private static final int AVAILABILITIES = 30;
    private static final int BASE_AVAILABILITY = 0;
    Hours validHours = new Hours("11:00:00", "19:30:00");
    Hours invalidHours = new Hours("18:00:00", "19:00:00");
    Customer customerMocked = Mockito.mock(Customer.class);
    private List<Reservation> reservationList;
    private Restaurant invalidRestaurantMocked;
    private Restaurant validRestaurantMocked;
    private Reservation reservationMocked;
    private int restaurantCapacityWithActiveReservation;

    public void setUp() {
        reservationList = new ArrayList<>();
        invalidRestaurantMocked = new Restaurant(OWNER_ID, RESTAURANT_NAME, VALID_RESTAURANTS_CAPACITY,
                invalidHours, new ReservationConfiguration(VALID_RESTAURANTS_CONFIGURATION));
        validRestaurantMocked = new Restaurant(OWNER_ID, RESTAURANT_NAME, VALID_RESTAURANTS_CAPACITY,
                validHours, new ReservationConfiguration(VALID_RESTAURANTS_CONFIGURATION));
        reservationMocked = new Reservation(validRestaurantMocked.getId(),
                VALID_DATE, VALID_START_RESERVATION, 3, customerMocked);
    }

    public void setUpWithReservationInList() {
        reservationList = new ArrayList<>();
        invalidRestaurantMocked = new Restaurant(OWNER_ID, RESTAURANT_NAME, VALID_RESTAURANTS_CAPACITY,
                invalidHours, new ReservationConfiguration(VALID_RESTAURANTS_CONFIGURATION));
        validRestaurantMocked = new Restaurant(OWNER_ID, RESTAURANT_NAME, VALID_RESTAURANTS_CAPACITY,
                validHours, new ReservationConfiguration(VALID_RESTAURANTS_CONFIGURATION));
        reservationMocked = new Reservation(validRestaurantMocked.getId(),
                VALID_DATE, VALID_START_RESERVATION, 3, customerMocked);
        reservationList.add(reservationMocked);
        restaurantCapacityWithActiveReservation =
                validRestaurantMocked.getCapacity() - reservationMocked.getGroupSize();
    }

    @Test
    public void givenInvalidHours_WhenGettingTheAvailabilitiesList_ShouldReturnEmptyList() {
        setUp();
        Availabilities availabilitiesInstance = new Availabilities(VALID_DATE, BASE_AVAILABILITY);
        List<Availabilities> availabilities =
                availabilitiesInstance.getAvailabilitiesForRestaurant(invalidRestaurantMocked, reservationList);
        assertThat(availabilities).isEmpty();
    }

    @Test
    public void givenValidHours_WhenGettingTheAvailabilitiesList_ShouldReturnListFull() {
        setUp();
        Availabilities availabilitiesInstance = new Availabilities(VALID_DATE, BASE_AVAILABILITY);
        List<Availabilities> availabilities =
                availabilitiesInstance.getAvailabilitiesForRestaurant(validRestaurantMocked, reservationList);
        assertThat(availabilities).isNotEmpty();
    }

    @Test
    public void givenSevenHoursOfOpening_whenGettingAvailabilitiesListShouldReturn_SevenMultipliedByNumberOfAvailabilitiesInOneHour() {
        setUp();
        Availabilities availabilitiesInstance = new Availabilities(VALID_DATE, BASE_AVAILABILITY);
        List<Availabilities> availabilities =
                availabilitiesInstance.getAvailabilitiesForRestaurant(validRestaurantMocked, reservationList);
        assertEquals(AVAILABILITIES, availabilities.size());
    }

    @Test
    public void givenValidAvailabilitiesList_FirstElementShouldReturnParsedLocalDateTime() {
        setUp();
        Availabilities availabilitiesInstance = new Availabilities(VALID_DATE, BASE_AVAILABILITY);
        List<Availabilities> availabilities =
                availabilitiesInstance.getAvailabilitiesForRestaurant(validRestaurantMocked, reservationList);
        assertEquals("2024-04-08T11:00:00", availabilities.get(0).getDate());
    }

    @Test
    public void givenValidAvailabilitiesList_WithNoReservation_FirstElementShouldReturnValidRestaurantCapacity() {
        setUp();
        Availabilities availabilitiesInstance = new Availabilities(VALID_DATE, BASE_AVAILABILITY);
        List<Availabilities> availabilities =
                availabilitiesInstance.getAvailabilitiesForRestaurant(validRestaurantMocked, reservationList);
        assertEquals(validRestaurantMocked.getCapacity(), availabilities.get(0).getRemainingPlace());
    }

    @Test
    public void givenValidAvailabilitiesList_WithNoReservation_FirstElementShouldNotReturnValidRestaurantCapacity() {
        setUpWithReservationInList();
        Availabilities availabilitiesInstance = new Availabilities(VALID_DATE, BASE_AVAILABILITY);
        List<Availabilities> availabilities =
                availabilitiesInstance.getAvailabilitiesForRestaurant(validRestaurantMocked, reservationList);
        assertNotEquals(VALID_RESTAURANTS_CAPACITY, availabilities.get(0).getRemainingPlace());
    }

    @Test
    public void givenAvailabList_WithActiveReservation_FirstElemShouldReturn_ValidRestaurantCapacityMinusReservationGroupSize() {
        setUpWithReservationInList();
        Availabilities availabilitiesInstance = new Availabilities(VALID_DATE, BASE_AVAILABILITY);
        List<Availabilities> availabilities =
                availabilitiesInstance.getAvailabilitiesForRestaurant(validRestaurantMocked, reservationList);
        assertEquals(restaurantCapacityWithActiveReservation, availabilities.get(0).getRemainingPlace());
    }
}
