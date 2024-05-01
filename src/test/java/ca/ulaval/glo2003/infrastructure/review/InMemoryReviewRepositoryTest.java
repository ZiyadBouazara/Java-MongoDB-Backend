package ca.ulaval.glo2003.infrastructure.review;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.review.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryReviewRepositoryTest extends ca.ulaval.glo2003.domain.repositories.ReviewRepositoryTest {

    private static final String RESTAURANT_ID = "restaurant1";
    private static final String DATE = "2024-01-01";
    private static final String CUSTOMER_NAME = "John Deer";
    private static final String CUSTOMER_EMAIL = "john.deer@gmail.com";
    private static final String CUSTOMER_PHONE_NUMBER = "1234567890";
    private static final Double RATING = 5.0;
    private static final String COMMENT = "Great";

    private InMemoryReviewRepository inMemoryReviewRepository;

    @Override
    protected InMemoryReviewRepository createRepository() {
        return new InMemoryReviewRepository();
    }

    @BeforeEach
    public void setUp() {
        super.setUp();
        inMemoryReviewRepository = createRepository();
    }

    @Test
    public void whenSavingReview_thenReviewIsSaved() {
        Review review = createReview();

        inMemoryReviewRepository.save(review);

        List<Review> savedReviews = inMemoryReviewRepository.getAllReviews(review.getRestaurantId());

        assertEquals(1, savedReviews.size());
        assertEquals(review, savedReviews.getFirst());
    }

    @Test
    public void givenSavedReviews_whenGettingAllReviewsByRestaurantId_thenReturnAllSavedReviews() {
        Review review1 = createReview();
        Review review2 = createReview();

        inMemoryReviewRepository.save(review1);
        inMemoryReviewRepository.save(review2);

        List<Review> savedReviews = inMemoryReviewRepository.getAllReviews(review1.getRestaurantId());

        assertEquals(2, savedReviews.size());
        assertTrue(savedReviews.contains(review1));
        assertTrue(savedReviews.contains(review2));
    }

    @Test
    public void givenNoSavedReviews_whenGettingAllReviewsByRestaurantId_thenReturnEmptyList() {
        List<Review> savedReviews = inMemoryReviewRepository.getAllReviews(RESTAURANT_ID);

        assertTrue(savedReviews.isEmpty());
    }

    private Review createReview() {
        return new Review(RESTAURANT_ID,
                DATE,
                new Customer(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE_NUMBER),
                RATING,
                COMMENT);
    }
}
