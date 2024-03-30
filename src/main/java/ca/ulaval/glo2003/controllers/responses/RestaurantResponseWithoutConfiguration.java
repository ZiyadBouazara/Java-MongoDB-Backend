package ca.ulaval.glo2003.controllers.responses;

import ca.ulaval.glo2003.service.dtos.HoursDTO;

public record RestaurantResponseWithoutConfiguration(String id,
                                                     String ownerId,
                                                     String name,
                                                     Integer capacity,
                                                     HoursDTO hours) {
}
