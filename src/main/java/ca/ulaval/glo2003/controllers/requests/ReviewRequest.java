package ca.ulaval.glo2003.controllers.requests;

import ca.ulaval.glo2003.service.dtos.CustomerDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewRequest(
        @NotBlank
        String date,
        @NotNull
        CustomerDTO customer,
        @NotNull
        Double rating,
        String comment) {

}
