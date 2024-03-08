package ca.ulaval.glo2003.service.assembler;

import ca.ulaval.glo2003.service.dtos.HoursDTO;
import ca.ulaval.glo2003.domain.hours.Hours;

public class HoursAssembler {
    public Hours fromDTO(HoursDTO hoursDTO) {
        return new Hours(hoursDTO.open(), hoursDTO.close());
    }
}
