package ca.ulaval.glo2003.restaurant.DomainTest;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.utils.Hours;
import ca.ulaval.glo2003.models.RestaurantRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class RestaurantRequestTest {

    @Test
    void verify_withValidParam_ShouldNotThrowException(){
        RestaurantRequest restauRequest = new RestaurantRequest();
        Hours hours = new Hours();
        hours.setOpen("10:00:00");
        hours.setClose("20:00:00");
        restauRequest.setHours(hours);

        restauRequest.setName("restauTest");
        restauRequest.setCapacity(30);

        assertDoesNotThrow(() -> restauRequest.verifyParameters());
    }

    @Test
    void verify_withInValidName_ShouldThrowException(){
        RestaurantRequest restauRequest = new RestaurantRequest();
        Hours hours = new Hours();
        hours.setOpen("10:00:00");
        hours.setClose("20:00:00");
        restauRequest.setHours(hours);

        restauRequest.setName("");
        restauRequest.setCapacity(30);

        assertThrows(InvalidParameterException.class, () -> restauRequest.verifyParameters());
    }

    @Test
    void verify_withInValidCapacity_ShouldThrowException(){
        RestaurantRequest restauRequest = new RestaurantRequest();
        Hours hours = new Hours();
        hours.setOpen("10:00:00");
        hours.setClose("20:00:00");
        restauRequest.setHours(hours);

        restauRequest.setName("");
        restauRequest.setCapacity(0);

        assertThrows(InvalidParameterException.class, () -> restauRequest.verifyParameters());
    }

    @Test
    void verify_withInValid_WritingFormat_Hours_ShouldThrowException(){
        RestaurantRequest restauRequest = new RestaurantRequest();
        Hours hours = new Hours();
        hours.setOpen("10,00:00");
        hours.setClose("20,00:00");
        restauRequest.setHours(hours);

        restauRequest.setName("restauTest");
        restauRequest.setCapacity(30);

        assertThrows(InvalidParameterException.class, () -> restauRequest.verifyParameters());
    }

    @Test
    void verify_withInValid_Format_Hours_ShouldThrowException(){
        RestaurantRequest restauRequest = new RestaurantRequest();
        Hours hours = new Hours();
        hours.setOpen("10:00");
        hours.setClose("20:00");
        restauRequest.setHours(hours);

        restauRequest.setName("restauTest");
        restauRequest.setCapacity(30);

        assertThrows(InvalidParameterException.class, () -> restauRequest.verifyParameters());
    }

    @Test
    void verify_openForMinDuration_ShouldThrowException(){
        RestaurantRequest restauRequest = new RestaurantRequest();
        Hours hours = new Hours();
        hours.setOpen("10:00:00");
        hours.setClose("10:00:00");
        restauRequest.setHours(hours);

        restauRequest.setName("restauTest");
        restauRequest.setCapacity(30);

        assertThrows(InvalidParameterException.class, () -> restauRequest.verifyParameters());
    }

    @Test
    void verify_openBeforeMidnight_ShouldThrowException(){
        RestaurantRequest restauRequest = new RestaurantRequest();
        Hours hours = new Hours();
        hours.setOpen("23:59:59");
        hours.setClose("10:00:00");
        restauRequest.setHours(hours);

        restauRequest.setName("restauTest");
        restauRequest.setCapacity(30);

        assertThrows(InvalidParameterException.class, () -> restauRequest.verifyParameters());
    }

    @Test
    void verify_closesBeforeMidnight_ShouldThrowException(){
        RestaurantRequest restauRequest = new RestaurantRequest();
        Hours hours = new Hours();
        hours.setOpen("10:00:00");
        hours.setClose("00:00:00");
        restauRequest.setHours(hours);

        restauRequest.setName("restauTest");
        restauRequest.setCapacity(30);

        assertThrows(InvalidParameterException.class, () -> restauRequest.verifyParameters());
    }

    @Test
    void verify_withMissingName_ShouldThrowException(){
        RestaurantRequest restauRequest = new RestaurantRequest();
        Hours hours = new Hours();
        hours.setOpen("10:00:00");
        hours.setClose("20:00:00");
        restauRequest.setHours(hours);

        restauRequest.setCapacity(30);

        assertThrows(MissingParameterException.class, () -> restauRequest.verifyParameters());
    }


    @Test
    void verify_withMissingCapacity_ShouldThrowException(){
        RestaurantRequest restauRequest = new RestaurantRequest();
        Hours hours = new Hours();
        hours.setOpen("10:00:00");
        hours.setClose("20:00:00");
        restauRequest.setHours(hours);

        restauRequest.setName("restauTest");

        assertThrows(MissingParameterException.class, () -> restauRequest.verifyParameters());
    }

    @Test
    void verify_withMissingHours_ShouldThrowException(){
        RestaurantRequest restauRequest = new RestaurantRequest();

        restauRequest.setName("restauTest");
        restauRequest.setCapacity(30);

        assertThrows(MissingParameterException.class, () -> restauRequest.verifyParameters());
    }
}
