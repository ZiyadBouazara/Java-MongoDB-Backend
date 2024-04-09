package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.api.fixture.ReservationRequestFixture;
import ca.ulaval.glo2003.controllers.assemblers.ReservationGeneralResponseAssembler;
import ca.ulaval.glo2003.controllers.assemblers.ReservationResponseAssembler;
import ca.ulaval.glo2003.controllers.requests.ReservationRequest;
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
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ReservationServiceTest {
//    private static final String CUSTOMER_NAME = "John Deer";
//    private static final String CUSTOMER_EMAIL = "john.deer@gmail.com";
//    private static final String CUSTOMER_PHONE_NUMBER = "1234567890";
    private static final String OWNER_ID = "1";
    private static final String RESTO_NAME = "1";
    private static final Integer RESTO_CAPACITY = 10;
    private static final Hours RESTO_HOURS = new Hours("10:00:00", "21:00:00");
    private static final String DATE = "2024-03-31";
    private static final String START_TIME = "20:46:00";
    private static final int GROUP_SIZE = 3;

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
        String restaurantId = "restaurant1";
        restaurant = new Restaurant(OWNER_ID, RESTO_NAME, RESTO_CAPACITY, RESTO_HOURS);
        ReservationRequest reservationRequest = new ReservationRequestFixture().create();
        when(restaurantRepository.findRestaurantById(restaurantId)).thenReturn(restaurant);
        Customer mockCustomer = mock(Customer.class);
        when(customerAssembler.fromDTO(reservationRequest.customer())).thenReturn(mockCustomer);
        Reservation mockReservation = mock(Reservation.class);
        when(reservationFactory.createReservation(any(), any(), any(), any(), any())).thenReturn(mockReservation);

        String reservationId = reservationService.createReservation(restaurantId, reservationRequest);

        verify(reservationRepository).saveReservation(mockReservation);
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
}
