package ca.ulaval.glo2003.domain.review;

import ca.ulaval.glo2003.domain.customer.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReviewFactoryTest {
    private static final String RESTAURANT_ID = "restaurant123";
    private static final String REVIEW_DATE = "2024-04-30";
    private static final Double REVIEW_RATING = 4.5;
    private static final String REVIEW_COMMENT = "Excellent service!";
    private static final String CUSTOMER_NAME = "John Doe";
    private static final String CUSTOMER_EMAIL = "john.deer@gmail.com";
    private static final String CUSTOMER_PHONE_NUMBER = "1234567890";
    private static Customer customer;
    private final ReviewFactory reviewFactory = new ReviewFactory();

    @BeforeEach
    void setUp() {
        customer = new Customer(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE_NUMBER);
    }

    @Test
    public void givenValidData_whenCreateReview_shouldReturnReviewWithCorrectRestaurantId() {
        Review review = reviewFactory.createReview(
            RESTAURANT_ID, REVIEW_DATE, REVIEW_RATING, REVIEW_COMMENT, customer);

        Assertions.assertThat(review.getRestaurantId()).isEqualTo(RESTAURANT_ID);
    }

    @Test
    public void givenValidData_whenCreateReview_shouldReturnReviewWithCorrectDate() {
        Review review = reviewFactory.createReview(
            RESTAURANT_ID, REVIEW_DATE, REVIEW_RATING, REVIEW_COMMENT, customer);

        Assertions.assertThat(review.getDate()).isEqualTo(REVIEW_DATE);
    }

    @Test
    public void givenValidData_whenCreateReview_shouldReturnReviewWithCorrectRating() {
        Review review = reviewFactory.createReview(
            RESTAURANT_ID, REVIEW_DATE, REVIEW_RATING, REVIEW_COMMENT, customer);

        Assertions.assertThat(review.getRating()).isEqualTo(REVIEW_RATING);
    }

    @Test
    public void givenValidData_whenCreateReview_shouldReturnReviewWithCorrectComment() {
        Review review = reviewFactory.createReview(
            RESTAURANT_ID, REVIEW_DATE, REVIEW_RATING, REVIEW_COMMENT, customer);

        Assertions.assertThat(review.getComment()).isEqualTo(REVIEW_COMMENT);
    }

    @Test
    public void givenValidData_whenCreateReview_shouldReturnReviewWithCorrectCustomer() {
        Review review = reviewFactory.createReview(
            RESTAURANT_ID, REVIEW_DATE, REVIEW_RATING, REVIEW_COMMENT, customer);

        Assertions.assertThat(review.getCustomer().getName()).isEqualTo(CUSTOMER_NAME);
    }
}
