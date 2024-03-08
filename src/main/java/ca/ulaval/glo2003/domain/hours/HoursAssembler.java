package ca.ulaval.glo2003.domain.hours;

import ca.ulaval.glo2003.controllers.models.HoursDTO;

public class HoursAssembler {
    public Hours fromDTO(HoursDTO hoursDTO) {
        return new Hours(hoursDTO.open(), hoursDTO.close());
    }
}
