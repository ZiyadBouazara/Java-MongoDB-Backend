package ca.ulaval.glo2003.controllers.requests;


import ca.ulaval.glo2003.service.dtos.HoursDTO;
import ca.ulaval.glo2003.service.dtos.ReservationConfigurationDTO;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RestaurantRequest(@NotBlank
                                String name,
                                @NotNull
                                Integer capacity,
                                @NotNull
                                HoursDTO hours,
                                @Nullable
                                ReservationConfigurationDTO reservations) {
}
