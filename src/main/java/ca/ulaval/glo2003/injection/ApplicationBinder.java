package ca.ulaval.glo2003.injection;

import ca.ulaval.glo2003.controllers.validators.CreateReservationValidator;
import ca.ulaval.glo2003.controllers.validators.CreateRestaurantValidator;
import ca.ulaval.glo2003.controllers.validators.GetRestaurantValidator;
import ca.ulaval.glo2003.controllers.validators.HeaderValidator;
import ca.ulaval.glo2003.domain.repositories.RestaurantAndReservationRepository;
import ca.ulaval.glo2003.domain.reservation.ReservationFactory;
import ca.ulaval.glo2003.domain.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.infrastructure.InMemoryRestaurantAndReservationRepository;
import ca.ulaval.glo2003.service.ReservationService;
import ca.ulaval.glo2003.service.RestaurantService;
import ca.ulaval.glo2003.service.assembler.CustomerAssembler;
import ca.ulaval.glo2003.service.assembler.HoursAssembler;
import jakarta.inject.Singleton;
import org.glassfish.jersey.internal.inject.AbstractBinder;


public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(InMemoryRestaurantAndReservationRepository.class)
                .to(RestaurantAndReservationRepository.class)
                .in(Singleton.class);
        bind(RestaurantService.class).to(RestaurantService.class);
        bind(ReservationService.class).to(ReservationService.class);

        bind(HeaderValidator.class).to(HeaderValidator.class);
        bind(CreateRestaurantValidator.class).to(CreateRestaurantValidator.class);
        bind(CreateReservationValidator.class).to(CreateReservationValidator.class);
        bind(GetRestaurantValidator.class).to(GetRestaurantValidator.class);
        bind(RestaurantFactory.class).to(RestaurantFactory.class);
        bind(HoursAssembler.class).to(HoursAssembler.class);
        bind(CustomerAssembler.class).to(CustomerAssembler.class);
        bind(ReservationFactory.class).to(ReservationFactory.class);
    }
}
