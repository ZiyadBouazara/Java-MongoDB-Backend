package ca.ulaval.glo2003.controllers.requests;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReviewSearchRequest(
        @NotNull
        String restaurantId,
        @NotNull
        String rating,
        @NotNull
        String date
){}
