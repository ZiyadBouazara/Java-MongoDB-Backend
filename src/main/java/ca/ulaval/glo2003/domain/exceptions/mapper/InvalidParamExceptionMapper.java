package ca.ulaval.glo2003.domain.exceptions.mapper;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import jakarta.ws.rs.core.Response;
import io.sentry.Sentry;
import jakarta.ws.rs.ext.ExceptionMapper;

public class InvalidParamExceptionMapper implements ExceptionMapper<InvalidParameterException> {

    @Override
    public Response toResponse(InvalidParameterException exception) {
        Sentry.captureException(exception);
        exception.printStackTrace();
        return Response.status(400).entity(new ErrorResponse(ErrorCode.INVALID_PARAMETER, exception.getMessage())).build();
    }
}
