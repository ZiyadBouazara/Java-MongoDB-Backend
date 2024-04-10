package ca.ulaval.glo2003.service.validators;

import jakarta.ws.rs.NotFoundException;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class GetRestaurantValidatorTest {
    private static final String EXPECTED_OWNER_ID = "owner123";
    private static final String UNEXPECTED_OWNER_ID = "owner321";

    @Test
    public void givenMatchingOwnerIds_whenValidateRestaurantOwnership_NoExceptionThrown() {
        GetRestaurantValidator validator = new GetRestaurantValidator();
        assertDoesNotThrow(() -> validator.validateRestaurantOwnership(EXPECTED_OWNER_ID, EXPECTED_OWNER_ID));
    }

    @Test
    public void givenMismatchingOwnerIds_whenValidateRestaurantOwnership_ShouldThrowNotFoundException() {
        GetRestaurantValidator validator = new GetRestaurantValidator();
        assertThrows(NotFoundException.class, () ->
                validator.validateRestaurantOwnership(EXPECTED_OWNER_ID, UNEXPECTED_OWNER_ID));
    }
}
