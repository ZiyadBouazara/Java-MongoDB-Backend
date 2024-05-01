package ca.ulaval.glo2003.infrastructure.review;

import ca.ulaval.glo2003.domain.repositories.ReviewRepositoryTest;
import ca.ulaval.glo2003.infrastructure.DatastoreProvider;
import com.mongodb.client.MongoClients;
import dev.morphia.Morphia;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Testcontainers
public class MongoReviewRepositoryTest extends ReviewRepositoryTest {

    @Container
    private final MongoDBContainer mongoInstance = new MongoDBContainer("mongo");

    @Override
    protected MongoReviewRepository createRepository() {
        DatastoreProvider mockProvider = configureTestDataStoreProvider();
        return new MongoReviewRepository(mockProvider);
    }

    private DatastoreProvider configureTestDataStoreProvider() {
        String connectionString = mongoInstance.getConnectionString();
        DatastoreProvider mockProvider = mock(DatastoreProvider.class);
        var mongoUrl = MongoClients.create(connectionString);
        when(mockProvider.provide()).thenReturn(Morphia.createDatastore(mongoUrl, "tests"));
        return mockProvider;
    }

    @BeforeEach
    public void setUp() {
        super.setUp();
    }
}
