package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.assemblers.FuzzySearchResponseAssembler;
import ca.ulaval.glo2003.controllers.assemblers.RestaurantResponseAssembler;
import ca.ulaval.glo2003.controllers.requests.FuzzySearchRequest;
import ca.ulaval.glo2003.controllers.responses.FuzzySearchResponse;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.repositories.ReservationRepository;
import ca.ulaval.glo2003.domain.repositories.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.domain.utils.Hours;
import ca.ulaval.glo2003.service.assembler.FuzzySearchAssembler;
import ca.ulaval.glo2003.service.assembler.HoursAssembler;
import ca.ulaval.glo2003.service.dtos.HoursDTO;
import ca.ulaval.glo2003.service.dtos.VisitTimeDTO;
import ca.ulaval.glo2003.service.validators.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    private static final FuzzySearchResponse FUZZY_SEARCH_RESPONSE =
            new FuzzySearchResponse("1",
                    "Restaurant 1",
                    20,
                    new HoursDTO("09:00", "16:00"));
    private static final FuzzySearchResponse FUZZY_SEARCH_RESPONSE_2 =
            new FuzzySearchResponse("2",
                    "Restaurant 2",
                    30,
                    new HoursDTO("10:00", "17:00"));
    private static final Restaurant RESTAURANT =
            new Restaurant("1",
                    "Some Apples",
                    20,
                    new Hours("09:00:00", "16:00:00"));
    private static final Restaurant RESTAURANT_2 =
            new Restaurant("2",
                    "Some Bananas",
                    30,
                    new Hours("10:00:00", "17:00:00"));
    public static final VisitTimeDTO VISIT_TIME_DTO =
            new VisitTimeDTO("10:00:00", "15:00:00");
    public static final VisitTimeDTO INVALID_VISIT_TIME_DTO =
            new VisitTimeDTO("08:00:00", "15:00:00");
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private RestaurantService restaurantServiceUnderTest;
    private AutoCloseable autoCloseable;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private RestaurantFactory restaurantFactory;
    @Mock
    private HoursAssembler hoursAssembler;
    @Mock
    private RestaurantResponseAssembler restaurantResponseAssembler;
    @Mock
    private FuzzySearchAssembler fuzzySearchAssembler;
    @Mock
    private FuzzySearchResponseAssembler fuzzySearchResponseAssembler;
    @Mock
    private ReservationValidator reservationValidator;
    @Mock
    private SearchRestaurantValidator restaurantSearchValidator;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        restaurantServiceUnderTest = new RestaurantService(
                restaurantRepository,
                restaurantFactory,
                hoursAssembler,
                restaurantResponseAssembler,
                fuzzySearchAssembler,
                fuzzySearchResponseAssembler,
                reservationRepository,
                reservationValidator);
        restaurantSearchValidator = new SearchRestaurantValidator();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void givenSearchRequest_whenGetAllRestaurantsForSearch_thenReturnFuzzySearchResponses()
            throws InvalidParameterException {

        FuzzySearchRequest searchRequest =
                new FuzzySearchRequest("Some", new VisitTimeDTO("10:00:00", "15:00:00"));
        List<Restaurant> allRestaurants = Arrays.asList(RESTAURANT, RESTAURANT_2);

        when(restaurantRepository.getAllRestaurants()).thenReturn(allRestaurants);
        when(fuzzySearchResponseAssembler.toDTO(RESTAURANT)).thenReturn(FUZZY_SEARCH_RESPONSE);
        when(fuzzySearchResponseAssembler.toDTO(RESTAURANT_2)).thenReturn(FUZZY_SEARCH_RESPONSE_2);

        List<FuzzySearchResponse> searchedRestaurants =
                restaurantServiceUnderTest.getAllRestaurantsForSearch(searchRequest);

        Assertions.assertEquals(searchedRestaurants, List.of(FUZZY_SEARCH_RESPONSE, FUZZY_SEARCH_RESPONSE_2));
    }

    @Test
    void givenFuzzySearchRequestName_whenShouldMatchRestaurantName_shouldReturnTrue() {

        FuzzySearchRequest searchRequest = new FuzzySearchRequest("So", null);

        boolean result = restaurantServiceUnderTest.shouldMatchRestaurantName(searchRequest, RESTAURANT);

        Assertions.assertTrue(result);
    }

    @Test
    void givenInvalidFuzzySearchRequestName_whenShouldMatchRestaurantName_shouldReturnFalse() {

        FuzzySearchRequest searchRequest = new FuzzySearchRequest("Soo", null);

        boolean result = restaurantServiceUnderTest.shouldMatchRestaurantName(searchRequest, RESTAURANT);

        Assertions.assertFalse(result);
    }

    @Test
    void givenVisitTimeDTO_whenShouldMatchRestaurantHours_shouldReturnTrue() {

        FuzzySearchRequest searchRequest = new FuzzySearchRequest(null, VISIT_TIME_DTO);

        boolean result = restaurantServiceUnderTest.shouldMatchRestaurantHours(searchRequest, RESTAURANT);

        Assertions.assertTrue(result);
    }

    @Test
    void givenInvalidVisitTimeDTO_whenShouldMatchRestaurantHours_shouldReturnFalse() {

        FuzzySearchRequest searchRequest = new FuzzySearchRequest(null, INVALID_VISIT_TIME_DTO);

        boolean result = restaurantServiceUnderTest.shouldMatchRestaurantHours(searchRequest, RESTAURANT);

        Assertions.assertFalse(result);
    }

    @Test
    void getFuzzySearchResponseForRestaurant() {

        when(fuzzySearchResponseAssembler.toDTO(RESTAURANT)).thenReturn(FUZZY_SEARCH_RESPONSE);

        FuzzySearchResponse fuzzySearchResponse =
                restaurantServiceUnderTest.getFuzzySearchResponseForRestaurant(RESTAURANT);

        Assertions.assertEquals(FUZZY_SEARCH_RESPONSE, fuzzySearchResponse);
    }

}
