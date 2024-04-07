package ca.ulaval.glo2003.controllers.assemblers;

import ca.ulaval.glo2003.controllers.responses.AvailabilitiesResponse;
import ca.ulaval.glo2003.domain.utils.Availabilities;

public class AvailabilitiesResponseAssembler {

    public AvailabilitiesResponse toDTO(Availabilities availabilities) {

        return new AvailabilitiesResponse(
                availabilities.getDate(),
                availabilities.getRemainingPlace());
    }
}
