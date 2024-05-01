package ca.ulaval.glo2003.domain.repositories;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.review.Review;
import ca.ulaval.glo2003.domain.utils.Hours;
import jakarta.ws.rs.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public abstract class RestaurantRepositoryTest {
    private static final String OWNER_ID = "owner";
    private static final String ANOTHER_OWNER_ID = "another owner";
    private static final String NAME = "name";
    private static final int CAPACITY = 5;
    private static final Hours HOURS = new Hours("11:00:00", "19:30:00");
    private static final String NON_EXISTENT_RESTAURANT_ID = "id";
    private static final Double RATING1 = 4.0;
    private static final Double RATING2 = 5.0;
    private static final Double EXPECTED_RATING = 4.5;
    private static final String COMMENT = "Great";
    private static final String DATE = "2024-01-01";
    private static final String CUSTOMER_NAME = "John Deer";
    private static final String CUSTOMER_EMAIL = "john.deer@gmail.com";
    private static final String CUSTOMER_PHONE_NUMBER = "1234567890";
    private static final Customer VALID_CUSTOMER = new Customer(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE_NUMBER);

    protected abstract RestaurantRepository createRepository();

    private RestaurantRepository restaurantRepository;

    @BeforeEach
    public void setUp() {
        restaurantRepository = createRepository();
    }

    @Test
    public void givenNoSavedRestaurants_whenFindingRestaurantById_shouldThrowNotFoundException() {
        Assertions.assertThatThrownBy(
                () -> restaurantRepository.findRestaurantById(NON_EXISTENT_RESTAURANT_ID))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void whenFindingRestaurantByOwnerId_shouldReturnAllSavedOwnerRestaurants() {
        Restaurant restaurant1 = createAndSaveRestaurant();
        Restaurant restaurant2 = createAndSaveRestaurant();

        var foundRestaurants = restaurantRepository.findRestaurantsByOwnerId(OWNER_ID);

        Assertions.assertThat(foundRestaurants)
            .extracting(Restaurant::getId)
            .containsExactlyInAnyOrder(restaurant1.getId(), restaurant2.getId());
    }

    @Test
    public void givenNoSavedRestaurants_whenFindingRestaurantByOwnerId_shouldReturnEmptyList() {
        var foundRestaurants = restaurantRepository.findRestaurantsByOwnerId(OWNER_ID);

        Assertions.assertThat(foundRestaurants).isEmpty();
    }

    @Test
    public void whenGettingAll_shouldReturnAllSavedRestaurants() {
        Restaurant restaurant1 = createAndSaveRestaurant();
        Restaurant restaurant2 = createAndSaveRestaurant();
        var foundRestaurants = restaurantRepository.getAllRestaurants();

        Assertions.assertThat(foundRestaurants)
            .extracting(Restaurant::getId)
            .containsExactlyInAnyOrder(restaurant1.getId(), restaurant2.getId());
    }

    @Test
    public void givenNoSavedRestaurant_whenGettingAll_shouldReturnEmptyList() {
        var foundRestaurants = restaurantRepository.getAllRestaurants();

        Assertions.assertThat(foundRestaurants).isEmpty();
    }

    @Test
    public void givenSavedRestaurant_whenDeleting_shouldDeleteSavedRestaurant() throws NotFoundException {
        Restaurant restaurant = createAndSaveRestaurant();

        restaurantRepository.deleteRestaurant(restaurant.getOwnerId(), restaurant.getId());

        Assertions.assertThatThrownBy(
                () -> restaurantRepository.findRestaurantById(restaurant.getId()))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void whenDeletingNonExistentRestaurant_shouldThrowNotFoundException() throws NotFoundException {
        Assertions.assertThatThrownBy(
                () -> restaurantRepository.deleteRestaurant(OWNER_ID, NON_EXISTENT_RESTAURANT_ID))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void givenSavedRestaurant_whenNotOwnerAndDeleting_shouldThrowNotFoundException() throws NotFoundException {
        Restaurant restaurant = createAndSaveRestaurant();
        Assertions.assertThatThrownBy(
                () -> restaurantRepository.deleteRestaurant(ANOTHER_OWNER_ID, restaurant.getId()))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void givenRestaurant_whenAddingReview_shouldUpdateRating() {
        Restaurant restaurant = createAndSaveRestaurant();
        Review review = new Review(restaurant.getId(), DATE, VALID_CUSTOMER, RATING1, COMMENT);

        restaurantRepository.updateReviews(review);

        Restaurant updatedRestaurant = restaurantRepository.findRestaurantById(restaurant.getId());
        Assertions.assertThat(updatedRestaurant.getRating()).isEqualTo(RATING1);
    }

    @Test
    public void givenRestaurant_whenAddingReview_shouldIncrementReviewCount() {
        Restaurant restaurant = createAndSaveRestaurant();
        Review review = new Review(restaurant.getId(), DATE, VALID_CUSTOMER, RATING1, COMMENT);

        restaurantRepository.updateReviews(review);

        Restaurant updatedRestaurant = restaurantRepository.findRestaurantById(restaurant.getId());
        Assertions.assertThat(updatedRestaurant.getReviewCount()).isEqualTo(1);
    }

    @Test
    public void givenRestaurantWithExistingReviews_whenAddingReview_shouldUpdateRating() {
        Restaurant restaurant = createAndSaveRestaurantWithRating(RATING1);
        Review newReview = new Review(restaurant.getId(), DATE, VALID_CUSTOMER, RATING2, COMMENT);

        restaurantRepository.updateReviews(newReview);

        Restaurant updatedRestaurant = restaurantRepository.findRestaurantById(restaurant.getId());
        Assertions.assertThat(updatedRestaurant.getRating()).isEqualTo(EXPECTED_RATING);
    }

    @Test
    public void givenNonExistentRestaurant_whenUpdatingReviews_shouldThrowNotFoundException() {
        Review review = new Review(NON_EXISTENT_RESTAURANT_ID, DATE, VALID_CUSTOMER, RATING2, COMMENT);

        Assertions.assertThatThrownBy(() -> restaurantRepository.updateReviews(review))
            .isInstanceOf(NotFoundException.class);
    }

    private Restaurant createAndSaveRestaurant() {
        Restaurant restaurant = new Restaurant(OWNER_ID, NAME, CAPACITY, HOURS);
        restaurantRepository.saveRestaurant(restaurant);
        return restaurant;
    }

    private Restaurant createAndSaveRestaurantWithRating(Double rating) {
        Restaurant restaurant = new Restaurant(OWNER_ID, NAME, CAPACITY, HOURS);
        restaurant.setRating(rating);
        restaurant.incrementReviewCount();
        restaurantRepository.saveRestaurant(restaurant);

        return restaurant;
    }
}
