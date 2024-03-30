package ca.ulaval.glo2003.service.dtos;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record TimeDTO(@NotBlank String start, @NotBlank String end) {
    public TimeDTO(String start, Integer reservationDuration) {
        this(start, calculateEndTime(start, reservationDuration));
    }

    private static String calculateEndTime(String start, Integer reservationDuration) {
        LocalTime startTime = LocalTime.parse(start, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime adjustedTime = startTime.plusMinutes(reservationDuration);
        return adjustedTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}


