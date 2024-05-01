package ca.ulaval.glo2003.infrastructure.assemblers;

import ca.ulaval.glo2003.domain.restaurant.ReservationConfiguration;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantMongo;
import ca.ulaval.glo2003.domain.utils.Hours;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class RestaurantAssemblerTest {
    private static final String RESTAURANT_ID = "SAMPLE_RESTAURANT_ID";
    private static final String OWNER_ID = "SAMPLE_OWNER_ID";
    private static final String NAME = "Sample Restaurant";
    private static final int CAPACITY = 50;
    private static final String OPEN_HOUR = "10:00";
    private static final String CLOSE_HOUR = "20:00";
    private static final int DURATION = 60;
    private static final Double RATING = 4.5;
    private static final int REVIEW_COUNTER = 3;

    @Test
    public void givenRestaurant_whenToRestaurantMongo_shouldMapAllFields() {
        Restaurant restaurant = createRestaurant();

        RestaurantMongo restaurantMongo = RestaurantAssembler.toRestaurantMongo(restaurant);

        assertThat(fieldsMatch(restaurantMongo, restaurant)).isTrue();
    }

    @Test
    public void givenRestaurantMongo_whenFromRestaurantMongo_shouldMapAllFields() {
        RestaurantMongo restaurantMongo = createRestaurantMongo();

        Restaurant restaurant = RestaurantAssembler.fromRestaurantMongo(restaurantMongo);

        assertThat(fieldsMatch(restaurantMongo, restaurant)).isTrue();
    }

    @Test
    public void givenRestaurantMongoList_whenFromRestaurantMongoList_shouldMapAllRestaurants() {
        List<RestaurantMongo> restaurantMongoList = List.of(createRestaurantMongo(), createRestaurantMongo());

        List<Restaurant> restaurants = RestaurantAssembler.fromRestaurantMongoList(restaurantMongoList);

        Assertions.assertThat(restaurants)
            .hasSize(2)
            .allSatisfy(restaurant -> assertThat(fieldsMatch(restaurantMongoList.get(0), restaurant)).isTrue());
    }

    private boolean fieldsMatch(RestaurantMongo restaurantMongo, Restaurant restaurant) {
        return restaurantMongo.getId().equals(restaurant.getId()) &&
            restaurantMongo.getOwnerId().equals(restaurant.getOwnerId()) &&
            restaurantMongo.getName().equals(restaurant.getName()) &&
            Objects.equals(restaurantMongo.getCapacity(), restaurant.getCapacity()) &&
            restaurantMongo.getOpenHour().equals(restaurant.getHours().getOpen()) &&
            restaurantMongo.getCloseHour().equals(restaurant.getHours().getClose()) &&
            Objects.equals(restaurantMongo.getDuration(), restaurant.getRestaurantConfiguration().getDuration());
    }

    private Restaurant createRestaurant() {
        return new Restaurant(RESTAURANT_ID, OWNER_ID, NAME, CAPACITY, new Hours(OPEN_HOUR, CLOSE_HOUR),
            new ReservationConfiguration(DURATION), RATING, REVIEW_COUNTER);
    }

    private RestaurantMongo createRestaurantMongo() {
        return new RestaurantMongo(RESTAURANT_ID, OWNER_ID, NAME, CAPACITY, OPEN_HOUR, CLOSE_HOUR, DURATION, RATING, REVIEW_COUNTER);
    }
}
