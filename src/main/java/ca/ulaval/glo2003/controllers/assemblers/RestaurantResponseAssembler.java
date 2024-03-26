package ca.ulaval.glo2003.controllers.assemblers;

import ca.ulaval.glo2003.controllers.responses.RestaurantResponse;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.service.dtos.HoursDTO;
import ca.ulaval.glo2003.service.dtos.ReservationConfigurationDTO;

public class RestaurantResponseAssembler {

    public RestaurantResponse toDTO(Restaurant restaurant) {
        HoursDTO hoursDTO = new HoursDTO(restaurant.getHours().getOpen(), restaurant.getHours().getClose());
        ReservationConfigurationDTO reservationConfigurationDTO = new ReservationConfigurationDTO(restaurant.getRestaurantConfiguration().getDuration());
        return new RestaurantResponse(
            restaurant.getId(),
            restaurant.getOwnerId(),
            restaurant.getName(),
            restaurant.getCapacity(),
            hoursDTO,
            reservationConfigurationDTO);
    }
}
