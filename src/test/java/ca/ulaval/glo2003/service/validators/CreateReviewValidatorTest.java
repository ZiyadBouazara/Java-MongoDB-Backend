package ca.ulaval.glo2003.service.validators;

import ca.ulaval.glo2003.controllers.api.fixture.ReviewRequestFixture;
import ca.ulaval.glo2003.controllers.requests.ReviewRequest;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.service.dtos.CustomerDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateReviewValidatorTest {
    private static final String INVALID_FUTURE_DATE = "2028-01-01";
    private static final String INVALID_DATE_FORMAT = "01-01-01";
    private static final Double INVALID_RATING = 0.0;
    private static final String INVALID_PHONE_NUMBER = "5";
    private static final CustomerDTO invalidCustomer = new CustomerDTO("John Doe", "john@gmail.com", INVALID_PHONE_NUMBER);
    private CreateReviewValidator validator = new CreateReviewValidator();

    @Test
    public void givenValidReview_whenValidateReviewRequest_shouldNotThrowExceptions()
        throws InvalidParameterException, MissingParameterException {
        ReviewRequest review = new ReviewRequestFixture().create();

        validator.validateReviewRequest(review);
    }

    @Test
    public void givenMissingDate_whenVerifyMissingParameters_shouldThrowMissingParameterException() {
        ReviewRequest review = new ReviewRequestFixture().createWithMissingDate();

        assertThrows(MissingParameterException.class, () -> validator.verifyMissingParameters(review));
    }

    @Test
    public void givenMissingCustomer_whenVerifyMissingParameters_shouldThrowMissingParameterException() {
        ReviewRequest review = new ReviewRequestFixture().createWithMissingCustomer();

        assertThrows(MissingParameterException.class, () -> validator.verifyMissingParameters(review));
    }

    @Test
    public void givenMissingRating_whenVerifyMissingParameters_shouldThrowMissingParameterException() {
        ReviewRequest review = new ReviewRequestFixture().createWithMissingRating();

        assertThrows(MissingParameterException.class, () -> validator.verifyMissingParameters(review));
    }

    @Test
    public void givenDateInTheFuture_whenVerifyValidParameters_shouldThrowInvalidParameterException() {
        ReviewRequest review = new ReviewRequestFixture().withDate(INVALID_FUTURE_DATE).create();

        assertThrows(InvalidParameterException.class, () -> validator.verifyValidParameters(review));
    }

    @Test
    public void givenInvalidDateFormat_whenVerifyValidParameters_shouldThrowInvalidParameterException() {
        ReviewRequest review = new ReviewRequestFixture().withDate(INVALID_DATE_FORMAT).create();

        assertThrows(InvalidParameterException.class, () -> validator.verifyValidParameters(review));
    }

    @Test
    public void givenInvalidRating_whenVerifyValidParameters_shouldThrowInvalidParameterException() {
        ReviewRequest review = new ReviewRequestFixture().withRating(INVALID_RATING).create();

        assertThrows(InvalidParameterException.class, () -> validator.verifyValidParameters(review));
    }

    @Test
    public void givenInvalidCustomer_whenVerifyValidParameters_shouldThrowInvalidParameterException() {
        ReviewRequest review = new ReviewRequestFixture().withCustomer(invalidCustomer).create();

        assertThrows(InvalidParameterException.class, () -> validator.verifyValidParameters(review));
    }
}
