package ca.ulaval.glo2003.domain.restaurant;

import ca.ulaval.glo2003.domain.utils.Hours;
import java.util.UUID;

public class Restaurant {
    private String id;
    private String ownerId;
    private String name;
    private Integer capacity;
    private Hours hours;
    private ReservationConfiguration reservationConfiguration;
    private Double rating;
    private int reviewCount;

    public Restaurant(String ownerId, String name, Integer capacity, Hours hours) {
        this.id = UUID.randomUUID().toString();
        this.ownerId = ownerId;
        this.name = name;
        this.capacity = capacity;
        this.hours = hours;
        this.reservationConfiguration = new ReservationConfiguration();
        this.rating = 1.0;
        this.reviewCount = 0;
    }

    public Restaurant(String id, String ownerId, String name, Integer capacity, Hours hours,
                      ReservationConfiguration reservationConfiguration, Double rating, int reviewCount) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.capacity = capacity;
        this.hours = hours;
        this.reservationConfiguration = reservationConfiguration;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }

    public Restaurant(String ownerId, String name, Integer capacity, Hours hours, ReservationConfiguration reservationConfiguration) {
        this.id = UUID.randomUUID().toString();
        this.ownerId = ownerId;
        this.name = name;
        this.capacity = capacity;
        this.hours = hours;
        this.reservationConfiguration = reservationConfiguration;
        this.rating = 1.0;
        this.reviewCount = 0;
    }

    public Restaurant(String name, Hours hours, Integer capacity) {
        this.name = name;
        this.hours = hours;
        this.capacity = capacity;
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

    public Double getRating() {
        return this.rating;
    }

    public int getReviewCount() {
        return this.reviewCount;
    }

    public void setRating(Double newRating) {
        this.rating = newRating;
    }

    public void incrementReviewCount() {
        this.reviewCount++;
    }
}
