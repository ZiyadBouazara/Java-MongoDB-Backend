package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.requests.ReviewRequest;
import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.repositories.ReviewRepository;
import ca.ulaval.glo2003.domain.review.Review;
import ca.ulaval.glo2003.domain.review.ReviewFactory;
import ca.ulaval.glo2003.service.assembler.CustomerAssembler;
import ca.ulaval.glo2003.service.validators.CreateReviewValidator;
import jakarta.inject.Inject;

public class ReviewService {
    CreateReviewValidator createReviewValidator;
    ReviewFactory reviewFactory;
    CustomerAssembler customerAssembler;
    ReviewRepository reviewRepository;

    @Inject
    public ReviewService(CreateReviewValidator createReviewValidator,
                         ReviewFactory reviewFactory,
                         CustomerAssembler customerAssembler,
                         ReviewRepository reviewRepository) {

        this.reviewFactory = reviewFactory;
        this.createReviewValidator = createReviewValidator;
        this.customerAssembler = customerAssembler;
        this.reviewRepository = reviewRepository;
    }

    public String createReview(String restaurantId, ReviewRequest reviewRequest) {
        createReviewValidator.validateReviewRequest(reviewRequest);

        Customer customer = customerAssembler.fromDTO(reviewRequest.customer());
        Review review = reviewFactory.createReview(restaurantId, reviewRequest.date(), reviewRequest.rating(), reviewRequest.comment(), customer);
        reviewRepository.save(review);
        return review.getId();
    }

}
