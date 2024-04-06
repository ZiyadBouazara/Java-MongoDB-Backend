package ca.ulaval.glo2003.injection;

import ca.ulaval.glo2003.controllers.assemblers.FuzzySearchResponseAssembler;
import ca.ulaval.glo2003.controllers.assemblers.ReservationGeneralResponseAssembler;
import ca.ulaval.glo2003.controllers.assemblers.ReservationResponseAssembler;
import ca.ulaval.glo2003.controllers.assemblers.RestaurantResponseAssembler;
import ca.ulaval.glo2003.domain.repositories.ReservationRepository;
import ca.ulaval.glo2003.domain.repositories.RestaurantRepository;
import ca.ulaval.glo2003.infrastructure.DatastoreProvider;
import ca.ulaval.glo2003.infrastructure.Reservation.InMemoryReservationRepository;
import ca.ulaval.glo2003.infrastructure.Restaurant.InMemoryRestaurantRepository;
import ca.ulaval.glo2003.infrastructure.Reservation.MongoReservationRepository;
import ca.ulaval.glo2003.infrastructure.Restaurant.MongoRestaurantRepository;
import ca.ulaval.glo2003.service.validators.CreateRestaurantValidator;
import ca.ulaval.glo2003.service.validators.ReservationValidator;
import ca.ulaval.glo2003.service.validators.GetRestaurantValidator;
import ca.ulaval.glo2003.service.validators.HeaderValidator;
import ca.ulaval.glo2003.service.validators.SearchRestaurantValidator;
import ca.ulaval.glo2003.domain.reservation.ReservationFactory;
import ca.ulaval.glo2003.domain.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.service.ReservationService;
import ca.ulaval.glo2003.service.RestaurantService;
import ca.ulaval.glo2003.service.assembler.CustomerAssembler;
import ca.ulaval.glo2003.service.assembler.FuzzySearchAssembler;
import ca.ulaval.glo2003.service.assembler.HoursAssembler;
import ca.ulaval.glo2003.service.assembler.VisitTimeAssembler;
import dev.morphia.Datastore;
import jakarta.inject.Singleton;
import org.glassfish.jersey.internal.inject.AbstractBinder;


public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        choosePersistenceType();
        bind(RestaurantService.class).to(RestaurantService.class);
        bind(ReservationService.class).to(ReservationService.class);

        bind(HeaderValidator.class).to(HeaderValidator.class);
        bind(CreateRestaurantValidator.class).to(CreateRestaurantValidator.class);
        bind(ReservationValidator.class).to(ReservationValidator.class);
        bind(GetRestaurantValidator.class).to(GetRestaurantValidator.class);
        bind(SearchRestaurantValidator.class).to(SearchRestaurantValidator.class);
        bind(RestaurantFactory.class).to(RestaurantFactory.class);
        bind(ReservationFactory.class).to(ReservationFactory.class);
        bind(HoursAssembler.class).to(HoursAssembler.class);
        bind(FuzzySearchResponseAssembler.class).to(FuzzySearchResponseAssembler.class);
        bind(FuzzySearchAssembler.class).to(FuzzySearchAssembler.class);
        bind(VisitTimeAssembler.class).to(VisitTimeAssembler.class);
        bind(CustomerAssembler.class).to(CustomerAssembler.class);
        bind(RestaurantResponseAssembler.class).to(RestaurantResponseAssembler.class);
        bind(ReservationResponseAssembler.class).to(ReservationResponseAssembler.class);
        bind(ReservationGeneralResponseAssembler.class).to(ReservationGeneralResponseAssembler.class);
    }

    private void choosePersistenceType() {
        String persistence = System.getProperty("persistence");
        if (persistence != null && persistence.equals("mongo")) {
            DatastoreProvider provider = new DatastoreProvider();
            Datastore datastore = provider.provide();

            bind(new MongoRestaurantRepository(datastore)).to(RestaurantRepository.class).in(Singleton.class);
            bind(new MongoReservationRepository(datastore)).to(ReservationRepository.class).in(Singleton.class);
        } else {
            bind(InMemoryRestaurantRepository.class).to(RestaurantRepository.class).in(Singleton.class);
            bind(InMemoryReservationRepository.class).to(ReservationRepository.class).in(Singleton.class);
        }
    }
}
