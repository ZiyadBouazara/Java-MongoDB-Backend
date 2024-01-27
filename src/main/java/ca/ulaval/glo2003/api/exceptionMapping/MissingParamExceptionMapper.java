package ca.ulaval.glo2003.api.exceptionMapping;

import ca.ulaval.glo2003.domain.MissingParameterException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class MissingParamExceptionMapper implements ExceptionMapper<MissingParameterException> {

    @Override
    public Response toResponse(MissingParameterException exception){
        exception.printStackTrace();
        return Response.status(400).entity(new ErrorResponse(ErrorCode.MISSING_PARAMETER, exception.getMessage())).build();
    }
}
