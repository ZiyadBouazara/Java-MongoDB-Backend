package ca.ulaval.glo2003.infrastructure.restaurant;

import ca.ulaval.glo2003.domain.repositories.RestaurantRepository;
import ca.ulaval.glo2003.domain.repositories.RestaurantRepositoryTest;
import ca.ulaval.glo2003.infrastructure.DatastoreProvider;
import com.mongodb.client.MongoClients;
import dev.morphia.Morphia;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Testcontainers
class MongoRestaurantRepositoryTest extends RestaurantRepositoryTest {
    @Container
    private final MongoDBContainer mongoInstance = new MongoDBContainer("mongo:7.0");

    @Override
    protected RestaurantRepository createRepository() {
        DatastoreProvider mockProvider = configureTestDataStoreProvider();
        return new MongoRestaurantRepository(mockProvider);
    }

    private DatastoreProvider configureTestDataStoreProvider() {
        String connectionString = mongoInstance.getConnectionString();
        DatastoreProvider mockProvider = mock(DatastoreProvider.class);
        var mongoUrl = MongoClients.create(connectionString);
        when(mockProvider.provide()).thenReturn(Morphia.createDatastore(mongoUrl, "tests"));
        return mockProvider;
    }
}
