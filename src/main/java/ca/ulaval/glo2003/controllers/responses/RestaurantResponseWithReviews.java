package ca.ulaval.glo2003.controllers.responses;

import ca.ulaval.glo2003.service.dtos.CustomerDTO;
import ca.ulaval.glo2003.service.dtos.HoursDTO;
import ca.ulaval.glo2003.service.dtos.ReservationConfigurationDTO;
import ca.ulaval.glo2003.service.dtos.TimeDTO;

public record RestaurantResponseWithReviews(String id,
                                            String ownerId,
                                            String name,
                                            Integer capacity,
                                            HoursDTO hours,
                                            ReservationConfigurationDTO reservations,
                                            Double rating,
                                            int reviewCount) {

}
