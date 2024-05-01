package ca.ulaval.glo2003.service.validators;

import ca.ulaval.glo2003.controllers.requests.ReviewRequest;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.service.dtos.CustomerDTO;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateReviewValidator {
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

    public void validateReviewRequest(ReviewRequest reviewRequest)
            throws MissingParameterException, InvalidParameterException {
        verifyMissingParameters(reviewRequest);
        verifyValidParameters(reviewRequest);
    }

    public void verifyMissingParameters(ReviewRequest reviewRequest) throws MissingParameterException {
        verifyMissing("date", reviewRequest.date());
        verifyMissing("rating", reviewRequest.rating());
        verifyMissingCustomer(reviewRequest);
    }

    private void verifyMissingCustomer(ReviewRequest reviewRequest) throws MissingParameterException {
        verifyMissing("customer", reviewRequest.customer());
        verifyMissing("customer: name", reviewRequest.customer().name());
        verifyMissing("customer: email", reviewRequest.customer().email());
        verifyMissing("customer: phoneNumber", reviewRequest.customer().phoneNumber());
    }

    private void verifyMissing(String parameterName, Object parameterValue) throws MissingParameterException {
        if (parameterValue == null || parameterValue instanceof Double && Double.isNaN((Double) parameterValue)) {
            throw new MissingParameterException("Missing parameter '" + parameterName + "'");
        }
    }

    public void verifyValidParameters(ReviewRequest reviewRequest) throws InvalidParameterException {
        verifyValidDate(reviewRequest.date());
        verifyValidRating(reviewRequest.rating());
        verifyValidCustomer(reviewRequest.customer());
    }
    public void verifyValidDate(String date) throws InvalidParameterException {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            if (parsedDate.isAfter(LocalDate.now())) {
                throw new InvalidParameterException("Invalid parameter 'date', it must not be in the future");
            }
        } catch (DateTimeParseException e) {
            throw new InvalidParameterException("Invalid parameter 'date', it must be a valid date in the format YYYY-MM-DD");
        }
    }

    public void verifyValidRating(Double rating) throws InvalidParameterException {
        if (rating < 1 || rating > 5) {
            throw new InvalidParameterException("Invalid parameter 'rating', it must be a between 1 and 5 included");
        }
    }

    public void verifyValidCustomer(CustomerDTO customer) throws InvalidParameterException {
        if (customer.name().isEmpty()) {
            throw new InvalidParameterException("Invalid parameter 'customer: name', it must not be empty");
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
}
