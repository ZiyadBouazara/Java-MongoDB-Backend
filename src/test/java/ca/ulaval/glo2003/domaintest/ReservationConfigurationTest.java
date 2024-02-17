package ca.ulaval.glo2003.domaintest;
import org.junit.Test;
import static org.junit.Assert.*;
import ca.ulaval.glo2003.domain.restaurant.ReservationConfiguration;


public class ReservationConfigurationTest {

    @Test
    public void testDefaultConstructor() {
        ReservationConfiguration reservationConfig = new ReservationConfiguration();

        Integer duration = reservationConfig.getDuration();

        assertEquals(60, duration.intValue());
    }

    @Test
    public void testConfigConstructor() {
        ReservationConfiguration reservationConfig = new ReservationConfiguration(90);

        Integer duration = reservationConfig.getDuration();

        assertEquals(90, duration.intValue());
    }

    @Test
    public void testReservationConfiguration_Setter() {
        ReservationConfiguration reservationConfig = new ReservationConfiguration();

        reservationConfig.setDuration(100);
        Integer duration = reservationConfig.getDuration();

        assertEquals(100, duration.intValue());
    }


}
