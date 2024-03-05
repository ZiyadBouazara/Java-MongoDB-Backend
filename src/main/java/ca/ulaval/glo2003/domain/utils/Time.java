package ca.ulaval.glo2003.domain.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Time {
    public String start;
    public String end;

    public Time(String start) {
        this.start = start;
        LocalTime startTime = LocalTime.parse(start, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime adjustedTime = startTime.plusHours(1);
        this.end = adjustedTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
