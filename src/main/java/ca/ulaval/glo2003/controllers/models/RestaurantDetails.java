package ca.ulaval.glo2003.controllers.models;

import ca.ulaval.glo2003.domain.restaurant.ReservationConfiguration;
import ca.ulaval.glo2003.domain.utils.Hours;

public interface RestaurantDetails {
    String id();
    String ownerId();
    String name();
    Integer capacity();
    Hours hours();
    ReservationConfiguration reservations();
}
