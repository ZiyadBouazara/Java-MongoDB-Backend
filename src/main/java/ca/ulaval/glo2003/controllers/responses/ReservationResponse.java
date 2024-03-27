package ca.ulaval.glo2003.controllers.responses;

import ca.ulaval.glo2003.service.dtos.CustomerDTO;
import ca.ulaval.glo2003.service.dtos.TimeDTO;

public record ReservationResponse(String id,
                                  String date,
                                  TimeDTO time,
                                  Integer groupSize,
                                  CustomerDTO customer,
                                  RestaurantResponseWithoutConfiguration restaurant) {
}


