package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.requests.ReviewRequest;
import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.repositories.RestaurantRepository;
import ca.ulaval.glo2003.domain.repositories.ReviewRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.review.Review;
import ca.ulaval.glo2003.domain.review.ReviewFactory;
import ca.ulaval.glo2003.service.assembler.CustomerAssembler;
import ca.ulaval.glo2003.service.validators.CreateReviewValidator;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

public class ReviewService {
    CreateReviewValidator createReviewValidator;
    ReviewFactory reviewFactory;
    CustomerAssembler customerAssembler;
    ReviewRepository reviewRepository;
    RestaurantRepository restaurantRepository;

    @Inject
    public ReviewService(CreateReviewValidator createReviewValidator,
                         ReviewFactory reviewFactory,
                         CustomerAssembler customerAssembler,
                         ReviewRepository reviewRepository,
                         RestaurantRepository restaurantRepository) {

        this.reviewFactory = reviewFactory;
        this.createReviewValidator = createReviewValidator;
        this.customerAssembler = customerAssembler;
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public String createReview(String restaurantId, ReviewRequest reviewRequest)
            throws InvalidParameterException, MissingParameterException {

        createReviewValidator.validateReviewRequest(reviewRequest);
        validateRestaurantId(restaurantId);

        Customer customer = customerAssembler.fromDTO(reviewRequest.customer());
        Review review = reviewFactory.createReview(
                restaurantId, reviewRequest.date(), reviewRequest.rating(), reviewRequest.comment(), customer);
        reviewRepository.save(review);
        return review.getId();
    }

    private void validateRestaurantId(String restaurantId) throws NotFoundException {
        Restaurant optionalRestaurant = restaurantRepository.findRestaurantById(restaurantId);
    }

}
