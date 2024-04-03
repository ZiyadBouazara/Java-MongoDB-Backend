package ca.ulaval.glo2003.controllers.assemblers;

import ca.ulaval.glo2003.controllers.responses.FuzzySearchResponse;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.service.assembler.HoursAssembler;

public class FuzzySearchResponseAssembler {

    HoursAssembler hoursAssembler = new HoursAssembler();

    public FuzzySearchResponse toDTO(Restaurant restaurant) {
        return new FuzzySearchResponse(restaurant.getId(), restaurant.getName(), restaurant.getCapacity(),
            hoursAssembler.toDTO(restaurant.getHours()));
    }
}
