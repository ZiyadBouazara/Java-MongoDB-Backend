package ca.ulaval.glo2003.controllers.assemblers;

import ca.ulaval.glo2003.controllers.responses.ReviewResponse;
import ca.ulaval.glo2003.domain.review.Review;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReviewResponseAssembler {

    public ReviewResponse toDTO(Review review) {
        return new ReviewResponse(
            review.getRestaurantId(),
            formatDate(review.getPostedDate()),
            review.getRating());
    }

    private String formatDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
