package ca.ulaval.glo2003.domain.restaurant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationConfigurationTest {
    private static final Integer DEFAULT_RESERVATION_DURATION = 60;
    private static final Integer CUSTOM_RESERVATION_DURATION = 90;

    @Test
    public void givenNoReservationDuration_whenCreatingReservationConfiguration_shouldUseDefaultDuration() {
        ReservationConfiguration defaultDurationConfiguration = new ReservationConfiguration();
        Integer duration = defaultDurationConfiguration.getDuration();
        assertEquals(DEFAULT_RESERVATION_DURATION, duration.intValue());
    }

    @Test
    public void givenCustomReservationDuration_whenCreatingReservationConfiguration_shouldUseCustomDuration() {
        ReservationConfiguration customDurationConfiguration = new ReservationConfiguration(CUSTOM_RESERVATION_DURATION);
        Integer duration = customDurationConfiguration.getDuration();
        assertEquals(CUSTOM_RESERVATION_DURATION, duration.intValue());
    }
}
