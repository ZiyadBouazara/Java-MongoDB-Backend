package ca.ulaval.glo2003.domain.review;

import ca.ulaval.glo2003.domain.customer.Customer;

public class ReviewFactory {
    public Review createReview(String restaurantId,
                               String date,
                               Double rating,
                               String comment,
                               Customer customer) {
        return new Review(restaurantId, date, customer, rating, comment);
    }
}
