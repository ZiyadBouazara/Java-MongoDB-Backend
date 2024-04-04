package ca.ulaval.glo2003.service.validators;

import ca.ulaval.glo2003.controllers.requests.FuzzySearchRequest;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;

import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SearchRestaurantValidator {
    private static final String TIME_FORMAT_REGEX = "^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
    private static final Pattern TIME_PATTERN_VISIT_TIME = Pattern.compile("^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$");
    private static final Pattern TIME_PATTERN = Pattern.compile(TIME_FORMAT_REGEX);


    public void verifyFuzzySearchValidParameters(FuzzySearchRequest fuzzySearch) throws InvalidParameterException {
        verifyFuzzySearchNotNull(fuzzySearch);
        verifyFuzzySearchValidName(fuzzySearch);
        verifyFuzzySearchValidVisitTime(fuzzySearch);
    }

    public void verifyFuzzySearchValidName(FuzzySearchRequest fuzzySearch) throws InvalidParameterException {
        if (fuzzySearch.name() != null && !(fuzzySearch.name() instanceof String)) {
            throw new InvalidParameterException("Name parameter is not a String");
        }
    }

    private void verifyFuzzySearchValidVisitTime(FuzzySearchRequest fuzzySearch) throws InvalidParameterException {
        verifyValidVisitTimeFormatForFromAndTo(fuzzySearch);
        if (fuzzySearch.opened() != null) {
            String from = fuzzySearch.opened().from();
            String to = fuzzySearch.opened().to();

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

    private void verifyValidVisitTimeFormatForFromAndTo(FuzzySearchRequest fuzzySearch) throws InvalidParameterException {
        if (fuzzySearch.opened() != null) {
            verifyValidVisitTimeFormat(fuzzySearch.opened().from());
            verifyValidVisitTimeFormat(fuzzySearch.opened().to());
        }
    }

    private void verifyFuzzySearchNotNull(FuzzySearchRequest fuzzySearch) throws InvalidParameterException {
        if (fuzzySearch == null) {
            throw new InvalidParameterException("Search object is null");
        }
    }
}
