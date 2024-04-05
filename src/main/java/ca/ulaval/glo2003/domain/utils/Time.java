package ca.ulaval.glo2003.domain.utils;

import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Time {
    public String start;
    public String end;

    public Time(String start, Restaurant restaurant) {
        this.start = start;
        Integer reservationDuration = restaurant.getRestaurantConfiguration().getDuration();
        LocalTime startTime = LocalTime.parse(start, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime adjustedTime = startTime.plusMinutes(reservationDuration);
        this.end = adjustedTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
