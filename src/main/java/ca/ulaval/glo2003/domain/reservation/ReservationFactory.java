package ca.ulaval.glo2003.domain.reservation;

import ca.ulaval.glo2003.domain.customer.Customer;

public class ReservationFactory {
    public Reservation createReservation(String restaurantId,
                                         String date,
                                         String startTime,
                                         Integer groupSize,
                                         Customer customer) {
        return new Reservation(restaurantId, date, startTime, groupSize, customer);
    }
}
