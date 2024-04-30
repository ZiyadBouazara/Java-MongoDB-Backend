package ca.ulaval.glo2003.service.validators;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class SearchReviewValidator {
    public void validate(Double rating, String date)
        throws InvalidParameterException {
        verifyValidParameters(rating, date);
    }

    private void verifyValidParameters(Double rating, String date) throws InvalidParameterException {
        verifyValidRating(rating);
        verifyValidDate(date);
    }

    private void verifyValidRating(Double rating) throws InvalidParameterException {
        if (rating != null && (rating < 0 || rating > 5)) {
            throw new InvalidParameterException("Invalid rating parameter. Rating must be between 0 and 5.");
        }
    }

    private void verifyValidDate(String date) throws InvalidParameterException {
        if (date != null) {
            try {
                LocalDate.parse(date);
            } catch (DateTimeParseException e) {
                throw new InvalidParameterException("Invalid date parameter. Date format must be yyyy-MM-dd.");
            }
        }
    }
}

