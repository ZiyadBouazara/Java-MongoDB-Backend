package ca.ulaval.glo2003.domain.restaurant;

import ca.ulaval.glo2003.service.dtos.HoursDTO;
import ca.ulaval.glo2003.service.dtos.ReservationConfigurationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestaurantFactoryTest {
    private static final String OWNER_ID = "1";
    private static final String NAME = "name";
    private static final int CAPACITY = 5;
    private static final HoursDTO HOURS = new HoursDTO("11:00:00", "19:30:00");
    private static final Integer CUSTOM_RESERVATION_DURATION = 90;
    private static final Integer DEFAULT_RESERVATION_DURATION = 60;
    private ReservationConfigurationDTO customConfiguration;
    private RestaurantFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new RestaurantFactory();
        customConfiguration = new ReservationConfigurationDTO(CUSTOM_RESERVATION_DURATION);
    }

    @Test
    public void givenReservationConfiguration_whenCreateRestaurant_shouldSetOwner() {
        Restaurant restaurant = factory.createRestaurant(OWNER_ID, NAME, CAPACITY, HOURS, customConfiguration);

        assertEquals(OWNER_ID, restaurant.getOwnerId());
    }

    @Test
    public void givenReservationConfiguration_whenCreateRestaurant_shouldSetCustomDuration() {
        Restaurant restaurant = factory.createRestaurant(OWNER_ID, NAME, CAPACITY, HOURS, customConfiguration);

        assertEquals(CUSTOM_RESERVATION_DURATION, restaurant.getRestaurantConfiguration().getDuration());
    }

    @Test
    public void givenNoReservationConfiguration_whenCreateRestaurant_shouldSetOwner() {
        Restaurant restaurant = factory.createRestaurant(OWNER_ID, NAME, CAPACITY, HOURS, null);

        assertEquals(OWNER_ID, restaurant.getOwnerId());
    }

    @Test
    public void givenNoReservationConfiguration_whenCreateRestaurant_shouldCreateDefaultDuration() {
        Restaurant restaurant = factory.createRestaurant(OWNER_ID, NAME, CAPACITY, HOURS, null);

        Assertions.assertNotNull(restaurant.getRestaurantConfiguration());
    }

    @Test
    public void givenNoReservationConfiguration_whenCreateRestaurant_shouldSetDefaultDuration() {
        Restaurant restaurant = factory.createRestaurant(OWNER_ID, NAME, CAPACITY, HOURS, null);

        assertEquals(DEFAULT_RESERVATION_DURATION, restaurant.getRestaurantConfiguration().getDuration());
    }
}
