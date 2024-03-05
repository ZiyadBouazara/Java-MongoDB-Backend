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
    void givenWithValidParameters_shouldNotThrowException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture().create();

        assertDoesNotThrow(reservationRequest::verifyParameters);
    }

    @Test
    void givenWithValidGroupSize_shouldNotThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withGroupSize(1)
                .create();

        assertDoesNotThrow(reservationRequest::verifyParameters);
    }

    @Test
    void given_withValidStartTime_shouldNotThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withStartTime(VALID_START_TIME)
                .create();

        assertDoesNotThrow(reservationRequest::verifyParameters);
    }

    @Test
    void given_withValidDate_shouldNotThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withDate(VALID_DATE)
                .create();

        assertDoesNotThrow(reservationRequest::verifyParameters);
    }

    @Test
    void given_withValidGroupSize_shouldNotThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withGroupSize(VALID_GROUP_SIZE)
                .create();

        assertDoesNotThrow(reservationRequest::verifyParameters);
    }

    @Test
    void given_withMissingDate_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .createWithMissingDate();

        assertThrows(MissingParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void given_withMissingStartTime_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .createWithMissingStartTime();

        assertThrows(MissingParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void given_withMissingGroupSize_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .createWithMissingGroupSize();

        assertThrows(MissingParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void given_withMissingCustomer_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .createWithMissingCustomer();

        assertThrows(MissingParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void given_withInvalidCustomerEmail_shouldThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .create();
        reservationRequest.getCustomer().setEmail(INVALID_EMAIL);

        assertThrows(InvalidParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void given_withInvalidCustomerPhoneNumber_shouldThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .create();
        reservationRequest.getCustomer().setPhoneNumber(INVALID_PHONE_NUMBER);

        assertThrows(InvalidParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void given_withMissingCustomerName_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .create();
        reservationRequest.getCustomer().setName(null);

        assertThrows(MissingParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void given_withMissingCustomerEmail_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .create();
        reservationRequest.getCustomer().setEmail(null);

        assertThrows(MissingParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void given_withMissingCustomerPhoneNumber_shouldThrowMissingParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .create();
        reservationRequest.getCustomer().setPhoneNumber(null);

        assertThrows(MissingParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void given_withInvalidGroupSize_shouldThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withGroupSize(INVALID_GROUP_SIZE)
                .create();

        assertThrows(InvalidParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void given_withInvalidStartTime_shouldThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withStartTime(INVALID_START_TIME)
                .create();

        assertThrows(InvalidParameterException.class, reservationRequest::verifyParameters);
    }

    @Test
    void given_withInvalidDate_shouldThrowInvalidParameterException() {
        ReservationRequest reservationRequest = new ReservationRequestFixture()
                .withDate(INVALID_DATE)
                .create();

        assertThrows(InvalidParameterException.class, reservationRequest::verifyParameters);
    }
}
