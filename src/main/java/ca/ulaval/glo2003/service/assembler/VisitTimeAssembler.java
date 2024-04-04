package ca.ulaval.glo2003.service.assembler;

import ca.ulaval.glo2003.domain.utils.VisitTime;
import ca.ulaval.glo2003.service.dtos.VisitTimeDTO;

public class VisitTimeAssembler {

    public VisitTime fromDTO(VisitTimeDTO visitTimeDTO) {
        return new VisitTime(visitTimeDTO.from(), visitTimeDTO.to());
    }

    public VisitTimeDTO toDTO(VisitTime visitTime) {
        return new VisitTimeDTO(visitTime.getFrom(), visitTime.getTo());
    }
}
