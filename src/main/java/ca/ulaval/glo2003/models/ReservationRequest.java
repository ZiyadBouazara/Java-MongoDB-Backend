package ca.ulaval.glo2003.models;

import ca.ulaval.glo2003.domain.customer.Customer;

public class ReservationRequest {
    public String date;
    public String startTime;
    public int groupSize;
    public Customer customer;

    public String getDate() {
        return this.date;
    }
    public String getStartTime() {
        return this.startTime;
    }
    public int getGroupSize() {
        return this.groupSize;
    }
    public Customer getCustomer() {
        return this.customer;
    }
}
