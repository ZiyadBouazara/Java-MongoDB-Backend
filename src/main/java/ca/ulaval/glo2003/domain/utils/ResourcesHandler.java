package ca.ulaval.glo2003.domain.utils;

import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.models.FuzzySearchResponse;
import ca.ulaval.glo2003.models.RestaurantResponse;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ResourcesHandler {
    private final Map<String, Restaurant> restaurants;

    public ResourcesHandler() {
        this.restaurants = new HashMap<>();
    }

    public void addRestaurant(Restaurant restaurant) throws NotFoundException {
        restaurants.put(restaurant.getId(), restaurant);
    }

    public Restaurant getRestaurant(String restaurantId) throws NotFoundException {
        Restaurant restaurant = restaurants.get(restaurantId);
        if (restaurant == null) throw new NotFoundException();
        return restaurant;
    }

    public List<RestaurantResponse> getAllRestaurantsForOwner(String ownerId) {
        List<RestaurantResponse> ownerRestaurants = new ArrayList<>();
        for (Restaurant restaurant : restaurants.values()) {
            if (restaurant.getOwnerId().equals(ownerId)) {
                ownerRestaurants.add(new RestaurantResponse(restaurant));
            }
        }
        return ownerRestaurants;
    }

    public void addReservation(Reservation reservation) throws NotFoundException {
        String restaurantId = reservation.getRestaurantId();
        Restaurant restaurant = restaurants.get(restaurantId);

        restaurant.addReservation(reservation);
    }

    public Reservation getReservation(String reservationId) throws NotFoundException {
        for (Restaurant restaurant : restaurants.values()) {
            var reservationIds = restaurant.getReservationsById();
            for (String number : reservationIds.keySet()) {
                if (Objects.equals(number, reservationId))
                    return reservationIds.get(reservationId);
            }
        }
        throw new NotFoundException();
    }

    public List<FuzzySearchResponse> getAllRestaurantsForSearch(FuzzySearch search) {
        List<FuzzySearchResponse> searchedRestaurants = new ArrayList<>();

        for (Restaurant restaurant : restaurants.values()) {
            if (shouldMatchRestaurantName(search, restaurant) &&
                shouldMatchRestaurantHours(search, restaurant)) {
                searchedRestaurants.add(getFuzzySearchResponseForRestaurant(restaurant));
            }
        }

        return searchedRestaurants;
    }

    private boolean shouldMatchRestaurantName(FuzzySearch search, Restaurant restaurant) {
        return search.getName() == null || FuzzySearch.isFuzzySearchOnNameSuccessful(search.getName(), restaurant.getName());
    }

    private boolean shouldMatchRestaurantHours(FuzzySearch search, Restaurant restaurant) {
        if (search.getHours() == null) {
            return true;
        }

        return FuzzySearch.isFromTimeMatching(search.getHours().getFrom(), restaurant.getHours().getOpen()) &&
            FuzzySearch.isToTimeMatching(search.getHours().getTo(), restaurant.getHours().getClose());
    }

    private FuzzySearchResponse getFuzzySearchResponseForRestaurant(Restaurant restaurant) {
        return new FuzzySearchResponse(restaurant);
    }

}
