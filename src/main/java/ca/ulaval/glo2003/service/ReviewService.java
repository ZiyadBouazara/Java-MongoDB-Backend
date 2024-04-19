package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.requests.ReviewRequest;
import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.review.Review;
import ca.ulaval.glo2003.domain.review.ReviewFactory;
import ca.ulaval.glo2003.service.assembler.CustomerAssembler;
import ca.ulaval.glo2003.service.validators.CreateReviewValidator;
import jakarta.inject.Inject;

public class ReviewService {
    CreateReviewValidator createReviewValidator;
    ReviewFactory reviewFactory;
    CustomerAssembler customerAssembler;

    @Inject
    public ReviewService(CreateReviewValidator createReviewValidator,
                         ReviewFactory reviewFactory,
                         CustomerAssembler customerAssembler) {

        this.reviewFactory = reviewFactory;
        this.createReviewValidator = createReviewValidator;
        this.customerAssembler = customerAssembler;
    }

    public String createReview(String restaurantId, ReviewRequest reviewRequest) {
        createReviewValidator.validateReviewRequest(reviewRequest);

        Customer customer = customerAssembler.fromDTO(reviewRequest.customer());
        Review review = reviewFactory.createReview(restaurantId, reviewRequest.date(), reviewRequest.rating(), reviewRequest.comment(), customer);
        //TODO: call to the database to save the review
        return review.getId();
    }

}
