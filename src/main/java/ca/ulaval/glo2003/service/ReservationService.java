package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.assemblers.ReservationGeneralResponseAssembler;
import ca.ulaval.glo2003.controllers.assemblers.ReservationResponseAssembler;
import ca.ulaval.glo2003.controllers.requests.ReservationRequest;
import ca.ulaval.glo2003.controllers.responses.ReservationGeneralResponse;
import ca.ulaval.glo2003.controllers.responses.ReservationResponse;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.repositories.ReservationRepository;
import ca.ulaval.glo2003.domain.repositories.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.service.assembler.CustomerAssembler;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationFactory;
import ca.ulaval.glo2003.service.helpers.ReservationHelper;
import ca.ulaval.glo2003.service.validators.GetRestaurantValidator;
import ca.ulaval.glo2003.service.validators.HeaderValidator;
import ca.ulaval.glo2003.service.validators.ReservationValidator;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationFactory reservationFactory;
    private final CustomerAssembler customerAssembler;
    private final ReservationResponseAssembler reservationResponseAssembler;
    private final ReservationGeneralResponseAssembler reservationGeneralResponseAssembler;
    private final ReservationValidator reservationValidator;
    private final HeaderValidator headerValidator = new HeaderValidator();
    private final GetRestaurantValidator getRestaurantValidator = new GetRestaurantValidator();

    @Inject
    public ReservationService(RestaurantRepository restaurantRepository,
                              ReservationRepository reservationRepository,
                              ReservationFactory reservationFactory,
                              CustomerAssembler customerAssembler,
                              ReservationResponseAssembler reservationResponseAssembler,
                              ReservationValidator reservationValidator,
                              ReservationGeneralResponseAssembler reservationGeneralResponseAssembler) {
        this.restaurantRepository = restaurantRepository;
        this.reservationRepository = reservationRepository;
        this.reservationFactory = reservationFactory;
        this.customerAssembler = customerAssembler;
        this.reservationResponseAssembler = reservationResponseAssembler;
        this.reservationValidator = reservationValidator;
        this.reservationGeneralResponseAssembler = reservationGeneralResponseAssembler;
    }

    public String createReservation(String restaurantId, ReservationRequest reservationRequest)
            throws InvalidParameterException, MissingParameterException {

        reservationValidator.validateReservationRequest(reservationRequest);
        String adjustedStartTime = adjustReservation(reservationRequest.startTime());
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);
        reservationValidator.validateGroupSizeWithinRestaurantLimit(reservationRequest, restaurant);
        reservationValidator.validateStartTimeInRestaurantBounds(adjustedStartTime, restaurant);

        Customer customer = customerAssembler.fromDTO(reservationRequest.customer());
        Reservation reservation = reservationFactory.createReservation(restaurantId, reservationRequest.date(),
                adjustedStartTime, reservationRequest.groupSize(), customer);
        reservationRepository.saveReservation(reservation);
        return reservation.getId();
    }

    public void deleteReservation(String reservationId) throws NotFoundException {
        reservationRepository.deleteReservation(reservationId);
    }

    public ReservationResponse getReservation(String reservationId) {
        Reservation reservation = reservationRepository.findReservationById(reservationId);
        Restaurant restaurant = restaurantRepository.findRestaurantById(reservation.getRestaurantId());
        return reservationResponseAssembler.toDTO(reservation, restaurant);
    }

    public List<ReservationGeneralResponse> searchReservations(String ownerId,
                                                               String restaurantId,
                                                               String date,
                                                               String customerName)
            throws InvalidParameterException, MissingParameterException {
        headerValidator.verifyMissingHeader(ownerId);
        reservationValidator.validateSearchReservationRequest(restaurantId, date);

        List<Reservation> reservations = reservationRepository.getAllRestaurantReservations(restaurantId);
        List<ReservationGeneralResponse> searchedReservations = new ArrayList<>();

        for (Reservation reservation : reservations) {
            Restaurant restaurant = findAndValidateRestaurant(reservation);
            getRestaurantValidator.validateRestaurantOwnership(ownerId, restaurant.getOwnerId());
            if (matchesSearchCriteria(reservation, customerName, date)) {
                searchedReservations.add(reservationGeneralResponseAssembler
                        .toDTO(reservation, restaurant));
            }
        }
        return searchedReservations;
    }

    private Restaurant findAndValidateRestaurant(Reservation reservation) throws NotFoundException {
        Restaurant restaurant = restaurantRepository.findRestaurantById(reservation.getRestaurantId());
        if (restaurant == null) {
            throw new NotFoundException("Restaurant not found for reservation with ID: " + reservation.getId());
        }
        return restaurant;
    }

    private boolean matchesSearchCriteria(Reservation reservation, String customerName, String date) {
        return ReservationHelper.isMatchingCustomerName(reservation.getCustomer().getName(), customerName)
                && ReservationHelper.isMatchingDate(reservation.getDate(), date);
    }

    private String adjustReservation(String startTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = LocalTime.parse(startTime, formatter);

        int minutes = time.getMinute();
        int roundTo = minutes > 0 ? ((minutes / 15) + 1) * 15 : 0;  // Handle 0 minutes

        // Adjust the hour and reset minutes if rounded minutes equals 60
        int adjustedHour = time.getHour();
        int adjustedMinutes = roundTo;
        if (adjustedMinutes == 60) {
            adjustedHour += 1;  // Increment hour
            adjustedMinutes = 0;  // Reset minutes to 0
        }

        // Create a LocalTime object with the adjusted hour and minutes
        LocalTime roundedTime = LocalTime.of(adjustedHour, adjustedMinutes);

        return roundedTime.format(formatter);
    }
}
