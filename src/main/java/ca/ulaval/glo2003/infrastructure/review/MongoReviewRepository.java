package ca.ulaval.glo2003.infrastructure.review;

import ca.ulaval.glo2003.domain.repositories.ReviewRepository;
import ca.ulaval.glo2003.domain.review.Review;
import ca.ulaval.glo2003.infrastructure.DatastoreProvider;
import ca.ulaval.glo2003.infrastructure.assemblers.ReviewAssembler;
import dev.morphia.Datastore;
import jakarta.inject.Inject;

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
}
