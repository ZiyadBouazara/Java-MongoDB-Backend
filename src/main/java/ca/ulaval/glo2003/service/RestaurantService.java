package ca.ulaval.glo2003.service;

import ca.ulaval.glo2003.controllers.assemblers.RestaurantResponseAssembler;
import ca.ulaval.glo2003.service.dtos.HoursDTO;
import ca.ulaval.glo2003.service.dtos.ReservationConfigurationDTO;
import ca.ulaval.glo2003.controllers.responses.RestaurantResponse;
import ca.ulaval.glo2003.service.assembler.HoursAssembler;
import ca.ulaval.glo2003.domain.repositories.RestaurantAndReservationRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.domain.hours.Hours;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantService {

    private final RestaurantAndReservationRepository restaurantAndReservationRepository;
    private final RestaurantFactory restaurantFactory;
    private final HoursAssembler hoursAssembler;
    private final RestaurantResponseAssembler restaurantResponseAssembler;

    @Inject
    public RestaurantService(RestaurantAndReservationRepository restaurantAndReservationRepository,
                             RestaurantFactory restaurantFactory, HoursAssembler hoursAssembler,
                             RestaurantResponseAssembler restaurantResponseAssembler) {

        this.restaurantAndReservationRepository = restaurantAndReservationRepository;
        this.restaurantFactory = restaurantFactory;
        this.hoursAssembler = hoursAssembler;
        this.restaurantResponseAssembler = restaurantResponseAssembler;
    }

    public String createRestaurant(String ownerId,
                                   String name,
                                   Integer capacity,
                                   HoursDTO hoursDto,
                                   ReservationConfigurationDTO reservationsDuration) {
        Hours hours = hoursAssembler.fromDTO(hoursDto);
        Restaurant restaurant = restaurantFactory.createRestaurant(ownerId, name, capacity, hours, reservationsDuration);
        restaurantAndReservationRepository.saveRestaurant(restaurant);
        return restaurant.getId();
    }

    public List<RestaurantResponse> getRestaurantsForOwnerId(String ownerId) {
        List<Restaurant> ownerRestaurants = restaurantAndReservationRepository.findRestaurantsByOwnerId(ownerId);
        return ownerRestaurants.stream()
            .map(restaurantResponseAssembler::toDTO)
            .collect(Collectors.toList());
    }

    public RestaurantResponse getRestaurant(String restaurantId) {
        Restaurant restaurant = restaurantAndReservationRepository.findRestaurantByRestaurantId(restaurantId);
        if (restaurant == null) {
            throw new NotFoundException("Restaurant with ID " + restaurantId + " not found");
        }
        return restaurantResponseAssembler.toDTO(restaurant);
    }
}
