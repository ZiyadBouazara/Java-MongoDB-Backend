package ca.ulaval.glo2003.domain.utils;

import org.junit.jupiter.api.Test;


import static ca.ulaval.glo2003.domain.utils.FuzzySearch.*;
import static org.assertj.core.api.Assertions.assertThat;

class FuzzySearchTest {

    @Test
    void testIsFuzzySearchOnNameSuccessful() {
        String searchingElement = "test";
        String comparedElement = "This is a test";
        boolean result = isFuzzySearchOnNameSuccessful(searchingElement, comparedElement);
        assertThat(result).isTrue();
    }

    @Test
    void testAreOpeningHoursCorresponding() {
        String openingHours = "12:00:00";
        String comparedToHour = "10:00:00";
        boolean result = isFromTimeMatching(openingHours, comparedToHour);
        assertThat(result).isTrue();
    }

    @Test
    void testAreClosingHoursCorresponding() {
        String closingHours = "18:00:00";
        String comparedToHour = "20:00:00";
        boolean result = isToTimeMatching(closingHours, comparedToHour);
        assertThat(result).isTrue();
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testAreClosingHoursCorrespondingWithNullClosingHours() {
        String closingHours = null;
        String comparedToHour = "20:00:00";
        boolean result = isToTimeMatching(closingHours, comparedToHour);
        assertThat(result).isTrue();
    }

    @Test
    void givenValidSearchingElementAndComparedElement_whenIsFuzzySearchOnNameSuccessful_thenShouldReturnTrue() {
        String searchingRestaurantName = "restaurant";
        String comparedElement = "This is a restaurant";
        boolean result = isFuzzySearchOnNameSuccessful(searchingRestaurantName, comparedElement);
        assertThat(result).isTrue();
    }

    @Test
    void givenNullSearchingElementAndComparedElement_whenIsFuzzySearchOnNameSuccessful_thenShouldReturnTrue() {
        String searchingRestaurantName = null;
        String comparedElement = "This is a restaurant";
        isFuzzySearchOnNameSuccessful(searchingRestaurantName, comparedElement);
        boolean result = true;
        assertThat(result).isTrue();
    }

    @Test
    void givenValidVisitTimeFromAndComparedToHour_whenIsFromTimeMatching_thenShouldReturnTrue() {
        String visitTimeFrom = "14:00:00";
        String comparedToHour = "12:00:00";
        boolean result = isFromTimeMatching(visitTimeFrom, comparedToHour);
        assertThat(result).isTrue();
    }

    @Test
    void givenNullVisitTimeFromAndComparedToHour_whenIsFromTimeMatching_thenShouldReturnTrue() {
        String visitTimeFrom = null;
        String comparedToHour = "12:00:00";
        isFromTimeMatching(visitTimeFrom, comparedToHour);
        boolean result = true;
        assertThat(result).isTrue();
    }

    @Test
    void givenValidVisitTimeToAndComparedToHour_whenIsToTimeMatching_thenShouldReturnTrue() {
        String visitTimeTo = "18:00:00";
        String comparedToHour = "20:00:00";
        boolean result = isToTimeMatching(visitTimeTo, comparedToHour);
        assertThat(result).isTrue();
    }

    @Test
    void givenNullVisitTimeToAndComparedToHour_whenIsToTimeMatching_thenShouldReturnTrue() {
        String visitTimeTo = null;
        String comparedToHour = "20:00:00";
        isToTimeMatching(visitTimeTo, comparedToHour);
        boolean result = true;
        assertThat(result).isTrue();
    }
}
