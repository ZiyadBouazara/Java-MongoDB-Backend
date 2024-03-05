package ca.ulaval.glo2003.model.fixture;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.models.ReservationRequest;

public class ReservationRequestFixture {

    private String date = "2024-03-05";
    private String startTime = "12:00:00";
    private Integer groupSize = 4;
    private Customer customer = new Customer();

    public ReservationRequest create() {
        ReservationRequest reservationRequest = new ReservationRequest();
        this.customer = createValidCustomer();
        reservationRequest.setDate(date);
        reservationRequest.setStartTime(startTime);
        reservationRequest.setGroupSize(groupSize);
        reservationRequest.setCustomer(customer);
        return reservationRequest;
    }

    public ReservationRequest createWithMissingDate() {
        ReservationRequest reservationRequest = create();
        reservationRequest.setDate(null);
        return reservationRequest;
    }

    public ReservationRequest createWithMissingStartTime() {
        ReservationRequest reservationRequest = create();
        reservationRequest.setStartTime(null);
        return reservationRequest;
    }

    public ReservationRequest createWithMissingGroupSize() {
        ReservationRequest reservationRequest = create();
        reservationRequest.setGroupSize(null);
        return reservationRequest;
    }

    public ReservationRequest createWithMissingCustomer() {
        ReservationRequest reservationRequest = create();
        reservationRequest.setCustomer(null);
        return reservationRequest;
    }

    private Customer createValidCustomer() {
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhoneNumber("1234567890");
        return customer;
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

    public ReservationRequestFixture withCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }
}