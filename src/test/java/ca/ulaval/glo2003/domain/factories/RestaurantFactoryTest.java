package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.restaurant.ReservationConfiguration;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.models.RestaurantRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RestaurantFactoryTest {
    private static final String OWNER_ID = "1";
    private static final Integer DEFAULT_RESERVATION_DURATION = 60;
    private RestaurantFactory factory;
    @Mock
    private RestaurantRequest restaurantRequestWithConfiguration;
    @Mock
    private RestaurantRequest restaurantRequestWithoutConfiguration;
    @Mock
    private ReservationConfiguration mockConfiguration;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        factory = new RestaurantFactory();

        when(restaurantRequestWithConfiguration.getRestaurantConfiguration()).thenReturn(mockConfiguration);
        when(restaurantRequestWithoutConfiguration.getRestaurantConfiguration()).thenReturn(null);
    }

    @Test
    public void givenReservationConfiguration_whenBuildRestaurant_shouldReturnRestaurantWithReservationConfiguration() {
        Restaurant restaurant = factory.buildRestaurant(OWNER_ID, restaurantRequestWithConfiguration);

        assertEquals(OWNER_ID, restaurant.getOwnerId());
        assertEquals(mockConfiguration, restaurant.getRestaurantConfiguration());
    }

    @Test
    public void givenNoReservationConfiguration_whenBuildRestaurant_shouldReturnRestaurantWithDefaultReservationConfiguration() {
        Restaurant restaurant = factory.buildRestaurant(OWNER_ID, restaurantRequestWithoutConfiguration);

        assertEquals(OWNER_ID, restaurant.getOwnerId());
        Assertions.assertNotNull(restaurant.getRestaurantConfiguration());
        assertEquals(DEFAULT_RESERVATION_DURATION, restaurant.getRestaurantConfiguration().getDuration());
    }
}
