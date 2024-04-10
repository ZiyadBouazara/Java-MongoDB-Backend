package ca.ulaval.glo2003.controllers.api.fixture;

import ca.ulaval.glo2003.controllers.requests.ReservationRequest;
import ca.ulaval.glo2003.service.dtos.CustomerDTO;

public class ReservationRequestFixture {

    private String date = "2024-03-05";
    private String startTime = "12:00:00";
    private Integer groupSize = 4;
    private CustomerDTO customer;
    private String customerName = "John Doe";
    private String customerEmail = "john.doe@example.com";
    private String customerPhoneNumber = "1234567890";

    public ReservationRequest create() {
        this.customer = new CustomerDTO(customerName, customerEmail, customerPhoneNumber);
        return new ReservationRequest(date, startTime, groupSize, customer);
    }

    public ReservationRequest createWithMissingDate() {
        return new ReservationRequest(null, startTime, groupSize, customer);
    }

    public ReservationRequest createWithMissingStartTime() {
        return new ReservationRequest(date, null, groupSize, customer);
    }

    public ReservationRequest createWithMissingGroupSize() {
        return new ReservationRequest(date, startTime, null, customer);
    }

    public ReservationRequest createWithMissingCustomer() {
        return new ReservationRequest(date, startTime, groupSize, null);
    }

    public ReservationRequestFixture withDate(String date) {
        this.date = date;
        return this;
    }

    public ReservationRequestFixture withStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public ReservationRequestFixture withGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
        return this;
    }

    public ReservationRequestFixture withCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public ReservationRequestFixture withCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public ReservationRequestFixture withCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
        return this;
    }
}
