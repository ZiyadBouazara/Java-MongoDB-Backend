package ca.ulaval.glo2003.controllers.validators;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.fuzzySearch.FuzzySearch;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SearchRestaurantValidator {
    private static final String TIME_FORMAT_REGEX = "^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
    private static final Pattern TIME_PATTERN_VISIT_TIME = Pattern.compile("^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$");
    private static final Pattern TIME_PATTERN = Pattern.compile(TIME_FORMAT_REGEX);


    public static void verifyFuzzySearchValidParameters(FuzzySearch fuzzySearch) throws InvalidParameterException {
        verifyFuzzySearchNotNull(fuzzySearch);
        verifyFuzzySearchValidName(fuzzySearch);
        verifyFuzzySearchValidHours(fuzzySearch);
    }

    public static void verifyFuzzySearchValidName(FuzzySearch fuzzySearch) throws InvalidParameterException {
        if (fuzzySearch.getName() != null && !(fuzzySearch.getName() instanceof String)) {
            throw new InvalidParameterException("Name parameter is not a String");
        }
    }

    private static void verifyFuzzySearchValidHours(FuzzySearch fuzzySearch) throws InvalidParameterException {
        verifyValidVisitTimeHours(fuzzySearch);
        if (fuzzySearch.getHours() != null) {
            String from = fuzzySearch.getHours().getFrom();
            String to = fuzzySearch.getHours().getTo();

            if (from != null && to != null && LocalTime.parse(from).isAfter(LocalTime.parse(to))) {
                throw new InvalidParameterException("The 'To' time is before the 'From' time");
            }
        }
    }

    public static void verifyValidVisitTimeFormat(String time) throws InvalidParameterException {
        if (time == null || time.trim().isEmpty()) {
            return;
        }
        Matcher matcher = TIME_PATTERN_VISIT_TIME.matcher(time);

        if (!matcher.find()) {
            throw new InvalidParameterException("Invalid time format: " + time + ". Use the 'HH:MM:SS' format");
        }
    }

    private static void verifyValidVisitTimeHours(FuzzySearch fuzzySearch) throws InvalidParameterException {
        verifyValidVisitTimeFormat(fuzzySearch.getHours().getFrom());
        verifyValidVisitTimeFormat(fuzzySearch.getHours().getTo());
    }

    public static void verifyValidTimeFormat(String time) throws InvalidParameterException {
        Matcher matcher = TIME_PATTERN.matcher(time);
        if (!matcher.matches()) {
            throw new InvalidParameterException("Invalid time format: " + time + ". Use the 'HH:MM:SS' format");
        }
    }

    public static void verifyFuzzySearchNotNull(FuzzySearch fuzzySearch) throws InvalidParameterException {
        if (fuzzySearch == null) {
            throw new InvalidParameterException("Search object is null");
        }
    }
}
