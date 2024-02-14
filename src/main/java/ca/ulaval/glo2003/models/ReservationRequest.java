package ca.ulaval.glo2003.models;

import ca.ulaval.glo2003.domain.Customer;

public class ReservationRequest {
    private String restaurantId;
    private String date;
    private String startTime;
    private int groupSize;
    private Customer customer;

    public String getRestaurantId() {
        return this.restaurantId;
    }
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
