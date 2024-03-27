package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.assemblers.ReservationResponseAssembler;
import ca.ulaval.glo2003.controllers.responses.ReservationResponse;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.service.dtos.CustomerDTO;
import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.service.assembler.CustomerAssembler;
import ca.ulaval.glo2003.domain.repositories.RestaurantAndReservationRepository;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationFactory;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

public class ReservationService {
    private final RestaurantAndReservationRepository restaurantAndReservationRepository;
    private final ReservationFactory reservationFactory;
    private final CustomerAssembler customerAssembler;
    private final ReservationResponseAssembler reservationResponseAssembler;
    @Inject
    public ReservationService(RestaurantAndReservationRepository restaurantAndReservationRepository,
                              ReservationFactory reservationFactory,
                              CustomerAssembler customerAssembler, ReservationResponseAssembler reservationResponseAssembler) {
        this.restaurantAndReservationRepository = restaurantAndReservationRepository;
        this.reservationFactory = reservationFactory;
        this.customerAssembler = customerAssembler;
        this.reservationResponseAssembler = reservationResponseAssembler;
    }

    public String createReservation(
            String restaurantId,
            String date,
            String startTime,
            Integer groupSize,
            CustomerDTO customerDTO) throws InvalidParameterException {

        Restaurant restaurant = restaurantAndReservationRepository.findRestaurantByRestaurantId(restaurantId);
        if (restaurant.getCapacity() < groupSize) {
            throw new InvalidParameterException("The reservation groupSize cannot exceed the restaurant's capacity");
        }

        Customer customer = customerAssembler.fromDTO(customerDTO);
        Reservation reservation = reservationFactory.createReservation(restaurantId, date, startTime, groupSize, customer);
        restaurantAndReservationRepository.saveReservation(reservation);
        return reservation.getId();
    }

    public ReservationResponse getReservation(String reservationId) {
        Restaurant restaurant = restaurantAndReservationRepository.findRestaurantByReservationId(reservationId);
        if (restaurant == null) {
            throw new NotFoundException("Restaurant associated with reservation ID " + reservationId + " was not found");
        }

        Reservation reservation = restaurant.getReservationsById().get(reservationId);
        if (reservation == null) {
            throw new NotFoundException("Reservation with ID " + reservationId + " was not found");
        }

        return reservationResponseAssembler.toDTO(reservation, restaurant);
    }
}
