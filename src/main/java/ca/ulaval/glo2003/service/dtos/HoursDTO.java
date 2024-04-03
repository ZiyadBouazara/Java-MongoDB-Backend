package ca.ulaval.glo2003.service.dtos;

import jakarta.validation.constraints.NotBlank;

public record HoursDTO(@NotBlank String open,
                       @NotBlank String close) {

}

