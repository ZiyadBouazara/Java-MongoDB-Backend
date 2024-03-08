package ca.ulaval.glo2003.controllers.requests;

import ca.ulaval.glo2003.service.dtos.CustomerDTO;

public record ReservationRequest(
        String date,
        String startTime,
        Integer groupSize,
        CustomerDTO customer) {
}
