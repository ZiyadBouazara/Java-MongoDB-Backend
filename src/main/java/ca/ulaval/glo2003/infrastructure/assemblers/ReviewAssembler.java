package ca.ulaval.glo2003.infrastructure.assemblers;

import ca.ulaval.glo2003.domain.review.Review;
import ca.ulaval.glo2003.domain.review.ReviewMongo;

public class ReviewAssembler {
    public static ReviewMongo toReviewMongo(Review review) {
        return new ReviewMongo(
                review.getId(),
                review.getRestaurantId(),
                review.getDate(),
                review.getRating(),
                review.getComment(),
                review.getCustomer().getName(),
                review.getCustomer().getEmail(),
                review.getCustomer().getPhoneNumber()
        );
    }
}
