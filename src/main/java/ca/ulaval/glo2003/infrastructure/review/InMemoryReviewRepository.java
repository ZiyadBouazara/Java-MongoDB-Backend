package ca.ulaval.glo2003.infrastructure.review;

import ca.ulaval.glo2003.domain.repositories.ReviewRepository;
import ca.ulaval.glo2003.domain.review.Review;

import java.util.ArrayList;
import java.util.List;

public class InMemoryReviewRepository implements ReviewRepository {
    private List<Review> reviews;

    public InMemoryReviewRepository() {
        this.reviews = new ArrayList<>();
    }

    @Override
    public void save(Review review) {
        reviews.add(review);
    }

    @Override
    public List<Review> getAllReviews(String restaurantId) {
        List<Review> matchingReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getRestaurantId().equals(restaurantId)) {
                matchingReviews.add(review);
            }
        }
        return matchingReviews;
    }
}
