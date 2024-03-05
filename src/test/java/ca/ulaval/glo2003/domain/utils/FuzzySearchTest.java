package ca.ulaval.glo2003.domain.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static ca.ulaval.glo2003.domain.utils.FuzzySearch.isFuzzySearchOnNameSuccessful;
import static ca.ulaval.glo2003.domain.utils.FuzzySearch.isFromTimeMatching;
import static ca.ulaval.glo2003.domain.utils.FuzzySearch.isToTimeMatching;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FuzzySearchTest {

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


//    @Test
//    void testAreClosingHoursCorrespondingWithMockedTime() {
//        String closingHours = "18:00:00";
//        String comparedToHour = "20:00:00";
//        LocalTime mockCloseHour = mock(LocalTime.class);
//        when(mockCloseHour.isBefore(LocalTime.parse(comparedToHour))).thenReturn(true);
//        boolean result = isToTimeMatching(closingHours, comparedToHour);
//        assertThat(result).isTrue();
//    }

    @Test
    void givenValidSearchingElementAndComparedElement_whenIsFuzzySearchOnNameSuccessful_thenShouldReturnTrue() {
        String searchingRestaurantName = "restaurant";
        String comparedElement = "This is a restaurant";
        boolean result = isFuzzySearchOnNameSuccessful(searchingRestaurantName, comparedElement);
        assertThat(result).isTrue();
    }

    @Test
    void givenNullSearchingElementAndComparedElement_whenIsFuzzySearchOnNameSuccessful_thenShouldReturnTrue() {

    }

    @Test
    void givenValidVisitTimeFromAndComparedToHour_whenIsFromTimeMatching_thenShouldReturnTrue() {
        // Add a test for valid VisitTimeFrom and comparedToHour
    }

    @Test
    void givenNullVisitTimeFromAndComparedToHour_whenIsFromTimeMatching_thenShouldReturnTrue() {
        // Add a test for a null VisitTimeFrom
    }

    @Test
    void givenValidVisitTimeToAndComparedToHour_whenIsToTimeMatching_thenShouldReturnTrue() {
        // Add a test for valid VisitTimeTo and comparedToHour
    }

    @Test
    void givenNullVisitTimeToAndComparedToHour_whenIsToTimeMatching_thenShouldReturnTrue() {
        // Add a test for a null VisitTimeTo
    }
}

