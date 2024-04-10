package ca.ulaval.glo2003.service.validators;

import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HeaderValidatorTest {
    private HeaderValidator headerValidator;

    @BeforeEach
    void setUp() {
        headerValidator = new HeaderValidator();
    }

    @Test
    public void givenNullOwnerId_verifyMissingHeader_shouldThrowException() {
        assertThrows(MissingParameterException.class, () -> headerValidator.verifyMissingHeader(null));
    }
}
