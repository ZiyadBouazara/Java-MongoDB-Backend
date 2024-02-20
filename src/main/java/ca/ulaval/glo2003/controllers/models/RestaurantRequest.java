package ca.ulaval.glo2003.controllers.models;

import ca.ulaval.glo2003.domain.utils.Hours;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.restaurant.ReservationConfiguration;
import jakarta.ws.rs.NotFoundException;

import java.time.Duration;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record RestaurantRequest(String name,
                                Integer capacity,
                                HoursDTO hours,
                                ReservationConfigurationDTO reservations) {
}
