package ca.ulaval.glo2003.service.validators;

import ca.ulaval.glo2003.service.dtos.HoursDTO;
import ca.ulaval.glo2003.controllers.requests.RestaurantRequest;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateRestaurantValidator {
    private static final String TIME_FORMAT_REGEX = "^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
    private static final Pattern TIME_PATTERN = Pattern.compile(TIME_FORMAT_REGEX);

    public void validate(String ownerId, RestaurantRequest restaurantRequest)
            throws MissingParameterException, InvalidParameterException {
        verifyParameters(restaurantRequest);
    }

    public void verifyParameters(RestaurantRequest restaurantRequest)
            throws InvalidParameterException, MissingParameterException {

        verifyMissingParameters(restaurantRequest);
        verifyValidParameters(restaurantRequest);
    }

    private void verifyMissingParameters(RestaurantRequest restaurantRequest) throws MissingParameterException {
        verifyMissingCapacity(restaurantRequest.capacity());
        verifyMissingName(restaurantRequest.name());
        verifyMissingHours(restaurantRequest.hours());
    }

    private void verifyValidParameters(RestaurantRequest restaurantRequest) throws InvalidParameterException {
        verifyValidName(restaurantRequest.name());
        verifyValidCapacity(restaurantRequest.capacity());
        verifyValidHours(restaurantRequest.hours());
    }

    private void verifyMissingCapacity(Integer capacity) throws MissingParameterException {
        if (capacity == null) {
            throw new MissingParameterException("Missing parameter 'capacity'");
        }
    }

    private void verifyMissingName(String name) throws MissingParameterException {
        if (name == null) {
            throw new MissingParameterException("Missing parameter 'name'");
        }
    }

    private void verifyMissingHours(HoursDTO hours) throws MissingParameterException {
        if (hours == null || hours.open() == null || hours.close() == null) {
            throw new MissingParameterException("Missing parameter 'hours'");
        }
    }

    private void verifyValidName(String name) throws InvalidParameterException {
        if (name.isEmpty()) {
            throw new InvalidParameterException("Invalid parameter 'name', cant be blank");
        }
    }

    private void verifyValidCapacity(Integer capacity) throws InvalidParameterException {
        if (capacity < 1) {
            throw new InvalidParameterException("Invalid parameter 'capacity', minimum capacity of 1 person");
        }
    }

    private void verifyValidHours(HoursDTO hours) throws InvalidParameterException {
        isValidTimeFormat(hours.open());
        isValidTimeFormat(hours.close());
        openForMinimumDuration(hours);
        doesNotOpenBeforeMidnight(hours);
        closesBeforeMidnight(hours);
    }

    private void isValidTimeFormat(String time) throws InvalidParameterException {
        Matcher matcher = TIME_PATTERN.matcher(time);
        if (!matcher.matches()) {
            throw new InvalidParameterException("Invalid time format: " + time + ". Use the 'HH:MM:SS' format");
        }
    }

    private void openForMinimumDuration(HoursDTO hours) throws InvalidParameterException {
        LocalTime openingTime = LocalTime.parse(hours.open());
        LocalTime closingTime = LocalTime.parse(hours.close());

        Duration duration = Duration.between(openingTime, closingTime);

        if (duration.toMinutes() < 60) {
            throw new InvalidParameterException("Invalid parameter 'hours', must be open for at least 1 hour");
        }
    }

    private void doesNotOpenBeforeMidnight(HoursDTO hours) throws InvalidParameterException {
        if (!hours.open().equals("00:00:00") && hours.open().compareTo("00:00:00") < 0) {
            throw new InvalidParameterException("Invalid parameter 'hours.open', can't open before midnight");
        }
    }

    private void closesBeforeMidnight(HoursDTO hours) throws InvalidParameterException {
        if (!hours.close().equals("23:59:59") && hours.close().compareTo("23:59:59") > 0) {
            throw new InvalidParameterException("Invalid parameter 'hours.close', must close before midnight");
        }
    }
}
