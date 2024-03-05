package ca.ulaval.glo2003.models;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.utils.ResourcesHandler;
import ca.ulaval.glo2003.domain.utils.Time;


public class ReservationResponse {
    public String number;
    public String date;
    public Time time;
    public int groupSize;
    public Customer customer;
    public RestaurantReservationResponse restaurant;

    public ReservationResponse(Reservation reservation, ResourcesHandler resourcesHandler) {
        this.number = reservation.getId();
        this.date = reservation.getDate();
        this.time = new Time(reservation.getStartTime());
        this.groupSize = reservation.getGroupSize();
        this.customer = reservation.getCustomer();
        this.restaurant = new RestaurantReservationResponse(resourcesHandler.getRestaurant(reservation.getRestaurantId()));
    }
}
