package ca.ulaval.glo2003.domain.review;

import ca.ulaval.glo2003.domain.customer.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Review {

    private LocalDateTime postedDate;
    private String restaurantId;
    private String id;
    private String date;
    private Customer customer;
    private double rating;
    private String comment;

    public Review(String restaurantId, String date, Customer customer, Double rating, String comment) {
        this.restaurantId = restaurantId;
        this.id = UUID.randomUUID().toString();
        this.date = date;
        this.postedDate = LocalDateTime.now();
        this.customer = customer;
        this.rating = rating;
        this.comment = comment;
    }

    public Review(String restaurantId, double rating) {
        this.restaurantId = restaurantId;
        this.id = UUID.randomUUID().toString();
        this.postedDate = LocalDateTime.now();
        this.rating = rating;
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

    public LocalDateTime getPostedDate() {
        return postedDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Double getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
