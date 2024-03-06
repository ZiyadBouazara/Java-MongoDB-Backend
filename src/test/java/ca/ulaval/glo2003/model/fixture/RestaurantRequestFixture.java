package ca.ulaval.glo2003.model.fixture;

import ca.ulaval.glo2003.domain.utils.Hours;
import ca.ulaval.glo2003.models.RestaurantRequest;

public class RestaurantRequestFixture {
    private String name = "name";
    private int capacity = 5;
    private final Hours hours = initValidHours();
    private Integer nameInteger;

    private Hours initValidHours() {
        Hours validHours = new Hours();
        validHours.setOpen("11:00:00");
        validHours.setClose("19:30:00");
        return validHours;
    }

    public RestaurantRequest create() {
        RestaurantRequest restaurantRequest = new RestaurantRequest();
        restaurantRequest.setName(name);
        restaurantRequest.setCapacity(capacity);
        restaurantRequest.setHours(hours);
        return restaurantRequest;
    }

    public RestaurantRequest createWithMissingName() {
        RestaurantRequest restaurantRequest = new RestaurantRequest();
        restaurantRequest.setCapacity(capacity);
        restaurantRequest.setHours(hours);
        return restaurantRequest;
    }

    public RestaurantRequest createWithMissingCapacity() {
        RestaurantRequest restaurantRequest = new RestaurantRequest();
        restaurantRequest.setName(name);
        restaurantRequest.setHours(hours);
        return restaurantRequest;
    }

    public RestaurantRequest createWithMissingHours() {
        RestaurantRequest restaurantRequest = new RestaurantRequest();
        restaurantRequest.setName(name);
        restaurantRequest.setCapacity(capacity);
        return restaurantRequest;
    }

    public RestaurantRequestFixture withName(String name) {
        this.name = name;
        return this;
    }

    public RestaurantRequestFixture withNameInteger(Integer name) {
        this.nameInteger = name;
        return this;
    }

    public RestaurantRequestFixture withCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public RestaurantRequestFixture withOpenHour(String openHour) {
        this.hours.setOpen(openHour);
        return this;
    }

    public RestaurantRequestFixture withCloseHour(String closeHour) {
        this.hours.setClose(closeHour);
        return this;
    }

}
