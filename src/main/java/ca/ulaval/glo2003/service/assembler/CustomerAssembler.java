package ca.ulaval.glo2003.service.assembler;

import ca.ulaval.glo2003.service.dtos.CustomerDTO;
import ca.ulaval.glo2003.domain.customer.Customer;

public class CustomerAssembler {
    public Customer fromDTO(CustomerDTO customerDTO) {
        return new Customer(customerDTO.name(), customerDTO.email(), customerDTO.phoneNumber());
    }
}
