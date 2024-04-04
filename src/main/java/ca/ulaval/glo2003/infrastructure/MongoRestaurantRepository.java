package ca.ulaval.glo2003.infrastructure;

import ca.ulaval.glo2003.domain.repositories.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantMongo;
import ca.ulaval.glo2003.infrastructure.assemblers.RestaurantAssembler;
import dev.morphia.Datastore;
import dev.morphia.query.Query;
import dev.morphia.query.filters.Filters;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

public class MongoRestaurantRepository implements RestaurantRepository {
    private final Datastore datastore;

    public MongoRestaurantRepository(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        RestaurantMongo restaurantMongo = RestaurantAssembler.toRestaurantMongo(restaurant);
        datastore.save(restaurantMongo);
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
            .filter(Filters.lte("id", restaurantId));
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
}
