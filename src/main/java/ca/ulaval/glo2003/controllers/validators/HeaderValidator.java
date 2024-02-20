package ca.ulaval.glo2003.controllers.validators;

import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;

public class HeaderValidator {
    public void verifyMissingHeader(String ownerId) throws MissingParameterException {
        if (ownerId == null) {
            throw new MissingParameterException("Missing 'Owner' header");
        }
    }
}
