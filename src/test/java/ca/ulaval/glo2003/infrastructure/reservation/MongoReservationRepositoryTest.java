package ca.ulaval.glo2003.infrastructure.reservation;

import ca.ulaval.glo2003.domain.repositories.ReservationRepository;
import ca.ulaval.glo2003.domain.repositories.ReservationRepositoryTest;
import ca.ulaval.glo2003.infrastructure.DatastoreProvider;
import com.mongodb.client.MongoClients;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static dev.morphia.Morphia.createDatastore;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Testcontainers
class MongoReservationRepositoryTest extends ReservationRepositoryTest {
    @Container
    private final MongoDBContainer mongoInstance = new MongoDBContainer("mongo:7.0");

    @Override
    protected ReservationRepository createRepository() {
        DatastoreProvider mockProvider = configureTestDataStoreProvider();
        return new MongoReservationRepository(mockProvider);
    }

    private DatastoreProvider configureTestDataStoreProvider() {
        String connectionString = mongoInstance.getConnectionString();
        DatastoreProvider mockProvider = mock(DatastoreProvider.class);
        var mongoUrl = MongoClients.create(connectionString);
        when(mockProvider.provide()).thenReturn(createDatastore(mongoUrl, "tests"));
        return mockProvider;
    }
}
