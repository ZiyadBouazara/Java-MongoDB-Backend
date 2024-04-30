package ca.ulaval.glo2003.infrastructure.assemblers;

import ca.ulaval.glo2003.domain.restaurant.ReservationConfiguration;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantMongo;
import ca.ulaval.glo2003.domain.utils.Hours;

import java.util.ArrayList;
import java.util.List;


public class RestaurantAssembler {

    public static RestaurantMongo toRestaurantMongo(Restaurant restaurant) {

        return new RestaurantMongo(restaurant.getId(), restaurant.getOwnerId(), restaurant.getName(), restaurant.getCapacity(),
            restaurant.getHours().getOpen(), restaurant.getHours().getClose(), restaurant.getRestaurantConfiguration().getDuration(),
                restaurant.getRating(), restaurant.getReviewCount());
    }

    public static Restaurant fromRestaurantMongo(RestaurantMongo restaurantMongo) {

        return new Restaurant(restaurantMongo.getId(), restaurantMongo.getOwnerId(), restaurantMongo.getName(),
            restaurantMongo.getCapacity(),
            new Hours(restaurantMongo.getOpenHour(), restaurantMongo.getCloseHour()),
            new ReservationConfiguration(restaurantMongo.getDuration()),
            restaurantMongo.getRating(),
            restaurantMongo.getReviewCount());
    }

    public static List<Restaurant> fromRestaurantMongoList(List<RestaurantMongo> restaurantMongoList) {
        List<Restaurant> restaurants = new ArrayList<>();
        for (RestaurantMongo restaurantMongo : restaurantMongoList) {
            restaurants.add(fromRestaurantMongo(restaurantMongo));
        }
        return restaurants;
    }
}
