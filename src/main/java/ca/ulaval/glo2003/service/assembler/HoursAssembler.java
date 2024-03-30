package ca.ulaval.glo2003.service.assembler;

import ca.ulaval.glo2003.service.dtos.HoursDTO;
import ca.ulaval.glo2003.domain.utils.Hours;

public class HoursAssembler {
    public Hours fromDTO(HoursDTO hoursDTO) {
        return new Hours(hoursDTO.open(), hoursDTO.close());
    }

    public HoursDTO toDTO(Hours hours) {
        return new HoursDTO(hours.getOpen(), hours.getClose());
    }
}
