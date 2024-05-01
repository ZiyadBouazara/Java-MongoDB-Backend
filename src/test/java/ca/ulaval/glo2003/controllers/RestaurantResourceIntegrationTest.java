package ca.ulaval.glo2003.controllers;

import ca.ulaval.glo2003.controllers.requests.RestaurantRequest;
import ca.ulaval.glo2003.controllers.responses.RestaurantResponse;
import ca.ulaval.glo2003.service.validators.HeaderValidator;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.utils.Hours;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.controllers.api.JerseyTestApi;
import ca.ulaval.glo2003.service.RestaurantService;
import ca.ulaval.glo2003.service.assembler.HoursAssembler;
import ca.ulaval.glo2003.service.dtos.HoursDTO;
import ca.ulaval.glo2003.service.dtos.ReservationConfigurationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.NotFoundException;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class RestaurantResourceIntegrationTest {
    public static final String RESTAURANT_ID = "";
    public static final String OWNER_ID = "1";
    public static final String INVALID_OWNER_ID = "invalid";
    public static final String RESTAURANT_NAME = "Restaurant Name";
    private static final int VALID_RESTAURANTS_CAPACITY = 5;
    private final RestaurantService restaurantServiceMocked = Mockito.mock(RestaurantService.class);
    private final HeaderValidator headerValidatorMocked = Mockito.mock(HeaderValidator.class);
    private final HoursAssembler hoursAssembler = new HoursAssembler();
    private final ObjectMapper mapper = new ObjectMapper();
    private JerseyTestApi api;
    public Restaurant validRestaurant;
    public Restaurant invalidRestaurant;
    public RestaurantResponse expectedResponse;
    public RestaurantRequest restaurantRequest;


    public Application configure() {
        Hours validHours = new Hours("11:00:00", "19:30:00");
        validRestaurant = new Restaurant(OWNER_ID, RESTAURANT_NAME, VALID_RESTAURANTS_CAPACITY, validHours);
        invalidRestaurant = new Restaurant(null, RESTAURANT_NAME, VALID_RESTAURANTS_CAPACITY, validHours);

        expectedResponse = new RestaurantResponse(validRestaurant.getId(),
            OWNER_ID,
            validRestaurant.getName(),
            validRestaurant.getCapacity(),
            hoursAssembler.toDTO(validRestaurant.getHours()),
            new ReservationConfigurationDTO(60));

        restaurantRequest = new RestaurantRequest(
            validRestaurant.getName(),
            validRestaurant.getCapacity(),
            new HoursDTO("11:00:00", "19:30:00"),
            new ReservationConfigurationDTO(60));

        return new ResourceConfig().register(new RestaurantResource(
            restaurantServiceMocked));
    }

    @BeforeEach
    public void setUp() throws InvalidParameterException, MissingParameterException {
        api = new JerseyTestApi(configure());
        api.start();

        List<RestaurantResponse> mockResponse = Collections.singletonList(expectedResponse);
        Mockito.when(restaurantServiceMocked.getRestaurantsForOwnerId(Mockito.anyString())).thenReturn(mockResponse);

        doThrow(MissingParameterException.class).when(headerValidatorMocked).verifyMissingHeader(null);
        when(restaurantServiceMocked.getRestaurant(OWNER_ID, validRestaurant.getId())).thenReturn(expectedResponse);
        when(restaurantServiceMocked.createRestaurant(OWNER_ID,
            restaurantRequest)).thenReturn(validRestaurant.getId());
    }

    @AfterEach
    public void tearDown() {
        api.stop();
    }

    @Test
    public void givenOwnerHasRestaurant_whenGetRestaurants_shouldReturn200() {
        var response = api.path("/restaurants/")
            .request()
            .header("Owner", OWNER_ID)
            .get();


        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void givenOwnerHasRestaurant_whenGetRestaurants_shouldReturnListOfRestaurants() {
        var response = api.path("/restaurants/")
            .request()
            .header("Owner", OWNER_ID)
            .get();

        var body = response.readEntity(String.class);

        assertThat(body).contains("capacity\":5");
    }

    @Test
    public void givenValidRestaurant_whenGetRestaurantWithId_shouldReturn200() {
        var response = api.path("/restaurants/" + validRestaurant.getId())
            .request()
            .header("Owner", OWNER_ID)
            .get();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void givenValidRestaurant_whenGetRestaurantWithId_shouldReturnRestaurant()
        throws NotFoundException, IOException {
        var response = api.path("/restaurants/" + validRestaurant.getId())
            .request()
            .header("Owner", OWNER_ID)
            .get();

        String jsonResponse = response.readEntity(String.class);

        RestaurantResponse responseRestaurant = mapper.readValue(jsonResponse, RestaurantResponse.class);

        String nameValue = responseRestaurant.name();

        Assertions.assertEquals("Restaurant Name", nameValue);
    }

    @Test
    public void givenNonExistentRestaurantId_whenGetRestaurantWithId_shouldReturn404() {
        assertThatThrownBy(() -> api.path("/restaurants/{id}/").resolveTemplate("id", RESTAURANT_ID)
            .request()
            .header("Owner", INVALID_OWNER_ID)
            .get(String.class))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining("Not Found");
    }

    @Test
    public void givenValidRestaurant_whenCreateRestaurant_shouldReturn201Created() {
        Response response = api.path("/restaurants/")
            .request()
            .header("Owner", OWNER_ID)
            .post(Entity.json(restaurantRequest));


        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus(), "Http Response should be 201 ");
    }

    @Test
    public void givenValidRestaurant_whenCreateRestaurant_shouldReturnLocationHeader_ContainingRestaurantId() {
        Response response = api.path("/restaurants/")
            .request()
            .header("Owner", OWNER_ID)
            .post(Entity.json(restaurantRequest));


        assertTrue(response.getHeaderString("Location").contains(validRestaurant.getId()));
    }

    @Test
    public void givenMissingParameter_whenCreateRestaurant_shouldThrowMissingParameterException() {
        assertThatThrownBy(() -> api.path("/restaurants/")
            .request()
            .header("Owner", OWNER_ID)
            .post(Entity.json("{\"name\":\"La Botega\", \"hours\":{\"open\":\"11:00:00\", \"close\":\"19:00:00\"}}")))
            .isInstanceOf(ProcessingException.class);
    }

    @Test
    public void givenMissingOwnerHeader_whenCreateRestaurant_shouldReturnBadRequest() {
        assertThatThrownBy(() -> api.path("/restaurants/")
            .request()
            .post(
                Entity.json(
                    "{\"name\":\"La Botega\", \"capacity\":\"5\",\"hours\":{\"open\":\"11:00:00\", \"close\":\"19:00:00\"}}")))
            .isInstanceOf(ProcessingException.class);
    }

    @Test
    public void givenInvalidParameter_whenCreateRestaurant_shouldThrowInvalidParameterException() {
        assertThatThrownBy(() -> api.path("/restaurants")
            .request()
            .header("Owner", OWNER_ID)
            .post(
                Entity.json(
                    "{\"name\":\"La Botega\", \"capacity\":\"0\",\"hours\":{\"open\":\"11:00:00\", \"close\":\"19:00:00\"}}")))
            .isInstanceOf(ProcessingException.class)
            .hasMessageContaining("Server-side request processing failed with an error.");
    }

}
