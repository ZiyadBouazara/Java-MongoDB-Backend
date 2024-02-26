package ca.ulaval.glo2003.controllers.models;

public record ReservationRequest(
        String date,
        String startTime,
        Integer groupSize,
        CustomerDTO customer) {
}
