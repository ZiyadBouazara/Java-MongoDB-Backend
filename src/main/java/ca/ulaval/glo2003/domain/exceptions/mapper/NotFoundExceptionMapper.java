package ca.ulaval.glo2003.domain.exceptions.mapper;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import io.sentry.Sentry;
import jakarta.ws.rs.ext.ExceptionMapper;

public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException e) {
        Sentry.captureException(e);
        e.printStackTrace();
        return Response.status(404).build();
    }
}
