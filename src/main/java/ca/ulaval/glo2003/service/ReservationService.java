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
    public ReservationService(RestaurantRepository restaurantRepository, ReservationRepository reservationRepository,
                              ReservationFactory reservationFactory, CustomerAssembler customerAssembler,
                              ReservationResponseAssembler reservationResponseAssembler, ReservationValidator reservationValidator,
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

        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);
        if (restaurant.getCapacity() < reservationRequest.groupSize()) {
            throw new InvalidParameterException("The reservation groupSize cannot exceed the restaurant's capacity");
        }

        Customer customer = customerAssembler.fromDTO(reservationRequest.customer());
        Reservation reservation =
                reservationFactory.createReservation(restaurantId, reservationRequest.date(), reservationRequest.startTime(),
                        reservationRequest.groupSize(), customer);
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
            Restaurant restaurant = restaurantRepository.findRestaurantById(reservation.getRestaurantId());
            if (restaurant != null) {
                if (ReservationHelper.isMatchingCustomerName(reservation.getCustomer().getName(), customerName)
                        && ReservationHelper.isMatchingDate(reservation.getDate(), date)) {
                    getRestaurantValidator.validateRestaurantOwnership(ownerId, restaurant.getOwnerId());
                    searchedReservations.add(reservationGeneralResponseAssembler
                            .toDTO(reservation, restaurant));
                }
            } else {
                throw new NotFoundException("Restaurant not found for reservation with ID: " + reservation.getId());
            }
        }
        return searchedReservations;
    }
}
