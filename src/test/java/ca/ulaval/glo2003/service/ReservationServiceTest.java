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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    private ReservationRepository reservationRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private ReservationFactory reservationFactory;
    @Mock
    private ReservationValidator reservationValidator;
    private CustomerAssembler customerAssembler;
    private ReservationResponseAssembler reservationResponseAssembler;
    private ReservationGeneralResponseAssembler reservationGeneralResponseAssembler;
    private ReservationService reservationService;
    private Restaurant restaurant;
    private Reservation reservation;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = mock(Customer.class);
        restaurant = new Restaurant(OWNER_ID, RESTO_NAME, RESTO_CAPACITY, RESTO_HOURS);
        reservation = new Reservation(restaurant.getId(), DATE, START_TIME, GROUP_SIZE, customer);
        customerAssembler = new CustomerAssembler();
        reservationResponseAssembler = new ReservationResponseAssembler();
        reservationGeneralResponseAssembler = new ReservationGeneralResponseAssembler();
        reservationService = new ReservationService(
                restaurantRepository,
                reservationRepository,
                reservationFactory,
                customerAssembler,
                reservationResponseAssembler,
                reservationValidator,
                reservationGeneralResponseAssembler);
    }

    public void givenValidRestaurantIdAndReservationRequest_whenCreatingReservation_shouldReturnRightReservationId()  {
        ReservationRequest reservationRequestFixture = new ReservationRequestFixture().create();
        when(restaurantRepository.findRestaurantById(restaurant.getId())).thenReturn(restaurant);
        when(reservationFactory.createReservation(restaurant.getId(),
                reservationRequestFixture.date(),
                reservationRequestFixture.startTime(),
                reservationRequestFixture.groupSize(),
                customer))
                .thenReturn(reservation);

        //String createdId = reservationService.createReservation(restaurant.getId(), reservationRequestFixture);

        //Assertions.assertThat(createdId).isEqualTo(TOURNAMENT_ID);
    }


    //    //TODO : IMPLEMENT THE SERVICE TEST
//    private static final String CUSTOMER_NAME = "John Deer";
//    private static final String CUSTOMER_EMAIL = "john.deer@gmail.com";
//    private static final String CUSTOMER_PHONE_NUMBER = "1234567890";
//    private static final String OWNER_ID = "1";
//    private static final String RESTO_NAME = "1";
//    private static final Integer RESTO_CAPACITY = 10;
//    private static final Hours RESTO_HOURS = new Hours("10:00:00", "21:00:00");
//    Customer customer;
//    ReservationService reservationService;
//    @Mock
//    ReservationFactory reservationFactory;
//    @Mock
//    RestaurantAndReservationRepository restaurantAndReservationRepository;
//    @Mock
//    CustomerAssembler customerAssembler;
//    @Mock
//    ReservationResponseAssembler reservationResponseAssembler;
//    @Mock
//    ReservationValidator reservationValidator;
//    @Mock
//    Restaurant restaurant;
//
//    CustomerDTO customerDto;
//
//    @BeforeEach
//    void setup() {
//        customerDto = new CustomerDTO(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE_NUMBER);
//        customer = new Customer(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE_NUMBER);
//        restaurant = new Restaurant(OWNER_ID, RESTO_NAME, RESTO_CAPACITY, RESTO_HOURS);
//
//        reservationService = new ReservationService(
//                restaurantAndReservationRepository,
//            restaurantRepository, reservationRepository, reservationFactory,
//                customerAssembler,
//                reservationResponseAssembler,
//                reservationValidator
//        );
//    }
//
//    @Test
//    void canCreateReservation() {
//        // TODO: since we changed the flow, the service now accepts a request; We have to change the test.
//        String date = "2024-03-31";
//        String startTime = "20:46:00";
//        Integer groupSize = 5;
//        Reservation reservation = new Reservation(restaurant.getId(), date, startTime, groupSize, customer);
//        when(restaurantAndReservationRepository.findRestaurantByRestaurantId(restaurant.getId())).thenReturn(restaurant);
//        when(reservationFactory.createReservation(restaurant.getId(), date, startTime, groupSize, customer)).thenReturn(reservation);
//
//        //reservationService.createReservation(restaurant.getId(), date, startTime, groupSize);
//    }
}
