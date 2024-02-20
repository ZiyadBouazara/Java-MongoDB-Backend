package ca.ulaval.glo2003.controllers.validators;

import java.util.regex.Pattern;

public class CreateRestaurantValidator {
    private static final String TIME_FORMAT_REGEX = "^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
    private static final Pattern TIME_PATTERN = Pattern.compile(TIME_FORMAT_REGEX);
}
