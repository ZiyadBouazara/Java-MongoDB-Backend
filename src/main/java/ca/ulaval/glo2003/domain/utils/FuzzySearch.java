package ca.ulaval.glo2003.domain.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FuzzySearch {
    private String name;
    private VisitTime opened;

    public FuzzySearch(String name, VisitTime opened) {
        this.name = name;
        this.opened = opened;
    }

    @JsonProperty("name")
    public String getName() {
        return (name != null) ? name : null;
    }

    @JsonProperty("name")
    public void setName(String restaurantName) {
        this.name = restaurantName;
    }

    @JsonProperty("opened")
    public VisitTime getOpened() {
        return this.opened;
    }

    public void setOpened(VisitTime visitTime) {
        this.opened = visitTime;
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
            LocalTime searchFrom = LocalTime.parse(visitTimeFrom, formatter);
            LocalTime restaurantOpenHour = LocalTime.parse(restaurantOpeningHour, formatter);

            System.out.println(searchFrom.isAfter(restaurantOpenHour) || searchFrom.equals(restaurantOpenHour));
            return searchFrom.isAfter(restaurantOpenHour) || searchFrom.equals(restaurantOpenHour);
        }
        return true;
    }

    public static boolean isToTimeMatching(String visitTimeTo, String restaurantClosingHour) {
        if (visitTimeTo != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime searchTo = LocalTime.parse(visitTimeTo, formatter);
            LocalTime restaurantCloseHour = LocalTime.parse(restaurantClosingHour, formatter);

            return searchTo.isBefore(restaurantCloseHour) || searchTo.equals(restaurantCloseHour);
        }
        return true;
    }
}
