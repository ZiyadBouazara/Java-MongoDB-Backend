package ca.ulaval.glo2003.domain.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FuzzySearch {
    private String name;
    private VisitTime visitTime;

    public FuzzySearch() {
    }

    public String getName() {
        return (name != null) ? name : null;
    }

    public void setName(String restaurantName) {
        this.name = restaurantName;
    }

    public VisitTime getVisitTime(){return this.visitTime;}

    public void setHours(VisitTime visitTime){
        this.visitTime = visitTime;
    }

    public static boolean isFuzzySearchOnNameSuccessful(String searchingElement, String comparedElement) {
        if(searchingElement != null){
            String cleanedSearchingElement = searchingElement.replaceAll("\\s", "").toLowerCase();
            String cleanedComparedElement = comparedElement.replaceAll("\\s", "").toLowerCase();

            return cleanedComparedElement.contains(cleanedSearchingElement);
        }
        return true;
    }

    public static boolean areFromHoursMatching(String visitTimeFrom, String comparedToHour) {
        if(visitTimeFrom != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime from = LocalTime.parse(visitTimeFrom, formatter);
            LocalTime comparedHour = LocalTime.parse(comparedToHour);

            return from.isAfter(comparedHour) || from.equals(comparedHour);
        }
        return true;
    }

    public static boolean areToHoursMatching(String visitTimeTo, String comparedToHour) {
        if(visitTimeTo != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime to = LocalTime.parse(visitTimeTo, formatter);
            LocalTime comparedHour = LocalTime.parse(comparedToHour);

            return to.isBefore(comparedHour) || to.equals(comparedHour);
        }
        return true;
    }
}
