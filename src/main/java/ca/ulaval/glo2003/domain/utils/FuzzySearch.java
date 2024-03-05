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

    public static boolean isFromTimeMatching(String visitTimeFrom, String comparedToHour) {
        if (visitTimeFrom != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime from = LocalTime.parse(visitTimeFrom, formatter);
            LocalTime comparedHour = LocalTime.parse(comparedToHour, formatter);

            return from.isAfter(comparedHour) || from.equals(comparedHour);
        }
        return true;
    }

    public static boolean isToTimeMatching(String visitTimeTo, String comparedToHour) {
        if (visitTimeTo != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime to = LocalTime.parse(visitTimeTo, formatter);
            LocalTime comparedHour = LocalTime.parse(comparedToHour, formatter);

            return to.isBefore(comparedHour) || to.equals(comparedHour);
        }
        return true;
    }
}
