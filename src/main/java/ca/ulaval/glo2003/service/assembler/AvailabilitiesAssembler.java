package ca.ulaval.glo2003.service.assembler;

import ca.ulaval.glo2003.domain.utils.Availabilities;
import ca.ulaval.glo2003.service.dtos.AvailabilitiesDTO;

public class AvailabilitiesAssembler {

    public Availabilities fromDTO(AvailabilitiesDTO availabilitiesDTO) {
        return new Availabilities(availabilitiesDTO.date(), availabilitiesDTO.remainingPlace());
    }

    public AvailabilitiesDTO toDTO(Availabilities availabilities) {
        return new AvailabilitiesDTO(availabilities.getDate(), availabilities.getRemainingPlace());
    }
}
