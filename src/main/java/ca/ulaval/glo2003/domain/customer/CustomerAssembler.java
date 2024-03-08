package ca.ulaval.glo2003.domain.customer;

import ca.ulaval.glo2003.controllers.models.CustomerDTO;

public class CustomerAssembler {
    public Customer fromDTO(CustomerDTO customerDTO) {
        return new Customer(customerDTO.name(), customerDTO.email(), customerDTO.phoneNumber());
    }
}
