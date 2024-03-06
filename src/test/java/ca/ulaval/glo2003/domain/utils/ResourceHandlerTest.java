package ca.ulaval.glo2003.domain.utils;

import ca.ulaval.glo2003.models.FuzzySearchResponse;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.models.RestaurantResponse;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ResourceHandlerTest {
    private static final String RESTAURANT_ID = "1";
    private static final String OWNER_ID = "1";
    private static final String RESTAURANT_NAME = "Restaurant Name";
    private static final int EXPECTED_OWNER_RESTAURANTS_COUNT = 1;
    private static final String FUZZY_NAME = "Restaurant Name";
    private static final String RESERVATION_ID = "1";
    private ResourcesHandler resourcesHandler;
    @Mock
    private Restaurant restaurant;
    @Mock
    private Reservation reservation;

    @Mock
    private FuzzySearch fuzzySearch;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(restaurant.getId()).thenReturn(RESTAURANT_ID);
        when(restaurant.getOwnerId()).thenReturn(OWNER_ID);
        when(restaurant.getName()).thenReturn(RESTAURANT_NAME);
        when(reservation.getId()).thenReturn(RESERVATION_ID);
        when(reservation.getRestaurantId()).thenReturn(RESTAURANT_ID);
        when(restaurant.getHours()).thenReturn(Mockito.mock(Hours.class));
        when(fuzzySearch.getName()).thenReturn(FUZZY_NAME);
        when(fuzzySearch.getHours()).thenReturn(Mockito.mock(VisitTime.class));

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

        RestaurantResponse ownerRestaurant = ownerRestaurants.getFirst();

        assertEquals(restaurant.getName(), ownerRestaurant.getName());
        assertEquals(restaurant.getCapacity(), ownerRestaurant.getCapacity());
    }

    @Test
    void givenOwnerHasNoRestaurants_whenGetAllRestaurantsForOwner_shouldReturnEmptyList() {
        List<RestaurantResponse> ownerRestaurants = resourcesHandler.getAllRestaurantsForOwner(OWNER_ID);
        assertTrue(ownerRestaurants.isEmpty());
    }

    @Test
    void givenOwnerHasRestaurants_whenGetAllRestaurantsForSearch_shouldReturnOwnerRestaurants() {
        resourcesHandler.addRestaurant(restaurant);
        List<FuzzySearchResponse> searchedRestaurants = resourcesHandler.getAllRestaurantsForSearch(fuzzySearch);

        assertFalse(searchedRestaurants.isEmpty());
        FuzzySearchResponse searchedRestaurant = searchedRestaurants.getFirst();

        assertEquals(restaurant.getId(), searchedRestaurant.getId());
        assertEquals(restaurant.getName(), searchedRestaurant.getName());
        assertEquals(restaurant.getCapacity(), searchedRestaurant.getCapacity());
        assertEquals(restaurant.getHours(), searchedRestaurant.getHours());

        assertEquals(restaurant.getHours().getOpen(), searchedRestaurant.getHours().getOpen());
        assertEquals(restaurant.getHours().getClose(), searchedRestaurant.getHours().getClose());
    }

    @Test
    void givenOwnerHasNoRestaurants_whenGetAllRestaurantsForSearch_shouldReturnEmptyList() {
        List<FuzzySearchResponse> searchedRestaurants = resourcesHandler.getAllRestaurantsForSearch(fuzzySearch);
        assertTrue(searchedRestaurants.isEmpty());
    }

    @Test
    void givenMatchingRestaurantName_whenShouldMatchRestaurantName_shouldReturnTrue() {
        assertTrue(resourcesHandler.shouldMatchRestaurantName(fuzzySearch, restaurant));
    }

    @Test
    void givenUnmatchingRestaurantName_whenShouldMatchRestaurantName_shouldReturnFalse() {
        when(fuzzySearch.getName()).thenReturn("NonMatchingName");
        assertFalse(resourcesHandler.shouldMatchRestaurantName(fuzzySearch, restaurant));
    }

    @Test
    void givenGoodRestaurantHours_whenShouldMatchRestaurantHours_shouldReturnTrue() {
        assertTrue(resourcesHandler.shouldMatchRestaurantHours(fuzzySearch, restaurant));
    }

    @Test
    void givenFuzzySearchResponse_whenGetFuzzySearchResponseForRestaurant_shouldReturnFuzzySearchResponse() {
        FuzzySearchResponse fuzzySearchResponse = resourcesHandler.getFuzzySearchResponseForRestaurant(restaurant);
        assertEquals(restaurant.getId(), fuzzySearchResponse.getId());
        assertEquals(restaurant.getName(), fuzzySearchResponse.getName());
        assertEquals(restaurant.getCapacity(), fuzzySearchResponse.getCapacity());
        assertEquals(restaurant.getHours(), fuzzySearchResponse.getHours());
    }
    @Test
    void whenAddingReservation_shouldAddToReservationsMap() {
        resourcesHandler.addRestaurant(restaurant);
        resourcesHandler.addReservation(reservation);

        verify(restaurant, times(1)).addReservation(reservation);
    }

    @Test
    void givenExistingReservation_whenGetReservation_shouldReturnReservation() {
        Map<String, Reservation> reservationsById = new HashMap<>();
        reservationsById.put(RESERVATION_ID, reservation);
        when(restaurant.getReservationsById()).thenReturn(reservationsById);
        resourcesHandler.addRestaurant(restaurant);

        Reservation addedReservation = resourcesHandler.getReservation(RESERVATION_ID);

        assertEquals(reservation, addedReservation);
    }

    @Test
    void givenNonExistingReservation_whenGetReservation_shouldThrowNotFoundException() {
        assertThrows(NotFoundException.class, () -> resourcesHandler.getReservation("NonExistingReservation"));
    }
}

