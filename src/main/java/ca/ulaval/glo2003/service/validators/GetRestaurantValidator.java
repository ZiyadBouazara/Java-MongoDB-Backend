package ca.ulaval.glo2003.service.validators;

import jakarta.ws.rs.NotFoundException;

public class GetRestaurantValidator {

    //TODO: Have one class for all validators associated with restaurants like in ReservationValidator
    public void validateRestaurantOwnership(String expectedOwnerId, String returnedRestaurantOwnerId) throws NotFoundException {
        if (!expectedOwnerId.equals(returnedRestaurantOwnerId)) {
            throw new NotFoundException("Cannot delete a restaurant if you are not the owner");
        }
    }
}
