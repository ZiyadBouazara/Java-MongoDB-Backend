package ca.ulaval.glo2003.injection;

import ca.ulaval.glo2003.controllers.assemblers.FuzzySearchResponseAssembler;
import ca.ulaval.glo2003.controllers.assemblers.ReservationResponseAssembler;
import ca.ulaval.glo2003.controllers.assemblers.RestaurantResponseAssembler;
import ca.ulaval.glo2003.service.validators.CreateRestaurantValidator;
import ca.ulaval.glo2003.service.validators.ReservationValidator;
import ca.ulaval.glo2003.service.validators.GetRestaurantValidator;
import ca.ulaval.glo2003.service.validators.HeaderValidator;
import ca.ulaval.glo2003.service.validators.SearchRestaurantValidator;
import ca.ulaval.glo2003.domain.repositories.RestaurantAndReservationRepository;
import ca.ulaval.glo2003.domain.reservation.ReservationFactory;
import ca.ulaval.glo2003.domain.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.infrastructure.InMemoryRestaurantAndReservationRepository;
import ca.ulaval.glo2003.service.ReservationService;
import ca.ulaval.glo2003.service.RestaurantService;
import ca.ulaval.glo2003.service.assembler.CustomerAssembler;
import ca.ulaval.glo2003.service.assembler.FuzzySearchAssembler;
import ca.ulaval.glo2003.service.assembler.HoursAssembler;
import ca.ulaval.glo2003.service.assembler.VisitTimeAssembler;
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
        bind(ReservationValidator.class).to(ReservationValidator.class);
        bind(GetRestaurantValidator.class).to(GetRestaurantValidator.class);
        bind(SearchRestaurantValidator.class).to(SearchRestaurantValidator.class);
        bind(RestaurantFactory.class).to(RestaurantFactory.class);
        bind(HoursAssembler.class).to(HoursAssembler.class);
        bind(FuzzySearchResponseAssembler.class).to(FuzzySearchResponseAssembler.class);
        bind(FuzzySearchAssembler.class).to(FuzzySearchAssembler.class);
        bind(VisitTimeAssembler.class).to(VisitTimeAssembler.class);
        bind(CustomerAssembler.class).to(CustomerAssembler.class);
        bind(ReservationFactory.class).to(ReservationFactory.class);
        bind(RestaurantResponseAssembler.class).to(RestaurantResponseAssembler.class);
        bind(ReservationResponseAssembler.class).to(ReservationResponseAssembler.class);
    }
}
