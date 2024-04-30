package ca.ulaval.glo2003.infrastructure.restaurant;

import ca.ulaval.glo2003.domain.repositories.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantMongo;
import ca.ulaval.glo2003.domain.review.Review;
import ca.ulaval.glo2003.infrastructure.DatastoreProvider;
import ca.ulaval.glo2003.infrastructure.assemblers.RestaurantAssembler;
import com.mongodb.client.result.DeleteResult;
import dev.morphia.Datastore;
import dev.morphia.DeleteOptions;
import dev.morphia.query.Query;
import dev.morphia.query.filters.Filters;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

public class MongoRestaurantRepository implements RestaurantRepository {
    private final Datastore datastore;

    @Inject
    public MongoRestaurantRepository(DatastoreProvider datastoreProvider) {
        this.datastore = datastoreProvider.provide();
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        RestaurantMongo restaurantMongo = RestaurantAssembler.toRestaurantMongo(restaurant);
        datastore.save(restaurantMongo);
    }

    @Override
    public void deleteRestaurant(String ownerId, String restaurantId) throws NotFoundException {
        DeleteResult deleteResult = datastore.find(RestaurantMongo.class)
            .filter(Filters.eq("ownerId", ownerId))
            .filter(Filters.eq("id", restaurantId))
            .delete(new DeleteOptions());

        if (deleteResult.getDeletedCount() == 0) {
            throw new NotFoundException("Restaurant to delete not found with restaurantId:" + restaurantId);
        }
    }

    @Override
    public List<Restaurant> findRestaurantsByOwnerId(String ownerId) {
        Query<RestaurantMongo> query = datastore.find(RestaurantMongo.class).filter(Filters.eq("ownerId", ownerId));
        List<RestaurantMongo> restaurantMongoList = query.iterator().toList();
        return RestaurantAssembler.fromRestaurantMongoList(restaurantMongoList);
    }

    @Override
    public Restaurant findRestaurantById(String restaurantId) throws NotFoundException {
        Query<RestaurantMongo> query = datastore.find(RestaurantMongo.class)
            .filter(Filters.eq("id", restaurantId));
        RestaurantMongo restaurantMongo = query.first();
        if (restaurantMongo == null) {
            throw new NotFoundException("No restaurant found for restaurant with ID: " + restaurantId);
        }
        return RestaurantAssembler.fromRestaurantMongo(restaurantMongo);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        List<RestaurantMongo> restaurantsMongo = datastore.find(RestaurantMongo.class).iterator().toList();
        return RestaurantAssembler.fromRestaurantMongoList(restaurantsMongo);
    }

    @Override
    public void updateReviews(Review review) {
        Restaurant updatedRestaurant = findRestaurantById(review.getRestaurantId());

        int totalReviews = updatedRestaurant.getReviewCount() + 1;
        Double currentRating = updatedRestaurant.getRating();

        Double updatedRating = getUpdatedRating(review, updatedRestaurant, totalReviews, currentRating);
        updatedRating = roundToTwoDecimals(updatedRating);
        updatedRestaurant.setRating(updatedRating);
        updatedRestaurant.incrementReviewCount();
        saveRestaurant(updatedRestaurant);
    }

    private Double getUpdatedRating(Review review, Restaurant restaurant, int totalReviews, Double currentRating) {
        return ((currentRating * restaurant.getReviewCount()) + review.getRating()) / totalReviews;
    }

    private Double roundToTwoDecimals(Double rating) {
        return Math.round(rating * 100.0) / 100.0;
    }
}
