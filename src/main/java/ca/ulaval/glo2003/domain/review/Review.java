package ca.ulaval.glo2003.domain.review;

import ca.ulaval.glo2003.domain.customer.Customer;

public class Review {

    private String restaurantId;
    private String id;
    private String date;
    private Customer customer;
    private Integer rating;
    private String comment;

    public Review(String restaurantId, String id, String date, Customer customer, Integer rating, String comment) {
        this.restaurantId = restaurantId;
        this.id = id;
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

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
