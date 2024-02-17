package ca.ulaval.glo2003.restaurant.DomainTest;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.utils.Hours;
import org.junit.Before;
import org.junit.Test;


import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class RestaurantTest {

    private Restaurant restaurant;

    @Before
    public void setUp() {
        String ownerId = UUID.randomUUID().toString();
        String name = "Test Restaurant";
        Integer capacity = 50;
        Hours hours = new Hours();
        restaurant = new Restaurant(ownerId, name, capacity, hours);
    }

    @Test
    public void testRestaurantInitialization() {
        assertThat(restaurant.getId()).isNotNull();
        assertThat(restaurant.getOwnerId()).isNotNull();
        assertThat(restaurant.getName()).isEqualTo("Test Restaurant");
        assertThat(restaurant.getCapacity()).isEqualTo(50);
    }

    @Test
    public void testRestaurantIDisNotNull() {
        assertThat(restaurant.getId()).isNotNull();
    }

    @Test
    public void testRestaurantOwnerIDisNotNull() {
        assertThat(restaurant.getOwnerId()).isNotNull();
    }

    @Test
    public void testRestaurantName() {
        assertThat(restaurant.getName()).isEqualTo("Test Restaurant");
    }

    @Test
    public void testCapacityValid() {
        assertThat(restaurant.getCapacity()).isEqualTo(50);
    }

}
