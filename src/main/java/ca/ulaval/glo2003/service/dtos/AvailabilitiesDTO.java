package ca.ulaval.glo2003.service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AvailabilitiesDTO(
        @NotBlank
        String date,
        @NotNull
        Integer remainingPlace) {
}
