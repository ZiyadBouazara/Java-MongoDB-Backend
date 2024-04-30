package ca.ulaval.glo2003.infrastructure.assemblers;

import ca.ulaval.glo2003.domain.review.Review;
import ca.ulaval.glo2003.domain.review.ReviewMongo;

import java.util.ArrayList;
import java.util.List;

public class ReviewAssembler {
    public static ReviewMongo toReviewMongo(Review review) {
        return new ReviewMongo(
                review.getId(),
                review.getRestaurantId(),
                review.getDate(),
                review.getPostedDate(),
                review.getRating(),
                review.getComment(),
                review.getCustomer().getName(),
                review.getCustomer().getEmail(),
                review.getCustomer().getPhoneNumber()
        );
    }

    public static Review fromReviewMongo(ReviewMongo reviewMongo) {
        return new Review(
                reviewMongo.getRestaurantId(),
                reviewMongo.getRating()
        );
    }

    public static List<Review> fromReviewMongoList(List<ReviewMongo> reviewMongoList) {
        List<Review> reviews = new ArrayList<>();
        for (ReviewMongo reviewMongo : reviewMongoList) {
            reviews.add(fromReviewMongo(reviewMongo));
        }
        return reviews;
    }
}
