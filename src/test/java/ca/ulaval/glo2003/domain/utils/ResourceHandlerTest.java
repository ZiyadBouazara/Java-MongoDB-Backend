package ca.ulaval.glo2003.domain.utils;

import jakarta.ws.rs.NotFoundException;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.models.RestaurantResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ResourceHandlerTest {
    private static final String RESTAURANT_ID = "1";
    private static final String OWNER_ID = "1";
    private static final String RESTAURANT_NAME = "Restaurant Name";
    private static final int EXPECTED_OWNER_RESTAURANTS_COUNT = 1;
    private ResourcesHandler resourcesHandler;
    @Mock
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(restaurant.getId()).thenReturn(RESTAURANT_ID);
        when(restaurant.getOwnerId()).thenReturn(OWNER_ID);
        when(restaurant.getName()).thenReturn(RESTAURANT_NAME);

        resourcesHandler = new ResourcesHandler();
    }

    @Test
    void whenAddingRestaurant_shouldAddToRestaurantsMap() {
        resourcesHandler.addRestaurant(restaurant);
        Restaurant addedRestaurant = resourcesHandler.getRestaurant(restaurant.getId());
        assertEquals(restaurant, addedRestaurant);
    }

    @Test
    void givenExistingRestaurant_whenGetRestaurant_shouldReturnTheRestaurant() {
        resourcesHandler.addRestaurant(restaurant);
        Restaurant actualRestaurant = resourcesHandler.getRestaurant(restaurant.getId());
        assertEquals(restaurant, actualRestaurant);
    }

    @Test
    void givenNonExistingRestaurant_whenGetRestaurant_shouldThrowNotFoundException() {
        assertThrows(NotFoundException.class, () -> resourcesHandler.getRestaurant("NonExistingRestaurant"));
    }

    @Test
    void givenOwnerHasOneRestaurant_whenGetAllRestaurantsForOwner_shouldReturnOneRestaurant() {
        resourcesHandler.addRestaurant(restaurant);
        List<RestaurantResponse> ownerRestaurants = resourcesHandler.getAllRestaurantsForOwner(OWNER_ID);
        assertEquals(EXPECTED_OWNER_RESTAURANTS_COUNT, ownerRestaurants.size());
    }

    @Test
    void givenOwnerHasOneRestaurant_whenGetAllRestaurantsForOwner_shouldReturnOwnerRestaurants() {
        resourcesHandler.addRestaurant(restaurant);

        List<RestaurantResponse> ownerRestaurants = resourcesHandler.getAllRestaurantsForOwner(OWNER_ID);

        assertEquals(1, ownerRestaurants.size());

        RestaurantResponse ownerRestaurant = ownerRestaurants.get(0);

        assertEquals(restaurant.getName(), ownerRestaurant.getName());
        assertEquals(restaurant.getCapacity(), ownerRestaurant.getCapacity());
    }

    @Test
    void givenOwnerHasNoRestaurants_whenGetAllRestaurantsForOwner_shouldReturnEmptyList() {
        List<RestaurantResponse> ownerRestaurants = resourcesHandler.getAllRestaurantsForOwner(OWNER_ID);
        assertTrue(ownerRestaurants.isEmpty());
    }

    //TODO : Add Tests for the reservation in the ResourceHandler
}
