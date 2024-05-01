package ca.ulaval.glo2003.controllers.responses;

public record ReviewResponse(
        String restaurantId,
        String date,
        double rating) {
}
