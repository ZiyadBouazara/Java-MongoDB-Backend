package ca.ulaval.glo2003.controllers.responses;

import ca.ulaval.glo2003.domain.review.Review;

public record ReviewResponse(
        String restaurantId,
        String date,
        double rating
) {

    public String getRestaurantId(){
        return restaurantId;
    }

    public String getDate(){
        return date;
    }

    public double getRating(){
        return rating;
    }
}
