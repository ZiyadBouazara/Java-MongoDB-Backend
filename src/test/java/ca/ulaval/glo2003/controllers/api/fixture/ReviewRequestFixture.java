package ca.ulaval.glo2003.controllers.api.fixture;

import ca.ulaval.glo2003.controllers.requests.ReviewRequest;
import ca.ulaval.glo2003.service.dtos.CustomerDTO;

public class ReviewRequestFixture {
    private String date = "2024-04-30";
    private CustomerDTO customer = new CustomerDTO("John Doe", "john@gmail.com", "5811111111");
    private Double rating = 4.5;
    private String comment = "Great product!";

    public ReviewRequest create() {
        return new ReviewRequest(date, customer, rating, comment);
    }

    public ReviewRequest createWithMissingDate() {
        return new ReviewRequest(null, customer, rating, comment);
    }

    public ReviewRequest createWithMissingCustomer() {
        return new ReviewRequest(date, null, rating, comment);
    }

    public ReviewRequest createWithMissingRating() {
        return new ReviewRequest(date, customer, null, comment);
    }

    public ReviewRequestFixture withDate(String date) {
        this.date = date;
        return this;
    }

    public ReviewRequestFixture withCustomer(CustomerDTO customer) {
        this.customer = customer;
        return this;
    }

    public ReviewRequestFixture withRating(Double rating) {
        this.rating = rating;
        return this;
    }
}

