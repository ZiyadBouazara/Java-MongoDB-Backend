package ca.ulaval.glo2003.infrastructure.reservation;

import ca.ulaval.glo2003.domain.repositories.ReservationRepository;
import ca.ulaval.glo2003.domain.repositories.ReservationRepositoryTest;

class InMemoryReservationRepositoryTest extends ReservationRepositoryTest {

    @Override
    protected ReservationRepository createRepository() {
        return new InMemoryReservationRepository();
    }
}
