package ca.ulaval.glo2003.service.validators;

import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.service.dtos.CustomerDTO;
import ca.ulaval.glo2003.controllers.requests.ReservationRequest;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReservationValidator {
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\d{10}$");

    private static final String EMAIL_LOCAL_PART_PATTERN =
            "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*";

    private static final String EMAIL_DOMAIN_PATTERN =
            "(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|" +
                    "\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
                    "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]" +
                    ":(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            EMAIL_LOCAL_PART_PATTERN + "@" + EMAIL_DOMAIN_PATTERN);

    private static final Pattern TIME_PATTERN = Pattern.compile("^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])?$");

    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    public void validateGroupSizeWithinRestaurantLimit(ReservationRequest reservationRequest, Restaurant restaurant)
            throws InvalidParameterException {
        if (restaurant.getCapacity() < reservationRequest.groupSize()) {
            throw new InvalidParameterException("The reservation groupSize cannot exceed the restaurant's capacity");
        }
    }

    public void validateStartTimeInRestaurantBounds(String adjustedStartTime, Restaurant restaurant)
            throws InvalidParameterException {
        LocalTime openTime = LocalTime.parse(restaurant.getHours().getOpen());
        LocalTime closeTime = LocalTime.parse(restaurant.getHours().getClose());
        LocalTime startTime = LocalTime.parse(adjustedStartTime);
        LocalTime endTime = startTime.plusMinutes(restaurant.getRestaurantConfiguration().getDuration());
        if (startTime.isBefore(openTime)) {
            throw new InvalidParameterException("The reservation startTime cannot be before the restaurant's openTime");
        }
        if (endTime.isAfter(closeTime)) {
            throw new InvalidParameterException("The reservation endTime cannot be after the restaurant's endTime");
        }
    }

    public void validateReservationRequest(ReservationRequest reservationRequest)
            throws InvalidParameterException, MissingParameterException {
        verifyMissingParameters(reservationRequest);
        verifyValidParameters(reservationRequest);
    }

    public void verifyMissingParameters(ReservationRequest reservationRequest) throws MissingParameterException {
        verifyMissing("date", reservationRequest.date());
        verifyMissing("startTime", reservationRequest.startTime());
        verifyMissing("groupSize", reservationRequest.groupSize());
        verifyMissingCustomer(reservationRequest);
    }

    private void verifyMissing(String parameterName, Object parameterValue) throws MissingParameterException {
        if (parameterValue == null) {
            throw new MissingParameterException("Missing parameter '" + parameterName + "'");
        }
    }

    private void verifyMissingCustomer(ReservationRequest reservationRequest) throws MissingParameterException {
        verifyMissing("customer", reservationRequest.customer());
        verifyMissing("customer: name", reservationRequest.customer().name());
        verifyMissing("customer: email", reservationRequest.customer().email());
        verifyMissing("customer: phoneNumber", reservationRequest.customer().phoneNumber());
    }

    public void verifyValidParameters(ReservationRequest reservationRequest) throws InvalidParameterException {
        verifyValidDate(reservationRequest.date());
        verifyValidStartTime(reservationRequest.startTime());
        verifyValidGroupSize(reservationRequest.groupSize());
        verifyValidCustomer(reservationRequest.customer());
    }

    private void verifyValidDate(String date) throws InvalidParameterException {
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidParameterException("Invalid parameter 'date', it must be a valid date in the format YYYY-MM-DD");
        }
    }

    private void verifyValidStartTime(String startTime) throws InvalidParameterException {
        if (!TIME_PATTERN.matcher(startTime).matches()) {
            throw new InvalidParameterException("Invalid parameter 'startTime', it must follow the following format: HH:MM:SS");
        }
    }

    private void verifyValidGroupSize(Integer groupSize) throws InvalidParameterException {
        if (groupSize < 1) {
            throw new InvalidParameterException("Invalid parameter 'groupSize', it must be a positive number");
        }
    }

    private void verifyValidCustomer(CustomerDTO customer) throws InvalidParameterException {
        if (customer.name().isEmpty()) {
            throw new InvalidParameterException("Invalid parameter 'customer': name, it must not be empty");
        }
        if (!isValidEmail(customer.email())) {
            throw new InvalidParameterException("Invalid parameter 'customer: email', it must follow the following format: x@y.z");
        }
        if (!PHONE_NUMBER_PATTERN.matcher(customer.phoneNumber()).matches()) {
            throw new InvalidParameterException("Invalid parameter 'customer: phoneNumber', it must contain 10 digits");
        }
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public void verifySearchAvailabilities(String date)
            throws InvalidParameterException, MissingParameterException {
        verifyMissing("date", date);
        verifyValidDate(date);
    }

    public void validateSearchReservationRequest(String restaurantId, String date)
            throws InvalidParameterException, MissingParameterException {
        validateRestaurantId(restaurantId);
        verifyValidSearchDate(date);
    }

    public void validateRestaurantId(String restaurantId) throws MissingParameterException {
        if (restaurantId == null || restaurantId.isEmpty()) {
            throw new MissingParameterException("Restaurant ID is missing.");
        }
    }

    public void verifyValidSearchDate(String date) throws InvalidParameterException {
        if (date != null) {
            if (!DATE_PATTERN.matcher(date).matches()) {
                throw new InvalidParameterException("Invalid parameter 'date', it must be a valid date in the format YYYY-MM-DD");
            }

            String[] parts = date.split("-");
            int month = Integer.parseInt(parts[1]);

            if (month < 1 || month > 12) {
                throw new InvalidParameterException("Invalid parameter 'date', month must be between 1 and 12");
            }
        }
    }
}
