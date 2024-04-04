package ca.ulaval.glo2003.controllers.requests;

import ca.ulaval.glo2003.service.dtos.CustomerDTO;
import jakarta.validation.constraints.NotNull;

public record ReservationRequest(
        @NotNull
        String date,
        @NotNull
        String startTime,
        @NotNull
        Integer groupSize,
        @NotNull
        CustomerDTO customer) {
}
