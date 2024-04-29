package ca.ulaval.glo2003.domain.repositories;

import ca.ulaval.glo2003.domain.review.Review;

public interface ReviewRepository {
    void save(Review review);
}
