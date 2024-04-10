package ca.ulaval.glo2003.service.validators;

import ca.ulaval.glo2003.controllers.requests.FuzzySearchRequest;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.service.dtos.VisitTimeDTO;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SearchRestaurantValidatorTest {
    private final SearchRestaurantValidator validator = new SearchRestaurantValidator();
    private static final String VALID_TEST_NAME = "test";
    private static final String VALID_TIME_FORMAT = "12:00:00";
    private static final String INVALID_TIME_FORMAT = "2:0:00";
    private static final VisitTimeDTO VALID_VISIT_TIME = new VisitTimeDTO("11:00:00", "12:00:00");
    @Test
    public void givenValidParameters_whenVerifyFuzzySearchValidParameters_ShouldNotThrowException() {
        FuzzySearchRequest validRequest = new FuzzySearchRequest(VALID_TEST_NAME, VALID_VISIT_TIME);
        assertDoesNotThrow(() -> validator.verifyFuzzySearchValidParameters(validRequest));
    }

    @Test
    public void givenNullRequest_whenVerifyFuzzySearchValidParameters_ShouldInvalidParameterException() {
        assertThrows(InvalidParameterException.class, () -> validator.verifyFuzzySearchValidParameters(null));
    }

    @Test
    public void givenValidTimeFormat_whenVerifyValidVisitTimeFormat_NoExceptionThrown() {
        assertDoesNotThrow(() -> SearchRestaurantValidator.verifyValidVisitTimeFormat(VALID_TIME_FORMAT));
    }

    @Test
    public void givenInvalidTimeFormat_whenVerifyValidVisitTimeFormat_ShouldThrowInvalidParameterException() {
        assertThrows(InvalidParameterException.class, () -> SearchRestaurantValidator.verifyValidVisitTimeFormat(INVALID_TIME_FORMAT));
    }
}
