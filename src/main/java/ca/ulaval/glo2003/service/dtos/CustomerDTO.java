package ca.ulaval.glo2003.service.dtos;

import jakarta.validation.constraints.NotBlank;

public record CustomerDTO(
    @NotBlank
    String name,
    @NotBlank
    String email,
    @NotBlank
    String phoneNumber
) { }
