package ca.ulaval.glo2003.infrastructure.assemblers;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationMongo;

public class ReservationAssembler {

    public static ReservationMongo toReservationMongo(Reservation reservation) {
        return new ReservationMongo(
            reservation.getId(),
            reservation.getRestaurantId(),
            reservation.getDate(),
            reservation.getStartTime(),
            reservation.getGroupSize(),
            reservation.getCustomer().getName(),
            reservation.getCustomer().getEmail(),
            reservation.getCustomer().getPhoneNumber()
        );
    }

    public static Reservation fromReservationMongo(ReservationMongo reservationMongo) {
        return new Reservation(
            reservationMongo.getRestaurantId(),
            reservationMongo.getDate(),
            reservationMongo.getStartTime(),
            reservationMongo.getGroupSize(),
            new Customer(reservationMongo.getCustomerName(), reservationMongo.getCustomerEmail(), reservationMongo.getCustomerPhone())
        );
    }
}
