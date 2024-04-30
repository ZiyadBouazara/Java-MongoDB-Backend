package ca.ulaval.glo2003.domain.restaurant;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;


@Entity("restaurants")
public class RestaurantMongo {
    @Id
    private String id;
    private String ownerId;
    private String name;
    private Integer capacity;
    private String openHour;
    private String closeHour;
    private Integer duration;
    private Double rating;
    private int reviewCount;

    public RestaurantMongo() {
    }

    public RestaurantMongo(String id, String ownerId, String name, Integer capacity, String openHour, String closeHour,
                           Integer duration, Double rating, int reviewCount) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.capacity = capacity;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.duration = duration;
        this.rating = rating;
        this.reviewCount = reviewCount;
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

    public String getOpenHour() {
        return openHour;
    }

    public String getCloseHour() {
        return closeHour;
    }

    public Integer getDuration() {
        return duration;
    }

    public Double getRating() {
        return rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }
}
