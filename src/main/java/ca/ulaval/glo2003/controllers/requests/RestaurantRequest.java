package ca.ulaval.glo2003.controllers.requests;


import ca.ulaval.glo2003.service.dtos.HoursDTO;
import ca.ulaval.glo2003.service.dtos.ReservationConfigurationDTO;

public record RestaurantRequest(String name,
                                Integer capacity,
                                HoursDTO hours,
                                ReservationConfigurationDTO reservations) {
}
