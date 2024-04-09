package ca.ulaval.glo2003.infrastructure;

import com.mongodb.ConnectionString;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import jakarta.inject.Inject;

public class DatastoreProvider {
    private final EnvironmentReader reader;

    @Inject
    public DatastoreProvider(EnvironmentReader reader) {
        this.reader = reader;
    }
    public Datastore provide() {
        String mongoClusterUrl = getRequiredEnvVar("MONGO_CLUSTER_URL");
        String mongoDatabaseName = getRequiredEnvVar("MONGO_DATABASE");

        return createDatastore(mongoClusterUrl, mongoDatabaseName);
    }

    private Datastore createDatastore(String mongoClusterUrl, String mongoDatabaseName) {
        try {
            ConnectionString connectionString = new ConnectionString(mongoClusterUrl);
            MongoClient mongoClient = MongoClients.create(connectionString);

            return Morphia.createDatastore(mongoClient, mongoDatabaseName);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error creating connection string: " + e.getMessage());
        } catch (MongoException e) {
            throw new RuntimeException("Error connecting to MongoDB: " + e.getMessage());
        }
    }
    private String getRequiredEnvVar(String name) {
        String value = reader.getenv(name);
        if (value == null || value.isEmpty()) {
            throw new IllegalStateException(name + " environment variable is not set.");
        }
        return value;
    }
}
