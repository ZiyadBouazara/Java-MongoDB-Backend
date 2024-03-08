package ca.ulaval.glo2003.domain.restaurant;

import ca.ulaval.glo2003.domain.hours.Hours;
import ca.ulaval.glo2003.domain.reservation.Reservation;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

public class Restaurant {
    private String id;
    private String ownerId;
    private String name;
    private Integer capacity;
    private Hours hours;
    private ReservationConfiguration reservationConfiguration;
    private Map<String, Reservation> reservationsById = new HashMap<>();

    public Restaurant(String ownerId, String name, Integer capacity, Hours hours) {
        this.id = UUID.randomUUID().toString();
        this.ownerId = ownerId;
        this.name = name;
        this.capacity = capacity;
        this.hours = hours;
        this.reservationConfiguration = new ReservationConfiguration();
    }

    public Restaurant(String ownerId, String name, Integer capacity, Hours hours, ReservationConfiguration reservationConfiguration) {
        this.id = UUID.randomUUID().toString();
        this.ownerId = ownerId;
        this.name = name;
        this.capacity = capacity;
        this.hours = hours;
        this.reservationConfiguration = reservationConfiguration;
    }

    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Hours getHours() {
        return hours;
    }

    public ReservationConfiguration getRestaurantConfiguration() {
        return reservationConfiguration;
    }

    public Map<String, Reservation> getReservationsById() {
        return this.reservationsById;
    }

    public void addReservation(Reservation reservation) {
        reservationsById.put(reservation.getId(), reservation);
    }

}
