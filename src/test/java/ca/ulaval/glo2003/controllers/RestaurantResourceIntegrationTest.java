package ca.ulaval.glo2003.controllers;

import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.exceptions.mapper.InvalidParamExceptionMapper;
import ca.ulaval.glo2003.domain.exceptions.mapper.MissingParamExceptionMapper;
import ca.ulaval.glo2003.domain.exceptions.mapper.NotFoundExceptionMapper;
import ca.ulaval.glo2003.domain.factories.RestaurantFactory;
import ca.ulaval.glo2003.domain.restaurant.ReservationConfiguration;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.utils.Hours;
import ca.ulaval.glo2003.domain.utils.ResourcesHandler;
import ca.ulaval.glo2003.models.RestaurantResponse;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import javassist.NotFoundException;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

//@RunWith(JerseyTestRunner.class)
public class RestaurantResourceIntegrationTest extends JerseyTest {

    private static final String RESTAURANT_ID = "1";
    private static final String OWNER_ID = "1";
    private static final String RESTAURANT_NAME = "Restaurant Name";
    private static final int EXPECTED_OWNER_RESTAURANTS_COUNT = 1;
    private Restaurant restaurant;
    private ResourcesHandler resourcesHandler = new ResourcesHandler();
    private RestaurantFactory restaurantFactory = mock(RestaurantFactory.class);
    private RestaurantResource restaurantResource;

    @Override
    protected Application configure() {
        return new ResourceConfig()
                .register(new RestaurantResource())
                .register(new InvalidParamExceptionMapper())
                .register(new MissingParamExceptionMapper())
                .register(new NotFoundExceptionMapper());
    }

//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        when(restaurant.getId()).thenReturn(RESTAURANT_ID);
//        when(restaurant.getOwnerId()).thenReturn(OWNER_ID);
//        when(restaurant.getName()).thenReturn(RESTAURANT_NAME);
//
////        resourcesHandler = new ResourcesHandler();
//    }

    @Test
    public void getRestaurantsList_WhenStatusIsOk_returnsStatusOkWith200() {
        var response = target("/restaurants/")
                .request()
                .header("Owner", OWNER_ID)
                .get();

        var body = response.readEntity(String.class);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(body).contains("[]");
    }

    @Test
    public void getRestaurantsList_WhenStatusIsNOTOk_returnsStatusOkWith500() {
        var response = target("/restaurants/")
                .request()
                .header("Owner", OWNER_ID)
                .get();

        var body = response.readEntity(String.class);

        assertThat(response.getStatus()).isEqualTo(500);
        assertThat(body).contains("[]");
    }

    @Test
    public void whenValidRestaurantId_returnsRestaurantResponse() {
        Restaurant restaurant = getRestaurant();

        resourcesHandler.addRestaurant(restaurant);
        Restaurant addedRestaurant = resourcesHandler.getRestaurant(restaurant.getId());
        System.out.println(restaurant.getId());
//        when(resourcesHandler.getRestaurant(restaurant.getId())).thenReturn(restaurant);

        var response = target("/restaurants/" + restaurant.getId())
                .request()
                .header("Owner", OWNER_ID)
                .get();

        System.out.println(response);

        assertThat(response.getStatus()).isEqualTo(200);

    }

    private static Restaurant getRestaurant() {
        Hours restaurantHours = new Hours();
        restaurantHours.setOpen("12:00:00");
        restaurantHours.setClose("22:00:00");

        // Create a real instance of ReservationConfiguration
        ReservationConfiguration reservationConfiguration = new ReservationConfiguration(2);

        // Create a real instance of Restaurant
        Restaurant restaurant = new Restaurant(
                OWNER_ID,
                RESTAURANT_NAME,
                50,
                restaurantHours,
                reservationConfiguration
        );
        return restaurant;
    }

    //        assertThat(response.getEntity()).isInstanceOf(RestaurantResponse.class);
//
//        RestaurantResponse restaurantResponse = response.readEntity(RestaurantResponse.class);
//        assertThat(restaurantResponse.id).isEqualTo(RESTAURANT_ID);
//        assertThat(restaurantResponse.name).isEqualTo(restaurant.getName());

    @Test
    public void testGetRestaurant() throws MissingParameterException {
        // Arrange
        String ownerId = "owner123";
        String restaurantId = "restaurant123";
        Restaurant mockedRestaurant = mock(Restaurant.class);

        // Create an instance of RestaurantResource
        RestaurantResource restaurantResource = new RestaurantResource();

        // Assume your get method is in ResourcesHandler and returns the restaurant
        when(resourcesHandler.getRestaurant(restaurantId)).thenReturn(mockedRestaurant);

        // Act
        Response response = restaurantResource.getRestaurant(ownerId, restaurantId);

        // Assert
        // Add your assertions here based on the expected behavior

        // Verify that the getRestaurant method is called with the expected parameters
        verify(resourcesHandler).getRestaurant(restaurantId);
    }

}
