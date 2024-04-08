package ca.ulaval.glo2003.infrastructure.reservation;

import ca.ulaval.glo2003.domain.repositories.ReservationRepository;
import ca.ulaval.glo2003.domain.repositories.ReservationRepositoryTest;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class MongoReservationRepositoryTest extends ReservationRepositoryTest {
    @Container
    private final MongoDBContainer mongoInstance = new MongoDBContainer("mongo:7.0");

    @Override
    protected ReservationRepository createRepository() {
        System.out.println(mongoInstance.getConnectionString());
        var mongoUrl = MongoClients.create(mongoInstance.getConnectionString());
        Datastore datastore = Morphia.createDatastore(mongoUrl, "tests");
        return new MongoReservationRepository(datastore);
    }

}
