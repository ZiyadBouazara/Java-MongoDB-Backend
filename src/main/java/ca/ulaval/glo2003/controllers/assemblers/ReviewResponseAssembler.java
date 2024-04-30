package ca.ulaval.glo2003.controllers.assemblers;

import ca.ulaval.glo2003.controllers.responses.ReviewResponse;
import ca.ulaval.glo2003.domain.review.Review;

import java.time.LocalDateTime;

public class ReviewResponseAssembler {

    public ReviewResponse toDTO(Review review){
        return new ReviewResponse(review.getRestaurantId(),
                LocalDateTime.now().toString(),
                review.getRating());
    }

    public Review fromDTO(ReviewResponse reviewResponse){
        return new Review(
                reviewResponse.getRestaurantId(),
                reviewResponse.getRating(),
                LocalDateTime.now()
        );
    }
}
