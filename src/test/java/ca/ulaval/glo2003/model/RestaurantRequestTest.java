package ca.ulaval.glo2003.model;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.models.RestaurantRequest;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantRequestTest {
    private static final String EMPTY_NAME = "";
    private static final int LESS_THAN_MINIMUM_CAPACITY = 0;
    private static final String VALID_TIME_FORMAT = "00:00:00";
    private static final String INVALID_TIME_FORMAT = "12:30";

    @Test
    void givenMatchingOwnerIds_whenVerifyRestaurantOwnership_shouldNotThrowException() {
        assertDoesNotThrow(() -> RestaurantRequest.verifyRestaurantOwnership("ownerId", "ownerId"));
    }

    @Test
    void givenNonMatchingOwnerIds_whenVerifyRestaurantOwnership_shouldThrowNotFoundException() {
        assertThrows(NotFoundException.class, () -> RestaurantRequest.verifyRestaurantOwnership("expectedOwnerId", "actualOwnerId"));
    }

    @Test
    public void givenMissingName_whenVerifyParameters_shouldThrowMissingParameterException() {
        RestaurantRequest restaurantRequest = new RestaurantRequestFixture()
            .createWithMissingName();

        assertThrows(MissingParameterException.class, restaurantRequest::verifyParameters);
    }

    @Test
    public void givenMissingHours_whenVerifyParameters_shouldThrowMissingParameterException() {
        RestaurantRequest restaurantRequest = new RestaurantRequestFixture()
            .createWithMissingHours();

        assertThrows(MissingParameterException.class, restaurantRequest::verifyParameters);
    }

    @Test
    public void givenMissingCapacity_whenVerifyParameters_shouldThrowMissingParameterException() {
        RestaurantRequest restaurantRequest = new RestaurantRequestFixture()
            .createWithMissingCapacity();

        assertThrows(MissingParameterException.class, restaurantRequest::verifyParameters);
    }

    @Test
    public void givenEmptyName_whenVerifyParameters_shouldThrowInvalidParameterException() {
        RestaurantRequest restaurantRequest = new RestaurantRequestFixture()
            .withName(EMPTY_NAME)
            .create();
        assertThrows(InvalidParameterException.class, restaurantRequest::verifyParameters);
    }

    @Test
    public void givenCapacityLessThanMinimum_whenVerifyParameters_shouldThrowInvalidParameterException() {
        RestaurantRequest restaurantRequest = new RestaurantRequestFixture()
            .withCapacity(LESS_THAN_MINIMUM_CAPACITY)
            .create();
        assertThrows(InvalidParameterException.class, restaurantRequest::verifyParameters);
    }

    @Test
    public void givenInvalidTimeFormat_whenVerifyParameters_shouldThrowInvalidParameterException() {
        RestaurantRequest restaurantRequest = new RestaurantRequestFixture()
            .withOpenHour(INVALID_TIME_FORMAT)
            .create();

        assertThrows(InvalidParameterException.class, restaurantRequest::verifyParameters);
    }

    @Test
    public void givenValidTimeFormat_whenVerifyParameters_shouldNotThrowException() {
        RestaurantRequest restaurantRequest = new RestaurantRequestFixture()
            .withOpenHour(VALID_TIME_FORMAT)
            .create();

        assertDoesNotThrow(restaurantRequest::verifyParameters);

    }

    @Test
    public void givenNotOpenForMinimumDuration_whenVerifyParameters_shouldThrowInvalidParameterException() {
        RestaurantRequest restaurantRequest = new RestaurantRequestFixture()
            .withOpenHour("00:00:00")
            .withCloseHour("00:59:00")
            .create();

        assertThrows(InvalidParameterException.class, restaurantRequest::verifyParameters);
    }

    @Test
    public void givenClosingBeforeOpening_whenVerifyParameters_shouldThrowInvalidParameterException() {
        RestaurantRequest restaurantRequest = new RestaurantRequestFixture()
            .withOpenHour("10:00:00")
            .withCloseHour("08:00:00")
            .create();

        assertThrows(InvalidParameterException.class, restaurantRequest::verifyParameters);
    }
}
