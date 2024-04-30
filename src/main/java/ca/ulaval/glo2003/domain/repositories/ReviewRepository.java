package ca.ulaval.glo2003.domain.repositories;

import ca.ulaval.glo2003.domain.review.Review;

import java.util.List;

public interface ReviewRepository {
    void save(Review review);
    List<Review> getAllReviews(String restaurantId);
}
