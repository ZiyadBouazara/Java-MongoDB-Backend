package ca.ulaval.glo2003.controllers.requests;

import ca.ulaval.glo2003.service.dtos.VisitTimeDTO;
import jakarta.annotation.Nullable;

public record FuzzySearchRequest(@Nullable String name,
                                 @Nullable
                                 VisitTimeDTO opened) { }
