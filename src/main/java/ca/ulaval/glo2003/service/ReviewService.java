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

        Customer customer = customerAssembler.fromDTO(reviewRequest.customer());
        double roundedRating = roundToTwoDecimals(reviewRequest.rating());
        Review review = reviewFactory.createReview(
                restaurantId, reviewRequest.date(), roundedRating, reviewRequest.comment(), customer);

        updateRestaurantReviews(review);
        reviewRepository.save(review);
        return review.getId();
    }

    private void updateRestaurantReviews(Review review) throws NotFoundException {
        Restaurant restaurant = restaurantRepository.findRestaurantById(review.getRestaurantId());

        int totalReviews = restaurant.getReviewCount() + 1;
        double currentRating = restaurant.getRating();

        double updatedRating = getUpdatedRating(review, restaurant, totalReviews, currentRating);
        updatedRating = roundToTwoDecimals(updatedRating);
        restaurant.setRating(updatedRating);
        restaurant.incrementReviewCount();

        restaurantRepository.updateReviews(restaurant);
    }

    private double getUpdatedRating(Review review, Restaurant restaurant, int totalReviews, double currentRating) {
        return ((currentRating * restaurant.getReviewCount()) + review.getRating()) / totalReviews;
    }

    private double roundToTwoDecimals(double rating) {
        return Math.round(rating * 100.0) / 100.0;
    }

}
