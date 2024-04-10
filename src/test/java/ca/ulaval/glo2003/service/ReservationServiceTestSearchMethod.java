package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.assemblers.ReservationGeneralResponseAssembler;
import ca.ulaval.glo2003.controllers.assemblers.ReservationResponseAssembler;
import ca.ulaval.glo2003.controllers.responses.ReservationGeneralResponse;
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
import ca.ulaval.glo2003.service.dtos.CustomerDTO;
import ca.ulaval.glo2003.service.dtos.TimeDTO;
import ca.ulaval.glo2003.service.validators.HeaderValidator;
import ca.ulaval.glo2003.service.validators.ReservationValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ReservationServiceTestSearchMethod {
    private static final String OWNER_ID = "1";
    private static final String INVALID_OWNER_ID = "2";
    private static final String INVALID_ID = "2";
    private static final String INVALID_DATE = "2024-03-02";
    private static final String VALID_CUSTOMER_NAME = "John Doe";
    private static final String INVALID_CUSTOMER_NAME = "Jhon Doe";
    private static final String RESTO_NAME = "1";
    private static final Integer RESTO_CAPACITY = 10;
    private static final Hours RESTO_HOURS = new Hours("10:00:00", "21:00:00");
    private static final String DATE = "2024-03-31";
    private static final String START_TIME = "20:46:00";
    private static final int GROUP_SIZE = 3;
    private static final String CUSTOMER_EMAIL = "z@y.z";
    private static final String CUSTOMER_PHONE_NUMBER = "123456789";
    @Mock
    private ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);
    @Mock
    private RestaurantRepository restaurantRepository = Mockito.mock(RestaurantRepository.class);
    @Mock
    private ReservationFactory reservationFactory;
    @Mock
    private ReservationValidator reservationValidator = Mockito.mock(ReservationValidator.class);
    @Mock
    private HeaderValidator headerValidator = Mockito.mock(HeaderValidator.class);
    private CustomerAssembler customerAssembler;
    private CustomerDTO customerDto;
    private ReservationResponseAssembler reservationResponseAssembler;
    private ReservationGeneralResponseAssembler reservationGeneralResponseAssembler;
    private ReservationService reservationService;
    private Restaurant restaurant;
    private Reservation reservation;
    private Customer customer;
    private ReservationGeneralResponse reservationGeneralResponse;
    private final List<Reservation> reservationList = new ArrayList<>();
    List<ReservationGeneralResponse> searchedReservations = new ArrayList<>();

    @BeforeEach
    public void setUp() throws MissingParameterException, InvalidParameterException {
        customer = mock(Customer.class);
        restaurant = new Restaurant(OWNER_ID, RESTO_NAME, RESTO_CAPACITY, RESTO_HOURS);
        reservation = new Reservation(restaurant.getId(), DATE, START_TIME, GROUP_SIZE, customer);
        customerAssembler = new CustomerAssembler();
        customerDto = new CustomerDTO("John Doe", CUSTOMER_EMAIL, CUSTOMER_PHONE_NUMBER);

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

        reservationGeneralResponse = new ReservationGeneralResponse(reservation.getId(),
                reservation.getDate(),
                new TimeDTO(reservation.getStartTime(), restaurant.getRestaurantConfiguration().getDuration()),
                reservation.getGroupSize(),
                customerDto);
        reservationList.add(reservation);
        when(reservationRepository.getAllRestaurantReservations(restaurant.getId()))
                .thenReturn(reservationList);

        when(restaurantRepository.findRestaurantById(restaurant.getId()))
                .thenReturn(restaurant);
        when(customer.getName())
                .thenReturn(VALID_CUSTOMER_NAME);

        doNothing().when(headerValidator).verifyMissingHeader(OWNER_ID);


        doNothing().when(reservationValidator).validateSearchReservationRequest(restaurant.getId(), DATE);

    }

    @Test
    public void givenValidParamShouldReturnValidResto()
            throws InvalidParameterException, MissingParameterException {
        searchedReservations = reservationService.searchReservations(OWNER_ID, restaurant.getId(), DATE, customerDto.name());
        boolean customerFound = searchedReservations.stream()
                .anyMatch(reservation -> reservation.customer().name().equals(customerDto.name()));
        Assertions.assertTrue(customerFound, "Customer name not found in the searched reservations list");
    }

    @Test
    public void givenValidCustomerInSearch_whenSearchReservations_ShouldReturnListContainingCustomerName()
            throws InvalidParameterException, MissingParameterException {
        searchedReservations = reservationService.searchReservations(OWNER_ID, restaurant.getId(), DATE, customerDto.name());
        ReservationGeneralResponse foundReservation = searchedReservations.stream()
                .filter(reservation -> reservation.customer().name().equals(customerDto.name()))
                .findFirst()
                .orElse(null);
        Assertions.assertNotNull(foundReservation, "Matching reservation not found");
        Assertions.assertEquals(foundReservation.customer().name(), customerDto.name());
    }

    @Test
    public void givenOneCorrespondingReservation_whenSearchReservation_ShouldReturnListSizeOne()
            throws InvalidParameterException, MissingParameterException {
        searchedReservations = reservationService.searchReservations(OWNER_ID, restaurant.getId(), DATE, customerDto.name());
        Assertions.assertEquals(1, searchedReservations.size(), "Expected one reservation in the list");
    }

    @Test
    public void givenNoCorrespondingReservation_whenSearchReservation_ShouldReturnListEmpty()
            throws InvalidParameterException, MissingParameterException {
        searchedReservations = reservationService.searchReservations(INVALID_OWNER_ID, INVALID_ID, INVALID_DATE, INVALID_CUSTOMER_NAME);
        Assertions.assertEquals(0, searchedReservations.size(), "Expected one reservation in the list");
    }

    @Test
    public void givenNullOwnerId_whenSearchReservations_shouldThrowInvalidParameter() {
        try {
            reservationService.searchReservations(null, restaurant.getId(), DATE, VALID_CUSTOMER_NAME);
            Assertions.fail("Expected InvalidParameterException to be thrown");
        } catch (InvalidParameterException | MissingParameterException e) {
            Assertions.assertTrue(e.getMessage().contains("Missing 'Owner' header"),
                    "Expected exception message to contain 'missing header'");
        }
    }

    @Test
    public void givenNoMatchingReservations_whenSearchReservations_shouldReturnEmptyList()
            throws InvalidParameterException, MissingParameterException {
        searchedReservations = reservationService.searchReservations(OWNER_ID, restaurant.getId(),
                DATE, "NonExistingCustomer");
        Assertions.assertTrue(searchedReservations.isEmpty(),
                "Expected empty list when no matching reservations found");
    }

    @Test
    public void givenValidParamsWithMultipleMatchingReservations_whenSearchReservations_shouldReturnListOfMatchingReservations()
            throws InvalidParameterException, MissingParameterException {
        reservationList.add(new Reservation(restaurant.getId(), DATE, START_TIME, GROUP_SIZE,
                new Customer("John Doenetsk", "johnmax@em.com", "999-999-9999")));
        searchedReservations = reservationService.searchReservations(OWNER_ID, restaurant.getId(),
                DATE, VALID_CUSTOMER_NAME);
        Assertions.assertEquals(2, searchedReservations.size(),
                "Expected two reservations in the list");
    }

    @Test
    public void givenValidParamsWithNoMatchingReservations_whenSearchReservations_shouldReturnEmptyList()
            throws InvalidParameterException, MissingParameterException {
        searchedReservations = reservationService.searchReservations(OWNER_ID, restaurant.getId(),
                DATE, "NonExistingCustomer");
        Assertions.assertTrue(searchedReservations.isEmpty(),
                "Expected empty list when no matching reservations found");
    }

    @Test
    public void givenValidParamsWithNoReservations_whenSearchReservations_shouldReturnEmptyList()
            throws InvalidParameterException, MissingParameterException {
        when(reservationRepository.getAllRestaurantReservations(restaurant.getId())).thenReturn(new ArrayList<>());
        searchedReservations = reservationService.searchReservations(OWNER_ID, restaurant.getId(),
                DATE, VALID_CUSTOMER_NAME);
        Assertions.assertTrue(searchedReservations.isEmpty(),
                "Expected empty list when no reservations found");
    }


}
