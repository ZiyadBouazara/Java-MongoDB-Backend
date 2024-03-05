package ca.ulaval.glo2003.model;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.model.fixture.ReservationRequestFixture;
import ca.ulaval.glo2003.models.ReservationRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationRequestTest {
    private static final String VALID_DATE = "2024-03-05";
    private static final String INVALID_DATE = "2024/03/05";
    private static final String VALID_START_TIME = "12:00:00";
    private static final String INVALID_START_TIME = "25:00:00";
    private static final Integer VALID_GROUP_SIZE = 5;
    private static final Integer INVALID_GROUP_SIZE = 0;
    private static final String INVALID_EMAIL = "invalid.ca";
    private static final String INVALID_PHONE_NUMBER = "123";

    @Test
    void givenValidParameters_whenCreating_shouldNotThrowException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture().create();

        assertDoesNotThrow(reservationRequest::verifyParameters);
    }

    @Test
    void givenValidGroupSize_whenCreating_shouldNotThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withGroupSize(VALID_GROUP_SIZE)
                .create();

        assertDoesNotThrow(reservationRequest::verifyParameters);
    }

    @Test
    void givenValidStartTime_whenCreating_shouldNotThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withStartTime(VALID_START_TIME)
                .create();

        assertDoesNotThrow(reservationRequest::verifyParameters);
    }

    @Test
    void givenValidDate_whenCreating_shouldNotThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withDate(VALID_DATE)
                .create();

        assertDoesNotThrow(reservationRequest::verifyParameters);
    }

    @Test
    void givenWithMissingDate_whenCreating_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .createWithMissingDate();

        assertThrows(MissingParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void givenWithMissingStartTime_whenCreating_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .createWithMissingStartTime();

        assertThrows(MissingParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void givenWithMissingGroupSize_whenCreating_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .createWithMissingGroupSize();

        assertThrows(MissingParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void givenWithMissingCustomer_whenCreating_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .createWithMissingCustomer();

        assertThrows(MissingParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void givenWithInvalidCustomerEmail_whenCreating_shouldThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .create();
        reservationRequest.getCustomer().setEmail(INVALID_EMAIL);

        assertThrows(InvalidParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void givenWithInvalidCustomerPhoneNumber_whenCreating_shouldThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .create();
        reservationRequest.getCustomer().setPhoneNumber(INVALID_PHONE_NUMBER);

        assertThrows(InvalidParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void givenWithMissingCustomerName_whenCreating_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .create();
        reservationRequest.getCustomer().setName(null);

        assertThrows(MissingParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void givenWithMissingCustomerEmail_whenCreating_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .create();
        reservationRequest.getCustomer().setEmail(null);

        assertThrows(MissingParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void givenWithMissingCustomerPhoneNumber_whenCreating_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .create();
        reservationRequest.getCustomer().setPhoneNumber(null);

        assertThrows(MissingParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void givenWithInvalidGroupSize_whenCreating_shouldThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withGroupSize(INVALID_GROUP_SIZE)
                .create();

        assertThrows(InvalidParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void givenWithInvalidStartTime_whenCreating_shouldThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withStartTime(INVALID_START_TIME)
                .create();

        assertThrows(InvalidParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void givenWithInvalidDate_whenCreating_shouldThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withDate(INVALID_DATE)
                .create();

        assertThrows(InvalidParameterException.class, reservationRequest::verifyParameters);
    }
}
