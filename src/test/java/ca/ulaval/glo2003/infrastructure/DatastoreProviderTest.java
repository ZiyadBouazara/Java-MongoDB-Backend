package ca.ulaval.glo2003.infrastructure;

import dev.morphia.Datastore;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;


@ExtendWith(MockitoExtension.class)
public class DatastoreProviderTest {

    EnvironmentReader mockReader;
    Map<String, String> mockData;
    DatastoreProvider datastoreProvider;

    @BeforeEach
    public void setUp() {
        mockData = new HashMap<>();
    }

    @Test
    public void givenValidEnvVariables_whenProvidingDatastore_shouldReturnNonNullDatastore() {
        mockData.put("MONGO_CLUSTER_URL", "mongodb://root:example@localhost:27017/");
        mockData.put("MONGO_DATABASE", "test");
        mockReader = new MockSystemEnvReader(mockData);
        datastoreProvider = new DatastoreProvider(mockReader);

        Datastore datastore = datastoreProvider.provide();

        Assertions.assertNotNull(datastore);
    }

    @Test
    public void givenMissingEnvVariable_whenProvidingDatastore_shouldThrowIllegalStateException() {
        mockData.remove("MONGO_CLUSTER_URL");
        mockReader = new MockSystemEnvReader(mockData);
        datastoreProvider = new DatastoreProvider(mockReader);

        Assertions.assertThrows(IllegalStateException.class, () -> datastoreProvider.provide());
    }

    @Test
    public void givenEmptyEnvVariable_whenProvidingDatastore_shouldThrowIllegalStateException() {
        mockData.put("MONGO_CLUSTER_URL", "");
        mockReader = new MockSystemEnvReader(mockData);
        datastoreProvider = new DatastoreProvider(mockReader);

        Assertions.assertThrows(IllegalStateException.class, () -> datastoreProvider.provide());
    }
}
