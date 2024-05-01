package ca.ulaval.glo2003.domain.repositories;

import ca.ulaval.glo2003.domain.review.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public abstract class ReviewRepositoryTest {

    protected abstract ReviewRepository createRepository();
    private ReviewRepository reviewRepository;
    private static final String NON_EXISTENT_RESTAURANT_ID = "nonExistentRestaurantId";

    @BeforeEach
    public void setUp() {
        reviewRepository = createRepository();
    }

    @Test
    public void givenNoSavedReviews_whenGettingAllReviewsByRestaurantId_thenReturnEmptyList() {
        List<Review> savedReviews = reviewRepository.getAllReviews(NON_EXISTENT_RESTAURANT_ID);

        Assertions.assertThat(savedReviews).isEmpty();
    }
}
