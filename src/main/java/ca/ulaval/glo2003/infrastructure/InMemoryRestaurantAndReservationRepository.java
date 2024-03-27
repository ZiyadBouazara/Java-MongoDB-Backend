package ca.ulaval.glo2003.infrastructure;

import ca.ulaval.glo2003.domain.repositories.RestaurantAndReservationRepository;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import jakarta.ws.rs.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryRestaurantAndReservationRepository implements RestaurantAndReservationRepository {
    private Map<String, Restaurant> restaurants;
    public InMemoryRestaurantAndReservationRepository() {
        this.restaurants = new HashMap<>();
    }

    @Override
    public void saveReservation(Reservation reservation) {
        String restaurantId = reservation.getRestaurantId();
        Restaurant restaurant = restaurants.get(restaurantId);

        restaurant.addReservation(reservation);
    }
    @Override
    public void saveRestaurant(Restaurant restaurant) throws NotFoundException {
        restaurants.put(restaurant.getId(), restaurant);
    }

    @Override
    public List<Restaurant> findRestaurantsByOwnerId(String ownerId) {
        return restaurants.values().stream()
                .filter(restaurant -> restaurant.getOwnerId().equals(ownerId))
                .collect(Collectors.toList());
    }

    @Override
    public Restaurant findRestaurantByRestaurantId(String restaurantId) {
        return restaurants.get(restaurantId);
    }

    @Override
    public Restaurant findRestaurantByReservationId(String reservationId) throws NotFoundException {
        Optional<Restaurant> foundRestaurant = restaurants.values().stream()
            .filter(restaurant -> restaurant.getReservationsById().containsKey(reservationId))
            .findFirst();

        return foundRestaurant.orElseThrow(NotFoundException::new);
    }
}
