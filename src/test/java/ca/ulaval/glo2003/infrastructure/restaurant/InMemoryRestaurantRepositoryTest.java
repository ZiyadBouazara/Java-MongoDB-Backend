package ca.ulaval.glo2003.infrastructure.restaurant;

import ca.ulaval.glo2003.domain.repositories.RestaurantRepository;
import ca.ulaval.glo2003.domain.repositories.RestaurantRepositoryTest;

class InMemoryRestaurantRepositoryTest extends RestaurantRepositoryTest {
    @Override
    protected RestaurantRepository createRepository() {
        return new InMemoryRestaurantRepository();
    }
}
