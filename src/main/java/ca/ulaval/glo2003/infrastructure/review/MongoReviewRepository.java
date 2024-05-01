package ca.ulaval.glo2003.infrastructure.review;

import ca.ulaval.glo2003.domain.repositories.ReviewRepository;
import ca.ulaval.glo2003.domain.review.Review;
import ca.ulaval.glo2003.domain.review.ReviewMongo;
import ca.ulaval.glo2003.infrastructure.DatastoreProvider;
import ca.ulaval.glo2003.infrastructure.assemblers.ReviewAssembler;
import dev.morphia.Datastore;
import dev.morphia.query.Query;
import dev.morphia.query.filters.Filters;
import jakarta.inject.Inject;

import java.util.List;

public class MongoReviewRepository implements ReviewRepository {
    private final Datastore datastore;

    @Inject
    public MongoReviewRepository(DatastoreProvider datastoreProvider) {
        this.datastore = datastoreProvider.provide();
    }
    @Override
    public void save(Review review) {
        datastore.save(ReviewAssembler.toReviewMongo(review));
    }

    @Override
    public List<Review> getAllReviews(String restaurantId) {
        Query<ReviewMongo> query = datastore.find(ReviewMongo.class).filter(Filters.eq("restaurantId", restaurantId));
        List<ReviewMongo> reviewMongo = query.iterator().toList();
        return ReviewAssembler.fromReviewMongoList(reviewMongo);
    }
}
