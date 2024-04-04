package ca.ulaval.glo2003.service.assembler;

import ca.ulaval.glo2003.controllers.requests.FuzzySearchRequest;
import ca.ulaval.glo2003.domain.utils.FuzzySearch;

public class FuzzySearchAssembler {

    VisitTimeAssembler visitTimeAssembler = new VisitTimeAssembler();
    public FuzzySearch fromDTO(FuzzySearchRequest fuzzySearchRequest) {
        return new FuzzySearch(fuzzySearchRequest.name(), visitTimeAssembler.fromDTO(fuzzySearchRequest.opened()));
    }
}
