package ca.ulaval.glo2003.controllers.assemblers;

import ca.ulaval.glo2003.controllers.responses.RestaurantResponse;
import ca.ulaval.glo2003.controllers.responses.RestaurantResponseWithReviews;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.service.dtos.HoursDTO;
import ca.ulaval.glo2003.service.dtos.ReservationConfigurationDTO;


public class RestaurantResponseAssembler {

    public RestaurantResponse toDTO(Restaurant restaurant) {

        return new RestaurantResponse(
            restaurant.getId(),
            restaurant.getOwnerId(),
            restaurant.getName(),
            restaurant.getCapacity(),
            createHoursDTO(restaurant),
            createReservationConfigurationDTO(restaurant));
    }

    public RestaurantResponseWithReviews toDTOv2(Restaurant restaurant) {

        return new RestaurantResponseWithReviews(
                restaurant.getId(),
                restaurant.getOwnerId(),
                restaurant.getName(),
                restaurant.getCapacity(),
                createHoursDTO(restaurant),
                createReservationConfigurationDTO(restaurant),
                restaurant.getRating(),
                restaurant.getReviewCount());
    }

    private ReservationConfigurationDTO createReservationConfigurationDTO(Restaurant restaurant) {
        return new ReservationConfigurationDTO(restaurant.getRestaurantConfiguration().getDuration());
    }

    private HoursDTO createHoursDTO(Restaurant restaurant) {
        return new HoursDTO(restaurant.getHours().getOpen(), restaurant.getHours().getClose());
    }
}

