package ca.ulaval.glo2003.controllers;

import ca.ulaval.glo2003.controllers.requests.ReviewRequest;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.service.ReviewService;
import ca.ulaval.glo2003.service.dtos.CustomerDTO;
import ca.ulaval.glo2003.controllers.api.JerseyTestApi;
import jakarta.ws.rs.ProcessingException;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ReviewResourceIntegrationTest {
    private final ReviewService reviewServiceMocked = Mockito.mock(ReviewService.class);
    private JerseyTestApi api;
    private final String RESTAURANT_ID = "12345";
    private final String REVIEW_ID = "54321";
    private final ReviewRequest validReviewRequest =
        new ReviewRequest("2024-04-30", new CustomerDTO("John Doe", "john@gmail.com", "5811111111"), 4.5, "Great food!");
    private final ReviewRequest invalidReviewRequest =
        new ReviewRequest(null, new CustomerDTO("John Doe", "john@gmail.com", "5811111111"), 4.5, "Missing date");

    public Application configure() {
        return new ResourceConfig().register(new ReviewResource(reviewServiceMocked));
    }

    @BeforeEach
    public void setUp() throws InvalidParameterException, MissingParameterException {
        api = new JerseyTestApi(configure());
        api.start();
        Mockito.when(reviewServiceMocked.createReview(RESTAURANT_ID, validReviewRequest)).thenReturn(REVIEW_ID);
    }

    @AfterEach
    public void tearDown() {
        api.stop();
    }

    @Test
    public void givenValidReviewRequest_whenCreateReview_shouldReturn201Created() {
        Response response = api.path("/restaurants/" + RESTAURANT_ID + "/reviews")
            .request()
            .post(Entity.json(validReviewRequest));

        assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
    }

    @Test
    public void givenValidReviewRequest_whenCreateReview_shouldReturnLocationHeader() {
        Response response = api.path("/restaurants/" + RESTAURANT_ID + "/reviews")
            .request()
            .post(Entity.json(validReviewRequest));

        assertThat(response.getHeaderString("Location")).contains("/reviews/" + REVIEW_ID);
    }

    @Test
    public void givenInvalidReviewRequest_whenCreateReview_shouldThrowInvalidParameterException() {
        assertThatThrownBy(() -> api.path("/restaurants/" + RESTAURANT_ID + "/reviews")
            .request()
            .post(Entity.json(invalidReviewRequest)))
            .isInstanceOf(ProcessingException.class);
    }

    @Test
    public void givenMissingParameter_whenCreateReview_shouldThrowMissingParameterException() {
        assertThatThrownBy(() -> api.path("/restaurants/" + RESTAURANT_ID + "/reviews")
            .request()
            .post(Entity.json(
                "{\"date\": null, \"customer\": {\"name\": \"John Doe\"}, \"rating\": 4.5, \"comment\": \"Missing date\"}")))
            .isInstanceOf(ProcessingException.class);
    }
}
