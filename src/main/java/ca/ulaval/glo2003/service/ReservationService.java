package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.assemblers.ReservationResponseAssembler;
import ca.ulaval.glo2003.controllers.requests.ReservationRequest;
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
import ca.ulaval.glo2003.service.validators.ReservationValidator;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationService {
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationFactory reservationFactory;
    private final CustomerAssembler customerAssembler;
    private final ReservationResponseAssembler reservationResponseAssembler;
    private final ReservationValidator reservationValidator;

    @Inject
    public ReservationService(RestaurantRepository restaurantRepository, ReservationRepository reservationRepository,
                              ReservationFactory reservationFactory, CustomerAssembler customerAssembler,
                              ReservationResponseAssembler reservationResponseAssembler, ReservationValidator reservationValidator) {
        this.restaurantRepository = restaurantRepository;
        this.reservationRepository = reservationRepository;
        this.reservationFactory = reservationFactory;
        this.customerAssembler = customerAssembler;
        this.reservationResponseAssembler = reservationResponseAssembler;
        this.reservationValidator = reservationValidator;
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

    public ReservationResponse getReservation(String reservationId) {
        Reservation reservation = reservationRepository.findReservationById(reservationId);
        Restaurant restaurant = restaurantRepository.findRestaurantById(reservation.getRestaurantId());
        return reservationResponseAssembler.toDTO(reservation, restaurant);
    }

    //TODO: remake this method after DB changes
    /*public List<ReservationResponse> searchReservation(String ownerId, String restaurantId, String date, String customerName) {
        List<Reservation> reservations = restaurantAndReservationRepository.getAllReservationsByRestaurant(restaurantId);
        List<Reservation> filteredReservations = reservations.stream()
                .filter(reservation -> reservation.getDate().equals(date))
                .filter(reservation -> reservation.getCustomer().getName().equals(customerName))
                .collect(Collectors.toList());

        return filteredReservations.stream()
                .map(reservation -> reservationResponseAssembler.toDTO(reservation, restaurantAndReservationRepository.findRestaurantByReservationId(reservation.getId())))
                .collect(Collectors.toList());
    }*/
}
