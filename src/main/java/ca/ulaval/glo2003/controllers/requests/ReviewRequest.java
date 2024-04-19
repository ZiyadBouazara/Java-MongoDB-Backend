package ca.ulaval.glo2003.controllers.requests;

import ca.ulaval.glo2003.domain.customer.Customer;

public record ReviewRequest(
        String restaurantId,
        String id,
        String date,
        Customer customer,
        Integer rating,
        String comment) {

}
