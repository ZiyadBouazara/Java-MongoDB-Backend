package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.api.fixture.ReviewRequestFixture;
import ca.ulaval.glo2003.controllers.assemblers.ReviewResponseAssembler;
import ca.ulaval.glo2003.controllers.requests.ReviewRequest;
import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.repositories.RestaurantRepository;
import ca.ulaval.glo2003.domain.repositories.ReviewRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.review.Review;
import ca.ulaval.glo2003.domain.review.ReviewFactory;
import ca.ulaval.glo2003.domain.utils.Hours;
import ca.ulaval.glo2003.service.assembler.CustomerAssembler;
import ca.ulaval.glo2003.service.validators.CreateReviewValidator;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class ReviewServiceTest {
    public static final String RESTAURANT_ID = "restaurant1";
    private static final String CUSTOMER_NAME = "John Deer";
    private static final String CUSTOMER_EMAIL = "john.deer@gmail.com";
    private static final String CUSTOMER_PHONE_NUMBER = "1234567890";
    private static final String DATE = "2024-03-31";
    private static final String OWNER_ID = "1";
    private static final String RESTO_NAME = "1";
    private static final Integer RESTO_CAPACITY = 10;
    private static final Hours RESTO_HOURS = new Hours("10:00:00", "21:00:00");
    public static final String FUTURE_DATE = LocalDateTime.of(
            9999, 12, 1, 10, 1)
            .toString();

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ReviewFactory reviewFactory;
    @Mock
    private CustomerAssembler customerAssembler;
    private CreateReviewValidator createReviewValidator;
    @Mock
    private ReviewResponseAssembler reviewResponseAssembler;
    private Restaurant restaurant;
    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    public void setUp() {
        createReviewValidator = new CreateReviewValidator();
        MockitoAnnotations.openMocks(this);
        reviewService = new ReviewService(
            reviewResponseAssembler,
            createReviewValidator,
            reviewFactory,
            customerAssembler,
            reviewRepository,
            restaurantRepository);
    }

    @Test
    public void givenValidReviewRequest_createReview_shouldCreateAndSaveReview() throws Exception {
        PrepareCreateMethod preparedCreateTest = getPreparedCreateTestMethod();

        String reviewId = reviewService.createReview(RESTAURANT_ID, preparedCreateTest.reviewRequest());

        Assertions.assertEquals(reviewId, preparedCreateTest.mockReview().getId());
        verify(reviewRepository).save(preparedCreateTest.mockReview());
    }


    @Test
    public void givenMissingDate_createReview_shouldThrowMissingParameterException() {
        ReviewRequest requestWithoutDate = new ReviewRequestFixture().createWithMissingDate();

        Assertions.assertThrows(MissingParameterException.class, () ->
                reviewService.createReview(RESTAURANT_ID, requestWithoutDate));
    }

    @Test
    public void givenMissingRating_createReview_shouldThrowMissingParameterException() {
        ReviewRequest requestWithoutRating = new ReviewRequestFixture().createWithMissingRating();

        Assertions.assertThrows(MissingParameterException.class, () ->
                reviewService.createReview(RESTAURANT_ID, requestWithoutRating));
    }

    @Test
    public void givenDateInFuture_createReview_shouldThrowInvalidParameterException() {
        restaurant = new Restaurant(OWNER_ID, RESTO_NAME, 1, RESTO_HOURS);
        ReviewRequest requestWithDateInFuture = new ReviewRequestFixture().withDate(FUTURE_DATE).create();

        Assertions.assertThrows(InvalidParameterException.class, () ->
                reviewService.createReview(RESTAURANT_ID, requestWithDateInFuture));
    }

    @Test
    public void givenValidReviewRequest_whenCreateReview_shouldCallDatabaseUpdateRestaurantMethod() throws Exception {
        ReviewRequest reviewRequest = new ReviewRequestFixture().create();
        Review review = mock(Review.class);
        when(reviewFactory.createReview(any(), any(), any(), any(), any())).thenReturn(review);

        reviewService.createReview(RESTAURANT_ID, reviewRequest);

        verify(restaurantRepository, times(1)).updateReviews(review);
    }

    @NotNull
    private PrepareCreateMethod getPreparedCreateTestMethod() {
        restaurant = new Restaurant(OWNER_ID, RESTO_NAME, RESTO_CAPACITY, RESTO_HOURS);
        ReviewRequest reviewRequest = new ReviewRequestFixture().create();
        when(restaurantRepository.findRestaurantById(RESTAURANT_ID)).thenReturn(restaurant);
        Customer mockCustomer = mock(Customer.class);
        when(customerAssembler.fromDTO(reviewRequest.customer())).thenReturn(mockCustomer);
        Review mockReview = mock(Review.class);
        when(reviewFactory.createReview(any(), any(), any(), any(), any())).thenReturn(mockReview);
        PrepareCreateMethod preparedCreateTest = new PrepareCreateMethod(reviewRequest, mockReview);
        return preparedCreateTest;
    }

    private record PrepareCreateMethod(ReviewRequest reviewRequest, Review mockReview) {
    }
}
