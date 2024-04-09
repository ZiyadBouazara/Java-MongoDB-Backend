package ca.ulaval.glo2003.domain.reservation;

import ca.ulaval.glo2003.domain.customer.Customer;

import java.util.UUID;

public class Reservation {
    private String restaurantId;
    private String id;
    private String date;
    private String startTime;
    private int groupSize;
    private Customer customer;

    public Reservation(String restaurantId, String date, String startTime, int groupSize, Customer customer) {
        this.restaurantId = restaurantId;
        this.id = UUID.randomUUID().toString();
        this.date = date;
        this.startTime = startTime;
        this.groupSize = groupSize;
        this.customer = customer;
    }

    public Reservation(String id, String restaurantId, String date, String startTime, int groupSize, Customer customer) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.date = date;
        this.startTime = startTime;
        this.groupSize = groupSize;
        this.customer = customer;
    }

    public String getRestaurantId() {
        return this.restaurantId;
    }

    public String getId() {
        return this.id;
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
