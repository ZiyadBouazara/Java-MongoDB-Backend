package ca.ulaval.glo2003.controllers;

import ca.ulaval.glo2003.domain.exceptions.mapper.InvalidParamExceptionMapper;
import ca.ulaval.glo2003.domain.exceptions.mapper.MissingParamExceptionMapper;
import ca.ulaval.glo2003.domain.exceptions.mapper.NotFoundExceptionMapper;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.utils.Hours;
import ca.ulaval.glo2003.domain.utils.ResourcesHandler;
import ca.ulaval.glo2003.model.RestaurantRequestFixture;
import ca.ulaval.glo2003.models.RestaurantRequest;
import ca.ulaval.glo2003.models.RestaurantResponse;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.NotFoundException;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class RestaurantResourceIntegrationTest extends JerseyTest {
    public static final String RESTAURANT_ID = "";
    public static final String OWNER_ID = "1";
    public static final String INVALID_OWNER_ID = "invalid";

    public static final String RESTAURANT_NAME = "Restaurant Name";
    private static final int VALID_RESTAURANTS_CAPACITY = 5;
    public Restaurant validRestaurant;
    public Restaurant invalidRestaurant;

    ResourcesHandler resourcesHandler;
    @Override
    protected Application configure() {
        Hours validHours = new Hours();
        validHours.setOpen("11:00:00");
        validHours.setClose("19:30:00");
        validRestaurant = new Restaurant(OWNER_ID, RESTAURANT_NAME, VALID_RESTAURANTS_CAPACITY, validHours);
        resourcesHandler = new ResourcesHandler();
        resourcesHandler.addRestaurant(validRestaurant);


        return new ResourceConfig()
                .register(new RestaurantResource(resourcesHandler))
                .register(InvalidParamExceptionMapper.class)
                .register(MissingParamExceptionMapper.class)
                .register(NotFoundExceptionMapper.class);
    }

    @Override
    protected URI getBaseUri() {
        return UriBuilder.fromUri(super.getBaseUri()).port(8080).build();
    }

    @Test
    public void givenOwnerHasRestaurant_whenGetRestaurant_shouldReturn200AndListOfRestaurants() {

        var response = target("/restaurants/")
                .request()
                .header("Owner", OWNER_ID)
                .get();

        var body = response.readEntity(String.class);


        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(body).contains("capacity\":5");
    }

    @Test
    public void whenGivenRestaurantWithMissingParameter_shouldReturnsStatusNotOk400_andThrowException() {
        var response = target("/restaurants/")
                .request()
                .get();

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.readEntity(String.class)).contains("Missing 'Owner' header");
    }

    @Test
    public void whenGivenValidRestaurantId_shouldGetRestaurant_andReturnValid200GetResponse() {
        var response = target("/restaurants/{id}/")
                .resolveTemplate("id", validRestaurant.getId())
                .request()
                .header("Owner", OWNER_ID)
                .get();

        assertThat(response.getStatus()).isEqualTo(200);

        RestaurantResponse responseRestaurant = response.readEntity(RestaurantResponse.class);

        assertThat(responseRestaurant.getName()).isEqualTo("Restaurant Name");
    }

    @Test
    public void whenGivenMissingOwnerHeader_shouldReturnInvalid400GetResponse_andThrowMissingParameterException() {
        var response = target("/restaurants/{id}/")
                .resolveTemplate("id", validRestaurant.getId())
                .request()
                .get();

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.readEntity(String.class)).contains("Missing 'Owner' header");
    }

    @Test
    public void whenGivenInexistentRestaurantID_shouldReturnInvalid404GetResponse() {
        var response = target("/restaurants/{id}/")
                .resolveTemplate("id", RESTAURANT_ID)
                .request()
                .header("Owner", OWNER_ID)
                .get();

        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    public void whenGivenInvalidOwner_shouldReturnInvalid404GetResponse_andThrowNotFoundException() {
        assertThatThrownBy(() -> {
            target("/restaurants/{id}/")
                    .resolveTemplate("id", validRestaurant.getId())
                    .request()
                    .header("Owner", INVALID_OWNER_ID)
                    .get(String.class);
        }).isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Not Found")
                .hasMessageContaining("404");

    }


    @Test
    public void givenValidRestaurant_whenCreateRestaurant_shouldReturn201CreatedAndLocationHeader() {
        Response response = target("/restaurants/")
                .request()
                .header("Owner", OWNER_ID)
                .post(Entity.json("{\"name\":\"La Botega\",\"capacity\":12, \"hours\":{\"open\":\"11:00:00\", \"close\":\"19:00:00\"}}"));

        assertEquals("Http Response should be 201 ", Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertTrue(response.getHeaderString("Location").contains("/restaurants/"));
    }

    @Test
    public void givenMissingHeaderOwner_shouldThrowMissingParameterException() {
        RestaurantRequest validRestaurantRequest = new RestaurantRequestFixture().create();

        Response response = target("/restaurants").request().post(Entity.entity(validRestaurantRequest, MediaType.APPLICATION_JSON));

        String responseBody = response.readEntity(String.class);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertTrue(responseBody.contains("Missing 'Owner' header"));
        assertTrue(responseBody.contains("MISSING_PARAMETER"));
    }


    @Test
    public void givenMissingParameter_whenCreateRestaurant_shouldThrowMissingParameterException() {
        Response response = target("/restaurants/")
                .request()
                .header("Owner", OWNER_ID)
                .post(Entity.json("{\"name\":\"La Botega\", \"hours\":{\"open\":\"11:00:00\", \"close\":\"19:00:00\"}}"));

        assertEquals("Http Response should be 400 ", 400, response.getStatus());
        assertThat(response.readEntity(String.class)).contains("Missing parameter 'capacity'");
    }

    @Test
    public void givenInvalidParameter_whenCreateRestaurant_shouldThrowInvalidParameterException() {
        Response response = target("/restaurants/")
                .request()
                .header("Owner", OWNER_ID)
                .post(Entity.json("{\"name\":\"\", \"capacity\":12, \"hours\":{\"open\":\"11:00:00\", \"close\":\"19:00:00\"}}"));

        assertEquals("Http Response should be 400 ", 400, response.getStatus());
        assertThat(response.readEntity(String.class)).contains("{\"description\":\"Invalid parameter 'name', cant be blank\",\"error\":\"INVALID_PARAMETER\"}");
    }
}



