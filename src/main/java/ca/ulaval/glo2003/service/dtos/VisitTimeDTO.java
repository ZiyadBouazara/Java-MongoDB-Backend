package ca.ulaval.glo2003.service.dtos;

import jakarta.annotation.Nullable;

public record VisitTimeDTO(@Nullable
                           String from,
                           @Nullable
                           String to) { }
