package ca.ulaval.glo2003.domain.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FuzzySearch {
    private String name;
    private VisitTime hours;

    public FuzzySearch() {
        this.hours = new VisitTime();
    }

    @JsonProperty("name")
    public String getName() {
        return (name != null) ? name : null;
    }

    @JsonProperty("name")
    public void setName(String restaurantName) {
        this.name = restaurantName;
    }

    @JsonProperty("hours")
    public VisitTime getHours() {
        return this.hours;
    }

    public void setHours(VisitTime visitTime) {
        this.hours = visitTime;
    }

    public static boolean isFuzzySearchOnNameSuccessful(String searchingElement, String comparedElement) {
        if (searchingElement != null) {
            String cleanedSearchingElement = searchingElement.replaceAll("\\s", "").toLowerCase();
            String cleanedComparedElement = comparedElement.replaceAll("\\s", "").toLowerCase();

            return cleanedComparedElement.contains(cleanedSearchingElement);
        }
        return true;
    }

    public static boolean isFromTimeMatching(String visitTimeFrom, String restaurantOpeningHour) {
        if (visitTimeFrom != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime from = LocalTime.parse(visitTimeFrom, formatter);
            LocalTime restaurantOpenHour = LocalTime.parse(restaurantOpeningHour, formatter);

            return from.isAfter(restaurantOpenHour) || from.equals(restaurantOpenHour);
        }
        return true;
    }

    public static boolean isToTimeMatching(String visitTimeTo, String restaurantClosingHour) {
        if (visitTimeTo != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime to = LocalTime.parse(visitTimeTo, formatter);
            LocalTime restaurantCloseHour = LocalTime.parse(restaurantClosingHour, formatter);

            return to.isBefore(restaurantCloseHour) || to.equals(restaurantCloseHour);
        }
        return true;
    }
}
