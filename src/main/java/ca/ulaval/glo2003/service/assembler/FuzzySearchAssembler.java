package ca.ulaval.glo2003.service.assembler;

import ca.ulaval.glo2003.controllers.requests.FuzzySearchRequest;
import ca.ulaval.glo2003.domain.utils.FuzzySearch;
import ca.ulaval.glo2003.domain.utils.Hours;
import ca.ulaval.glo2003.service.dtos.HoursDTO;

public class FuzzySearchAssembler {

    VisitTimeAssembler visitTimeAssembler = new VisitTimeAssembler();
    public FuzzySearch fromDTO(FuzzySearchRequest fuzzySearchRequest) {
        return new FuzzySearch(fuzzySearchRequest.name(), visitTimeAssembler.fromDTO(fuzzySearchRequest.visitTime()));
    }
}
