package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.api.fixture.ReservationRequestFixture;
import ca.ulaval.glo2003.controllers.assemblers.ReservationGeneralResponseAssembler;
import ca.ulaval.glo2003.controllers.assemblers.ReservationResponseAssembler;
import ca.ulaval.glo2003.controllers.requests.ReservationRequest;
import ca.ulaval.glo2003.controllers.responses.ReservationResponse;
import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.repositories.ReservationRepository;
import ca.ulaval.glo2003.domain.repositories.RestaurantRepository;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationFactory;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.utils.Hours;
import ca.ulaval.glo2003.service.assembler.CustomerAssembler;
import ca.ulaval.glo2003.service.validators.ReservationValidator;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {
    private static final String CUSTOMER_NAME = "John Deer";
    private static final String CUSTOMER_EMAIL = "john.deer@gmail.com";
    private static final String CUSTOMER_PHONE_NUMBER = "1234567890";
    private static final String DATE = "2024-03-31";
    private static final String START_TIME = "20:46:00";
    private static final int GROUP_SIZE = 3;
    private static final String OWNER_ID = "1";
    private static final String RESTO_NAME = "1";
    private static final Integer RESTO_CAPACITY = 10;
    private static final Hours RESTO_HOURS = new Hours("10:00:00", "21:00:00");

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ReservationFactory reservationFactory;
    @Mock
    private CustomerAssembler customerAssembler;
    private ReservationValidator reservationValidator;
    private ReservationResponseAssembler reservationResponseAssembler;
    private ReservationGeneralResponseAssembler reservationGeneralResponseAssembler;
    private Restaurant restaurant;
    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {
        reservationValidator = new ReservationValidator();
        reservationGeneralResponseAssembler = new ReservationGeneralResponseAssembler();
        reservationResponseAssembler = new ReservationResponseAssembler();
        MockitoAnnotations.openMocks(this);
        reservationService = new ReservationService(
                restaurantRepository,
                reservationRepository,
                reservationFactory,
                customerAssembler,
                reservationResponseAssembler,
                reservationValidator,
                reservationGeneralResponseAssembler);
    }

    @Test
    public void givenValidReservationRequest_createReservation_shouldCreateAndSaveReservation() throws Exception {
        PrepareCreateMethod preparedCreateTest = getPreparedCreateTestMethod();

        String reservationId = reservationService.createReservation(
                preparedCreateTest.restaurantId(), preparedCreateTest.reservationRequest());

        Assertions.assertEquals(reservationId, preparedCreateTest.mockReservation().getId());
        verify(reservationRepository).saveReservation(preparedCreateTest.mockReservation());
    }


    @Test
    public void givenMissingGroupSize_createReservation_shouldThrowMissingParameterException() {
        String restaurantId = "restaurant1";
        ReservationRequest requestWithoutDate = new ReservationRequestFixture().createWithMissingDate();

        Assertions.assertThrows(MissingParameterException.class, () ->
                reservationService.createReservation(restaurantId, requestWithoutDate));
    }

    @Test
    public void givenExceedingGroupSize_createReservation_shouldThrowInvalidParameterException() {
        restaurant = new Restaurant(OWNER_ID, RESTO_NAME, 1, RESTO_HOURS);
        String restaurantId = "restaurant1";
        ReservationRequest requestWithoutDate = new ReservationRequestFixture().withGroupSize(2).create();
        when(restaurantRepository.findRestaurantById(restaurantId)).thenReturn(restaurant);

        Assertions.assertThrows(InvalidParameterException.class, () ->
                reservationService.createReservation(restaurantId, requestWithoutDate));
    }

    @Test
    public void givenReservationId_whenDeleteReservation_shouldCallDatabaseDeleteMethod() {
        String reservationId = "1";

        reservationService.deleteReservation(reservationId);

        verify(reservationRepository, times(1)).deleteReservation(reservationId);
    }

    @Test
    public void givenReservationId_whenGetReservation_shouldReturnNonNullResponse() {
        prepareGetMethod preparedGetTest = getPreparedGetTestMethod();

        ReservationResponse response = reservationService.getReservation(preparedGetTest.reservationId());

        assertNotNull(response, "Reservation response should not be null");
    }
    @Test
    public void givenReservationId_whenGetReservation_shouldReturnExpectedId() {
        prepareGetMethod preparedGetTest = getPreparedGetTestMethod();

        ReservationResponse response = reservationService.getReservation(preparedGetTest.reservationId());

        assertEquals(preparedGetTest.expectedReservation().getId(), response.id());
    }

    @NotNull
    private PrepareCreateMethod getPreparedCreateTestMethod() {
        String restaurantId = "restaurant1";
        restaurant = new Restaurant(OWNER_ID, RESTO_NAME, RESTO_CAPACITY, RESTO_HOURS);
        ReservationRequest reservationRequest = new ReservationRequestFixture().create();
        when(restaurantRepository.findRestaurantById(restaurantId)).thenReturn(restaurant);
        Customer mockCustomer = mock(Customer.class);
        when(customerAssembler.fromDTO(reservationRequest.customer())).thenReturn(mockCustomer);
        Reservation mockReservation = mock(Reservation.class);
        when(reservationFactory.createReservation(any(), any(), any(), any(), any())).thenReturn(mockReservation);
        PrepareCreateMethod preparedCreateTest = new PrepareCreateMethod(restaurantId, reservationRequest, mockReservation);
        return preparedCreateTest;
    }

    private record PrepareCreateMethod(String restaurantId, ReservationRequest reservationRequest, Reservation mockReservation) {
    }

    @NotNull
    private prepareGetMethod getPreparedGetTestMethod() {
        String reservationId = "1";
        Restaurant expectedRestaurant = new Restaurant(OWNER_ID, RESTO_NAME, RESTO_CAPACITY, RESTO_HOURS);
        Customer customer = new Customer(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE_NUMBER);
        Reservation expectedReservation = new Reservation(reservationId, expectedRestaurant.getId(),
                DATE, START_TIME, GROUP_SIZE, customer);
        when(reservationRepository.findReservationById(reservationId)).thenReturn(expectedReservation);
        when(restaurantRepository.
                findRestaurantById(expectedReservation.getRestaurantId())).thenReturn(expectedRestaurant);
        prepareGetMethod preparedGetTest = new prepareGetMethod(reservationId, expectedReservation);
        return preparedGetTest;
    }

    private record prepareGetMethod(String reservationId, Reservation expectedReservation) {
    }
}
