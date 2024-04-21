package ca.ulaval.glo2003.controllers.requests;

import ca.ulaval.glo2003.service.dtos.CustomerDTO;

public record ReviewRequest(
        String date,
        CustomerDTO customer,
        double rating,
        String comment) {

}
