package ca.ulaval.glo2003.controllers.responses;

import ca.ulaval.glo2003.service.dtos.HoursDTO;

public record FuzzySearchResponse(String id,
                                  String name,
                                  Integer capacity,
                                  HoursDTO hours) {
}
