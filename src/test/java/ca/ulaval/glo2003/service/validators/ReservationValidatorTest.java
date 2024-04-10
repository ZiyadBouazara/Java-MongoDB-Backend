package ca.ulaval.glo2003.service.validators;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.controllers.api.fixture.ReservationRequestFixture;
import ca.ulaval.glo2003.controllers.requests.ReservationRequest;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.utils.Hours;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationValidatorTest {
    private static final String OWNER_ID = "1";
    private static final String RESTO_NAME = "1";
    private static final Integer RESTO_CAPACITY = 10;
    private static final Hours RESTO_HOURS = new Hours("10:00:00", "21:00:00");
    private static final String VALID_DATE = "2024-03-05";
    private static final String INVALID_DATE = "2024/03/05";
    private static final String VALID_START_TIME = "12:00:00";
    private static final String INVALID_START_TIME = "25:00:00";
    private static final Integer VALID_GROUP_SIZE = 5;
    private static final Integer INVALID_GROUP_SIZE = 0;
    private static final String INVALID_EMAIL = "invalid.ca";
    private static final String INVALID_PHONE_NUMBER = "123";

    ReservationValidator reservationValidator;

    @BeforeEach
    void setup() {
        reservationValidator = new ReservationValidator();
    }

    @Test
    void givenExceedingGroupSize_whenCreating_shouldThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture().withGroupSize(15).create();
        Restaurant restaurant = new Restaurant(OWNER_ID, RESTO_NAME, RESTO_CAPACITY, RESTO_HOURS);

        assertThrows(InvalidParameterException.class, () -> reservationValidator.validateGroupSizeWithinRestaurantLimit(
                reservationRequest, restaurant));
    }

    @Test
    void givenValidParameters_whenCreating_shouldNotThrowException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture().create();

        assertDoesNotThrow(() -> reservationValidator.validateReservationRequest(reservationRequest));
    }

    @Test
    void givenValidGroupSize_whenCreating_shouldNotThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withGroupSize(VALID_GROUP_SIZE)
                .create();

        assertDoesNotThrow(() -> reservationValidator.validateReservationRequest(reservationRequest));
    }

    @Test
    void givenValidStartTime_whenCreating_shouldNotThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withStartTime(VALID_START_TIME)
                .create();

        assertDoesNotThrow(() -> reservationValidator.validateReservationRequest(reservationRequest));
    }

    @Test
    void givenValidDate_whenCreating_shouldNotThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withDate(VALID_DATE)
                .create();

        assertDoesNotThrow(() ->
                reservationValidator.validateReservationRequest(reservationRequest));
    }

    @Test
    void givenMissingDate_whenCreating_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .createWithMissingDate();

        assertThrows(MissingParameterException.class, () ->
                reservationValidator.validateReservationRequest(reservationRequest));
    }

    @Test
    void givenMissingStartTime_whenCreating_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .createWithMissingStartTime();

        assertThrows(MissingParameterException.class, () ->
                reservationValidator.validateReservationRequest(reservationRequest));
    }

    @Test
    void givenMissingGroupSize_whenCreating_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .createWithMissingGroupSize();

        assertThrows(MissingParameterException.class, () ->
                reservationValidator.validateReservationRequest(reservationRequest));
    }

    @Test
    void givenMissingCustomer_whenCreating_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .createWithMissingCustomer();

        assertThrows(MissingParameterException.class, () ->
                reservationValidator.validateReservationRequest(reservationRequest));
    }

    @Test
    void givenInvalidCustomerEmail_whenCreating_shouldThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withCustomerEmail(INVALID_EMAIL)
                .create();

        assertThrows(InvalidParameterException.class, () ->
                reservationValidator.validateReservationRequest(reservationRequest));
    }

    @Test
    void givenInvalidCustomerPhoneNumber_whenCreating_shouldThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withCustomerPhoneNumber(INVALID_PHONE_NUMBER)
                .create();

        assertThrows(InvalidParameterException.class, () ->
                reservationValidator.validateReservationRequest(reservationRequest));
    }

    @Test
    void givenMissingCustomerName_whenCreating_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withCustomerName(null)
                .create();

        assertThrows(MissingParameterException.class, () ->
                reservationValidator.validateReservationRequest(reservationRequest));
    }

    @Test
    void givenMissingCustomerEmail_whenCreating_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withCustomerEmail(null)
                .create();

        assertThrows(MissingParameterException.class, () ->
                reservationValidator.validateReservationRequest(reservationRequest));
    }

    @Test
    void givenMissingCustomerPhoneNumber_whenCreating_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withCustomerPhoneNumber(null)
                .create();

        assertThrows(MissingParameterException.class, () ->
                reservationValidator.validateReservationRequest(reservationRequest));
    }

    @Test
    void givenInvalidGroupSize_whenCreating_shouldThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withGroupSize(INVALID_GROUP_SIZE)
                .create();

        assertThrows(InvalidParameterException.class, () ->
                reservationValidator.validateReservationRequest(reservationRequest));
    }

    @Test
    void givenInvalidStartTime_whenCreating_shouldThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withStartTime(INVALID_START_TIME)
                .create();

        assertThrows(InvalidParameterException.class, () ->
                reservationValidator.validateReservationRequest(reservationRequest));
    }

    @Test
    void givenInvalidDate_whenCreating_shouldThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withDate(INVALID_DATE)
                .create();

        assertThrows(InvalidParameterException.class, () ->
                reservationValidator.validateReservationRequest(reservationRequest));
    }

    @Test
    void givenValidDate_whenSearchingReservation_shouldNotThrowException() {
        assertDoesNotThrow(() ->
                reservationValidator.validateSearchReservationRequest("restaurantId", VALID_DATE));
    }

    @Test
    void givenInvalidDate_whenSearchingReservation_shouldThrowInvalidParameterException() {
        assertThrows(InvalidParameterException.class, () ->
                reservationValidator.validateSearchReservationRequest("restaurantId", INVALID_DATE));
    }

    @Test
    void givenMissingRestaurantId_whenSearchingReservation_shouldThrowMissingParameterException() {
        assertThrows(MissingParameterException.class, () ->
                reservationValidator.validateSearchReservationRequest(null, VALID_DATE));
    }

    @Test
    void givenEmptyRestaurantId_whenSearchingReservation_shouldThrowMissingParameterException() {
        assertThrows(MissingParameterException.class, () ->
                reservationValidator.validateSearchReservationRequest("", VALID_DATE));
    }

    @Test
    void givenNullRestaurantId_whenSearchingReservation_shouldThrowMissingParameterException() {
        assertThrows(MissingParameterException.class, () ->
                reservationValidator.validateSearchReservationRequest(null, VALID_DATE));
    }

    @Test
    void givenNullDate_whenSearchingReservation_shouldNotThrowException() {
        assertDoesNotThrow(() ->
                reservationValidator.validateSearchReservationRequest("restaurantId", null));
    }

    @Test
    void givenEmptyDate_whenSearchingReservation_shouldThrowException() {
        assertThrows(InvalidParameterException.class, () ->
                reservationValidator.validateSearchReservationRequest("restaurantId", ""));
    }

    @Test
    void givenInvalidSearchReservationRequest_whenSearchingReservation_shouldThrowException() {
        assertThrows(InvalidParameterException.class, () ->
                reservationValidator.validateSearchReservationRequest("restaurantId", "2024/03/05"));
    }

    @Test
    void givenValidSearchReservationRequest_whenSearchingReservation_shouldNotThrowException() {
        assertDoesNotThrow(() ->
                reservationValidator.validateSearchReservationRequest("restaurantId", "2024-03-05"));
    }

    @Test
    public void givenMissingDate_whenVerifyingSearchAvailabilities_ShouldThrowMissingParameterException() {
        assertThrows(MissingParameterException.class, () -> reservationValidator.verifySearchAvailabilities(null));
    }

    @Test
    public void givenInvalidDate_whenVerifyingSearchAvailabilities_ShouldThrowInvalidParameterException() {
        assertThrows(InvalidParameterException.class, () -> reservationValidator.verifySearchAvailabilities(INVALID_DATE));
    }

    @Test
    public void givenValidDate_whenVerifyingSearchAvailabilities_ShouldNotThrowParameterException() {
        assertDoesNotThrow(() -> reservationValidator.verifySearchAvailabilities(VALID_DATE));
    }
}
