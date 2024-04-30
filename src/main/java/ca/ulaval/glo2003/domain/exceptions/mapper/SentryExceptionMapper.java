package ca.ulaval.glo2003.domain.exceptions.mapper;

import io.sentry.Sentry;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class SentryExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof NotFoundException) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Sentry.captureException(exception);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("An internal server error occurred.")
                .build();
    }
}
