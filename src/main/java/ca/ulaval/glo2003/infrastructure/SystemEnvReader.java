package ca.ulaval.glo2003.infrastructure;

public class SystemEnvReader implements EnvironmentReader {
    @Override
    public String getenv(String name) {
        return System.getenv(name);
    }
}
