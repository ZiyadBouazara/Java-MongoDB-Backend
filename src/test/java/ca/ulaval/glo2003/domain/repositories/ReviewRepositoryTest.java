package ca.ulaval.glo2003.domain.repositories;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.review.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public abstract class ReviewRepositoryTest {

    protected abstract ReviewRepository createRepository();
    private ReviewRepository reviewRepository;
    private static final String RESTAURANT_ID = "restaurant1";
    private static final String NON_EXISTENT_RESTAURANT_ID = "nonExistentRestaurantId";
    private static final String DATE = "2024-01-01";
    private static final String CUSTOMER_NAME = "John Deer";
    private static final String CUSTOMER_EMAIL = "john.deer@gmail.com";
    private static final String CUSTOMER_PHONE_NUMBER = "1234567890";
    private static final Double RATING = 5.0;
    private static final String COMMENT = "Great";

    @BeforeEach
    public void setUp() {
        reviewRepository = createRepository();
    }

//    @Test
//    public void whenSavingReview_thenReviewIsSaved() {
//        Review review = createReview();
//
//        reviewRepository.save(review);
//
//        List<Review> savedReviews = reviewRepository.getAllReviews(review.getRestaurantId());
//
//        Assertions.assertThat(savedReviews).containsExactly(review);
//    }

//    @Test
//    public void givenSavedReviews_whenGettingAllReviewsByRestaurantId_thenReturnAllSavedReviews() {
//        Review review1 = createReview();
//        Review review2 = createReview();
//
//        reviewRepository.save(review1);
//        reviewRepository.save(review2);
//
//        List<Review> savedReviews = reviewRepository.getAllReviews(review1.getRestaurantId());
//
//        Assertions.assertThat(savedReviews)
//                .extracting(Review::getId)
//                .containsExactlyInAnyOrder(review1.getId(), review2.getId());
//    }

    @Test
    public void givenNoSavedReviews_whenGettingAllReviewsByRestaurantId_thenReturnEmptyList() {
        List<Review> savedReviews = reviewRepository.getAllReviews(NON_EXISTENT_RESTAURANT_ID);

        Assertions.assertThat(savedReviews).isEmpty();
    }

    private Review createReview() {
        return new Review(RESTAURANT_ID,
                DATE,
                new Customer(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE_NUMBER),
                RATING,
                COMMENT);
    }
}