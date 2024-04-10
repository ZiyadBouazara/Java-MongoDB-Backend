package ca.ulaval.glo2003.service.validators;

import ca.ulaval.glo2003.controllers.requests.RestaurantRequest;
import ca.ulaval.glo2003.service.dtos.HoursDTO;
import ca.ulaval.glo2003.service.dtos.ReservationConfigurationDTO;

public class RestaurantRequestFixture {
    private String name = "name";
    private int capacity = 5;
    private HoursDTO hours = initValidHours();
    private ReservationConfigurationDTO reservationConfiguration = new ReservationConfigurationDTO(60);

    private HoursDTO initValidHours() {
        return new HoursDTO("11:00:00", "19:30:00");
    }

    public RestaurantRequest create() {
        return new RestaurantRequest(name, capacity, hours, reservationConfiguration);
    }

    public RestaurantRequest createWithMissingName() {
        return new RestaurantRequest(null, capacity, hours, reservationConfiguration);
    }

    public RestaurantRequest createWithMissingCapacity() {
        return new RestaurantRequest(name, null, hours, reservationConfiguration);
    }

    public RestaurantRequest createWithMissingHours() {
        return new RestaurantRequest(name, capacity, null, reservationConfiguration);

    }

    public RestaurantRequestFixture withName(String name) {
        this.name = name;
        return this;
    }

    public RestaurantRequestFixture withCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public RestaurantRequestFixture withHours(HoursDTO hours) {
        this.hours = hours;
        return this;
    }

}

