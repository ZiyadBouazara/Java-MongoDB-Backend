package ca.ulaval.glo2003.controllers.models;

import ca.ulaval.glo2003.domain.customer.Customer;


public record ReservationRequest(
        String restaurantId,
        String date,
        String startTime,
        Integer groupSize,
        Customer customer) {
}
