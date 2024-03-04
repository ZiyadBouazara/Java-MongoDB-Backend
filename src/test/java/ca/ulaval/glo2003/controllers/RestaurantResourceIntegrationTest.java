package ca.ulaval.glo2003.controllers;//package ca.ulaval.glo2003.controllers;

import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.exceptions.mapper.InvalidParamExceptionMapper;
import ca.ulaval.glo2003.domain.exceptions.mapper.MissingParamExceptionMapper;
import ca.ulaval.glo2003.domain.exceptions.mapper.NotFoundExceptionMapper;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.ReservationConfiguration;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.utils.Hours;
import ca.ulaval.glo2003.domain.utils.ResourcesHandler;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import javassist.NotFoundException;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantResourceIntegrationTest extends JerseyTest {
    private static final String RESTAURANT_ID = "1";
    private static final String OWNER_ID = "1";
    private static final String RESTAURANT_NAME = "Restaurant Name";
    @Mock
    private ReservationConfiguration mockReservationConfiguration;
    @Mock
    private Reservation mockReservation;
    @Mock
    private Hours mockHours;
    private ResourcesHandler resourcesHandler = new ResourcesHandler();
//    ResourcesHandler resourcesHandler = Mockito.mock(ResourcesHandler.class);
    private Restaurant restaurant;
    protected ResourceConfig configure() {
        return new ResourceConfig()
                .register(new RestaurantResource(resourcesHandler))
                .register(new InvalidParamExceptionMapper())
                .register(new MissingParamExceptionMapper())
                .register(new NotFoundExceptionMapper());
    }

    @Override
    protected URI getBaseUri() {
        return UriBuilder.fromUri(super.getBaseUri()).port(8080).build();
    }

    @Test
    public void getRestaurantsList_WhenStatusIsOk_returnsStatusOkWith200() {
//        mockingRestaurantResponse();
        when(resourcesHandler.getRestaurant(anyString())).thenReturn(restaurant);

        var response = target("/restaurants/")
                .request()
                .header("Owner", OWNER_ID)
                .get();

        var body = response.readEntity(String.class);


        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(body).contains("[]");
    }

    @Test
    public void getRestaurantsList_WhenStatusIsNOTOk_returnsStatusOkWith400() throws MissingParameterException {
        when(resourcesHandler.getRestaurant(RESTAURANT_ID)).thenThrow(new MissingParameterException("Invalid parameter 'name', can't be blank"));

        var response = target("/restaurants/")
                .request()
                .header("Owner", OWNER_ID)
                .get();

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.readEntity(String.class)).contains("Invalid parameter 'name', can't be blank");
    }

    @Test
    public void whenValidRestaurantId_returnsRestaurantResponse() {
        Restaurant restaurant = persoBeforEach();
//        givenRestoSuccess(restaurant);

        System.out.println(restaurant+"lolllllll");
        System.out.println(restaurant.getId()+"lolllllll");

//        Mockito.when(resourcesHandler.getRestaurant(restaurant.getId())).thenReturn(this.restaurant);


        System.out.println("Before making request to /restaurants/" + restaurant.getId());

        Response response = target("/restaurants/" + restaurant.getId())
                .request()
                .header("Owner", OWNER_ID)
                .get();

        System.out.println("After making request to /restaurants/" + restaurant.getId() + ", response status: " + response.getStatus());

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void givenRestaurantExists_whenSearching_thenResponseContainsRestaurantEntity() {
        Mockito.when(resourcesHandler.getRestaurant(anyString())).thenThrow(new Exception());

        var response = target("/restaurants/{id}/")
                .resolveTemplate("id", RESTAURANT_ID)
                .request()
                .header("Owner", OWNER_ID)
                .get();
        var body = response.readEntity(String.class);

        assertThat(response.getStatus()).isEqualTo(404);
        assertThat(body).contains("Not Found");
    }

    @Test
    public void postRestaurantTest() {
        Response response = target("/restaurants/")
                .request()
                .header("Owner", OWNER_ID)
                .post(Entity.json("{\"name\":\"La Botega\",\"capacity\":12, \"hours\":{\"open\":\"11:00:00\", \"close\":\"19:00:00\"}}"));

        assertEquals("Http Response should be 201 ", Response.Status.CREATED.getStatusCode(), response.getStatus());
    }



    @Test
    public void postRestaurantTestFailedMissingParam() {
        Response response = target("/restaurants/")
                .request()
                .header("Owner", OWNER_ID)
                .post(Entity.json("{\"name\":\"La Botega\", \"hours\":{\"open\":\"11:00:00\", \"close\":\"19:00:00\"}}"));

        assertEquals("Http Response should be 400 ", 400, response.getStatus());
        assertThat(response.readEntity(String.class)).contains("Missing parameter 'capacity'");
    }

    @Test
    public void postRestaurantTestFailedInvalidParam() {
        Response response = target("/restaurants/")
                .request()
                .header("Owner", OWNER_ID)
                .post(Entity.json("{\"name\":\"\", \"capacity\":12, \"hours\":{\"open\":\"11:00:00\", \"close\":\"19:00:00\"}}"));

        assertEquals("Http Response should be 400 ", 400, response.getStatus());
        assertThat(response.readEntity(String.class)).contains("{\"description\":\"Invalid parameter 'name', cant be blank\",\"error\":\"INVALID_PARAMETER\"}");
    }

    private void givenRestoSuccess(Restaurant restaurant) {
        when(resourcesHandler.getRestaurant(restaurant.getId())).thenReturn(restaurant);
        this.resourcesHandler.addRestaurant(restaurant);
        System.out.println(this.resourcesHandler.getRestaurant(this.restaurant.getId()) + "zoobooboobobo");
    }

    private void givenRestoNotSuccess(Restaurant restaurant) {
        Mockito.when(resourcesHandler.getRestaurant(anyString())).thenThrow(new RuntimeException());
    }

    public Restaurant persoBeforEach() {
        MockitoAnnotations.openMocks(this);
        restaurant = new Restaurant("ownerId", "Restaurant Name", 100, mockHours, mockReservationConfiguration);

        return restaurant;
    }
}



