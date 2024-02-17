package ca.ulaval.glo2003.restaurant.domainTest;

import jakarta.ws.rs.NotFoundException;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.utils.Hours;
import ca.ulaval.glo2003.domain.utils.ResourcesHandler;
import ca.ulaval.glo2003.models.RestaurantResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceHandlerTest {

    private ResourcesHandler ressourceHandler;
    private Restaurant restaurantTest;

    @BeforeEach
    void setUp () {

        Hours hours = new Hours();
        hours.setOpen("10:00:00");
        hours.setClose("20:00:00");

        ressourceHandler = new ResourcesHandler();
        restaurantTest = new Restaurant("1", "Test Restaurant", 10, hours);
    }

    @Test
    void add_restaurantValid_ShouldAddToMap() {
        assertDoesNotThrow(() -> ressourceHandler.addRestaurant(restaurantTest));

        assertEquals(restaurantTest, ressourceHandler.getRestaurant(restaurantTest.getId()));
    }

    @Test
    void getRestaurant_Exisisting_ShouldReturnTheRestaurant() {
        ressourceHandler.addRestaurant(restaurantTest);

        Restaurant wantedRestaurant = ressourceHandler.getRestaurant(restaurantTest.getId());

        assertEquals(restaurantTest, wantedRestaurant);
    }

    @Test
    void getRestaurant_withNonExistingRestaurantId_shouldThrowNotFoundException() {
        assertThrows(NotFoundException.class, () -> ressourceHandler.getRestaurant("nonexistentId"));
    }

    @Test
    void getRestaurantList_ForOwnerId_souldReturnListOfOwnedRestaurant() {
        ressourceHandler.addRestaurant(restaurantTest);

        List<RestaurantResponse> allRestaurant = ressourceHandler.getAllRestaurantsForOwner(restaurantTest.getOwnerId());

        assertFalse(allRestaurant.isEmpty());
        assertEquals(allRestaurant.size(), 1);
        assertEquals(restaurantTest.getName(), allRestaurant.getFirst().name);
    }

    @Test
    void getRestaurantList_ForInvalidOwnerId_souldReturnEmptyListOfOwnedRestaurant() {
        List<RestaurantResponse> allRestaurant = ressourceHandler.getAllRestaurantsForOwner("3");

        assertTrue(allRestaurant.isEmpty());
    }

    //TODO : Test for the reservation in the ressource Handler

}
