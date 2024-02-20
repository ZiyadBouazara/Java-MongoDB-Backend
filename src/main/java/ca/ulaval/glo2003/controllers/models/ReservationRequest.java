package ca.ulaval.glo2003.controllers.models;

public record ReservationRequest(
        String restaurantId,
        String date,
        String startTime,
        Integer groupSize,
        CustomerDTO customer) {
}
