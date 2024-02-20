package ca.ulaval.glo2003.controllers.models;


public record RestaurantRequest(String name,
                                Integer capacity,
                                HoursDTO hours,
                                ReservationConfigurationDTO reservations) {
}
