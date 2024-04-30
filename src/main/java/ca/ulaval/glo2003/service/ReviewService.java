package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.assemblers.ReviewResponseAssembler;
import ca.ulaval.glo2003.controllers.requests.ReviewRequest;
import ca.ulaval.glo2003.controllers.responses.ReviewResponse;
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
import ca.ulaval.glo2003.service.validators.SearchReviewValidator;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReviewService {
    private final ReviewResponseAssembler reviewResponseAssembler;
    private final CreateReviewValidator createReviewValidator;
    private final ReviewFactory reviewFactory;
    private final CustomerAssembler customerAssembler;
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final SearchReviewValidator searchValidator;


    @Inject
    public ReviewService(ReviewResponseAssembler reviewResponseAssembler,
                         CreateReviewValidator createReviewValidator,
                         ReviewFactory reviewFactory,
                         CustomerAssembler customerAssembler,
                         ReviewRepository reviewRepository,
                         RestaurantRepository restaurantRepository,
                         SearchReviewValidator searchValidator) {
        this.reviewResponseAssembler = reviewResponseAssembler;
        this.reviewFactory = reviewFactory;
        this.createReviewValidator = createReviewValidator;
        this.customerAssembler = customerAssembler;
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.searchValidator = searchValidator;
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

    public List<ReviewResponse> getSearchReviews(String restaurantId, Double rating, String date) throws InvalidParameterException {
        searchValidator.validate(rating, date);

        restaurantRepository.findRestaurantById(restaurantId);

        List<Review> reviews = reviewRepository.getAllReviews(restaurantId);
        List<ReviewResponse> searchedReviews = new ArrayList<>();

        for (Review review : reviews) {
            if ((rating == null || correspondingRating(review.getRating(), rating)) &&
                (date == null || correspondingDate(review.getPostedDate(), date)) &&
                correspondingRestaurant(review.getRestaurantId(), restaurantId)) {
                searchedReviews.add(reviewResponseAssembler.toDTO(review));
            }
        }
        return searchedReviews;
    }


    public boolean correspondingRating(double reviewRating, double targetRating) {
        return reviewRating == targetRating;
    }

    public boolean correspondingDate(LocalDateTime reviewDate, String targetDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedReviewDate = reviewDate.format(formatter);
        return formattedReviewDate.equals(targetDate);
    }

    public boolean correspondingRestaurant(String reviewRestaurantId, String targetRestaurantId) {
        return reviewRestaurantId.equals(targetRestaurantId);
    }
}
