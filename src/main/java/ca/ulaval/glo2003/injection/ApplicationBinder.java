package ca.ulaval.glo2003.injection;

import ca.ulaval.glo2003.controllers.assemblers.FuzzySearchResponseAssembler;
import ca.ulaval.glo2003.controllers.assemblers.ReservationGeneralResponseAssembler;
import ca.ulaval.glo2003.controllers.assemblers.ReservationResponseAssembler;
import ca.ulaval.glo2003.controllers.assemblers.RestaurantResponseAssembler;
import ca.ulaval.glo2003.controllers.assemblers.ReviewResponseAssembler;
import ca.ulaval.glo2003.domain.repositories.ReservationRepository;
import ca.ulaval.glo2003.domain.repositories.RestaurantRepository;
import ca.ulaval.glo2003.domain.repositories.ReviewRepository;
import ca.ulaval.glo2003.domain.review.ReviewFactory;
import ca.ulaval.glo2003.infrastructure.DatastoreProvider;
import ca.ulaval.glo2003.infrastructure.EnvironmentReader;
import ca.ulaval.glo2003.infrastructure.SystemEnvReader;
import ca.ulaval.glo2003.infrastructure.reservation.InMemoryReservationRepository;
import ca.ulaval.glo2003.infrastructure.restaurant.InMemoryRestaurantRepository;
import ca.ulaval.glo2003.infrastructure.reservation.MongoReservationRepository;
import ca.ulaval.glo2003.infrastructure.restaurant.MongoRestaurantRepository;
import ca.ulaval.glo2003.infrastructure.review.InMemoryReviewRepository;
import ca.ulaval.glo2003.infrastructure.review.MongoReviewRepository;
import ca.ulaval.glo2003.service.ReviewService;
import ca.ulaval.glo2003.domain.reservation.ReservationFactory;
import ca.ulaval.glo2003.domain.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.service.ReservationService;
import ca.ulaval.glo2003.service.RestaurantService;
import ca.ulaval.glo2003.service.assembler.CustomerAssembler;
import ca.ulaval.glo2003.service.assembler.FuzzySearchAssembler;
import ca.ulaval.glo2003.service.assembler.HoursAssembler;
import ca.ulaval.glo2003.service.assembler.VisitTimeAssembler;
import ca.ulaval.glo2003.service.validators.CreateRestaurantValidator;
import ca.ulaval.glo2003.service.validators.CreateReviewValidator;
import ca.ulaval.glo2003.service.validators.GetRestaurantValidator;
import ca.ulaval.glo2003.service.validators.HeaderValidator;
import ca.ulaval.glo2003.service.validators.ReservationValidator;
import ca.ulaval.glo2003.service.validators.SearchRestaurantValidator;
import jakarta.inject.Singleton;
import org.glassfish.jersey.internal.inject.AbstractBinder;


public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        choosePersistenceType();
        bind(RestaurantService.class).to(RestaurantService.class);
        bind(ReservationService.class).to(ReservationService.class);
        bind(ReviewService.class).to(ReviewService.class);

        bind(HeaderValidator.class).to(HeaderValidator.class);
        bind(CreateRestaurantValidator.class).to(CreateRestaurantValidator.class);
        bind(ReservationValidator.class).to(ReservationValidator.class);
        bind(GetRestaurantValidator.class).to(GetRestaurantValidator.class);
        bind(SearchRestaurantValidator.class).to(SearchRestaurantValidator.class);
        bind(CreateReviewValidator.class).to(CreateReviewValidator.class);
        bind(RestaurantFactory.class).to(RestaurantFactory.class);
        bind(ReservationFactory.class).to(ReservationFactory.class);
        bind(ReviewFactory.class).to(ReviewFactory.class);
        bind(HoursAssembler.class).to(HoursAssembler.class);
        bind(FuzzySearchResponseAssembler.class).to(FuzzySearchResponseAssembler.class);
        bind(FuzzySearchAssembler.class).to(FuzzySearchAssembler.class);
        bind(VisitTimeAssembler.class).to(VisitTimeAssembler.class);
        bind(CustomerAssembler.class).to(CustomerAssembler.class);
        bind(RestaurantResponseAssembler.class).to(RestaurantResponseAssembler.class);
        bind(ReservationResponseAssembler.class).to(ReservationResponseAssembler.class);
        bind(ReservationGeneralResponseAssembler.class).to(ReservationGeneralResponseAssembler.class);
        bind(ReviewResponseAssembler.class).to(ReviewResponseAssembler.class);
    }

    private void choosePersistenceType() {
        String persistence = System.getProperty("persistence");
        if (persistence != null && persistence.equals("mongo")) {
            bind(SystemEnvReader.class).to(EnvironmentReader.class);
            bind(DatastoreProvider.class).to(DatastoreProvider.class);
            bind(MongoRestaurantRepository.class).to(RestaurantRepository.class).in(Singleton.class);
            bind(MongoReservationRepository.class).to(ReservationRepository.class).in(Singleton.class);
            bind(MongoReviewRepository.class).to(ReviewRepository.class).in(Singleton.class);
        } else {
            bind(InMemoryRestaurantRepository.class).to(RestaurantRepository.class).in(Singleton.class);
            bind(InMemoryReservationRepository.class).to(ReservationRepository.class).in(Singleton.class);
            bind(InMemoryReviewRepository.class).to(ReviewRepository.class).in(Singleton.class);
        }
    }
}
