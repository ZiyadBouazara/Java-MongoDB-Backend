package ca.ulaval.glo2003.domain.review;

import ca.ulaval.glo2003.domain.customer.Customer;

import java.util.UUID;

public class Review {

    private String restaurantId;
    private String id;
    private String date;
    private Customer customer;
    private double rating;
    private String comment;

    public Review(String restaurantId, String id, String date, Customer customer, double rating, String comment) {
        this.restaurantId = restaurantId;
        this.id = id;
        this.date = date;
        this.customer = customer;
        this.rating = rating;
        this.comment = comment;
    }

    public Review(String restaurantId, String date, Customer customer, double rating, String comment) {
        this.restaurantId = restaurantId;
        this.id = UUID.randomUUID().toString();
        this.date = date;
        this.customer = customer;
        this.rating = rating;
        this.comment = comment;
    }
    public String getRestaurantId() {
        return restaurantId;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
