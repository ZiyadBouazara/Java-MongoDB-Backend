package ca.ulaval.glo2003.controllers;

import ca.ulaval.glo2003.Main;
import ca.ulaval.glo2003.controllers.requests.ReviewRequest;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.service.ReviewService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;

@Path("/")
public class ReviewResource {
    private final ReviewService reviewService;
    @Inject
    public ReviewResource(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @POST
    @Path("restaurants/{id}/reviews")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReview(@PathParam("id") String restaurantId, ReviewRequest reviewRequest)
            throws InvalidParameterException, MissingParameterException {

        String createdReviewId = reviewService.createReview(
                restaurantId,
                reviewRequest);

        URI newProductURI = UriBuilder.fromPath(Main.BASE_URI)
                .path("reviews")
                .path(createdReviewId)
                .build();
        return Response.created(newProductURI).build();
    }
}
