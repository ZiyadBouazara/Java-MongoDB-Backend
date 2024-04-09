package ca.ulaval.glo2003.infrastructure;

import java.util.Map;

public class MockSystemEnvReader implements EnvironmentReader {
    private Map<String, String> mockValues;

    public MockSystemEnvReader(Map<String, String> mockValues) {
        this.mockValues = mockValues;
    }

    @Override
    public String getenv(String name) {
        return mockValues.get(name);
    }
}
