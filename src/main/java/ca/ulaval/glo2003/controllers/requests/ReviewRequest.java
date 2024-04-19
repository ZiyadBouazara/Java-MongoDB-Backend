package ca.ulaval.glo2003.controllers.requests;

import ca.ulaval.glo2003.service.dtos.CustomerDTO;

public record ReviewRequest(
        String date,
        CustomerDTO customer,
        Integer rating,
        String comment) {

}
