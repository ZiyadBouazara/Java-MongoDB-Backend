package ca.ulaval.glo2003.infrastructure;

import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

public class DatastoreProvider {
    public Datastore provide() {
        String username = System.getenv("MONGO_USERNAME");
        String password = System.getenv("MONGO_PASSWORD");

        if (username == null || password == null) {
            throw new IllegalStateException("MONGO_USERNAME and MONGO_PASSWORD environment variables must be set.");
        }

        String mongoUrl = "mongodb://" + username + ":" + password + "@localhost:27017";
        return Morphia.createDatastore(MongoClients.create(mongoUrl), "restalo");
    }
}
