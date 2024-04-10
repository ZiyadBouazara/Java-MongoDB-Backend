package ca.ulaval.glo2003.service.validators;

import ca.ulaval.glo2003.controllers.requests.RestaurantRequest;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.service.dtos.HoursDTO;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class CreateRestaurantValidatorTest {
    private static final String VALID_OWNER_ID = "owner123";
    private static final int INVALID_CAPACITY = 0;
    private static final String VALID_OPEN_TIME = "10:00:00";
    private static final String SHORT_DURATION = "10:30:00";
    private static final String INVALID_OPEN_TIME_FORMAT = "invalid time";
    private static final String VALID_CLOSE_TIME = "22:00:00";
    private static final HoursDTO INVALID_TIME_FORMAT = new HoursDTO(INVALID_OPEN_TIME_FORMAT, VALID_CLOSE_TIME);
    private static final HoursDTO LESS_THAN_60_DURATION = new HoursDTO(VALID_OPEN_TIME, SHORT_DURATION);
    private CreateRestaurantValidator validator = new CreateRestaurantValidator();

    @Test
    public void givenValidParameters_whenValidate_shouldReturnNoException() throws Exception {
        RestaurantRequest request = new RestaurantRequestFixture().create();

        validator.validate(VALID_OWNER_ID, request);
    }

    @Test
    public void givenMissingName_whenValidate_shouldThrowMissingParameterException() {
        RestaurantRequest request = new RestaurantRequestFixture().createWithMissingName();
        assertThrows(MissingParameterException.class, () -> validator.validate(VALID_OWNER_ID, request));
    }

    @Test
    public void givenMissingCapacity_whenValidate_shouldThrowMissingParameterException() {
        RestaurantRequest request = new RestaurantRequestFixture().createWithMissingCapacity();
        assertThrows(MissingParameterException.class, () -> validator.validate(VALID_OWNER_ID, request));
    }

    @Test
    public void givenMissingHours_whenValidate_shouldThrowMissingParameterException() {
        RestaurantRequest request = new RestaurantRequestFixture().createWithMissingHours();
        assertThrows(MissingParameterException.class, () -> validator.validate(VALID_OWNER_ID, request));
    }

    @Test
    public void givenBlankName_whenValidate_shouldThrowInvalidParameterException() {
        RestaurantRequest request = new RestaurantRequestFixture().withName("").create();
        assertThrows(InvalidParameterException.class, () -> validator.validate(VALID_OWNER_ID, request));
    }

    @Test
    public void givenInvalidCapacity_whenValidate_shouldThrowInvalidParameterException() {
        RestaurantRequest request = new RestaurantRequestFixture().withCapacity(INVALID_CAPACITY).create();
        assertThrows(InvalidParameterException.class, () -> validator.validate(VALID_OWNER_ID, request));
    }

    @Test
    public void givenNotOpenForMinimumDuration_whenValidate_shouldThrowInvalidParameterException() {
        RestaurantRequest request = new RestaurantRequestFixture().withHours(LESS_THAN_60_DURATION).create();
        assertThrows(InvalidParameterException.class, () -> validator.validate(VALID_OWNER_ID, request));
    }

    @Test
    public void givenNotValidTimeFormat_whenValidate_shouldThrowInvalidParameterException() {
        RestaurantRequest request = new RestaurantRequestFixture().withHours(INVALID_TIME_FORMAT).create();
        assertThrows(InvalidParameterException.class, () -> validator.validate(VALID_OWNER_ID, request));
    }
}
