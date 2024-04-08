package ca.ulaval.glo2003.infrastructure.restaurant;

import ca.ulaval.glo2003.domain.repositories.RestaurantRepository;
import ca.ulaval.glo2003.domain.repositories.RestaurantRepositoryTest;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class MongoRestaurantRepositoryTest extends RestaurantRepositoryTest {
    @Container
    private final MongoDBContainer mongoInstance = new MongoDBContainer("mongo:7.0");

    @Override
    protected RestaurantRepository createRepository() {
        System.out.println(mongoInstance.getConnectionString());
        var mongoUrl = MongoClients.create(mongoInstance.getConnectionString());
        Datastore datastore = Morphia.createDatastore(mongoUrl, "tests");
        return new MongoRestaurantRepository(datastore);
    }
}
