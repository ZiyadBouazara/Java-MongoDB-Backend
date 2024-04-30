package ca.ulaval.glo2003.domain.exceptions.mapper;

import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import jakarta.ws.rs.core.Response;
import io.sentry.Sentry;
import jakarta.ws.rs.ext.ExceptionMapper;

public class MissingParamExceptionMapper implements ExceptionMapper<MissingParameterException> {

    @Override
    public Response toResponse(MissingParameterException exception) {
        Sentry.captureException(exception);
        exception.printStackTrace();
        return Response.status(400).entity(new ErrorResponse(ErrorCode.MISSING_PARAMETER, exception.getMessage())).build();
    }
}
