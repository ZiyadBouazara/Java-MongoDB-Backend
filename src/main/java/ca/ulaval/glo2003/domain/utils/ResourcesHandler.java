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

    public List<FuzzySearchResponse> getAllRestaurantsForSearch(FuzzySearch search) {
        List<FuzzySearchResponse> searchedRestaurants = new ArrayList<>();

        for (Restaurant restaurant : restaurants.values()) {
            boolean isMatchRestaurantName;
            boolean isMatchRestaurantOpenHour;
            boolean isMatchRestaurantCloseHour;

            if (search.getName() != null) {
                isMatchRestaurantName = FuzzySearch.isFuzzySearchOnNameSuccessful(search.getName(), restaurant.getName());
            } else {
                isMatchRestaurantName = true;
            }

            if (search.getVisitTime() != null) {
                isMatchRestaurantOpenHour = FuzzySearch.isFromTimeMatching(search.getVisitTime().getFrom(), restaurant.getHours().getOpen());
                isMatchRestaurantCloseHour = FuzzySearch.isToTimeMatching(search.getVisitTime().getTo(), restaurant.getHours().getClose());
            } else {
                isMatchRestaurantOpenHour = true;
                isMatchRestaurantCloseHour = true;
            }

            if (isMatchRestaurantName && isMatchRestaurantOpenHour && isMatchRestaurantCloseHour) {
                searchedRestaurants.add(new FuzzySearchResponse(restaurant));
            }
        }
        return searchedRestaurants;
    }
}
