package ca.ulaval.glo2003.controllers.api;

import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Application;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.inmemory.InMemoryTestContainerFactory;

public class JerseyTestApi {
    private final Application application;
    private JerseyTest api;

    public JerseyTestApi(Application application) {
        System.setProperty("jersey.test.host", "0.0.0.0");
        System.setProperty("jersey.config.test.container.port", "8080");
        this.application = application;
    }

    public void start() {
        if (api != null) return;

        api = new JerseyTest(new InMemoryTestContainerFactory()) {
            @Override
            public Application configure() {
                return JerseyTestApi.this.application;
            }
        };

        try {
            api.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            api.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WebTarget path(String path) {
        return api.target(path);
    }

    public WebTarget uri(String uri) {
        return api.client().target(uri);
    }
}
