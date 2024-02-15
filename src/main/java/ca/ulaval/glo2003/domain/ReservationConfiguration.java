package ca.ulaval.glo2003.domain;

public class RestaurantConfiguration {
    private static final Integer DEFAULT_RESERVATION_DURATION = 60;
    private Integer duration;

    public RestaurantConfiguration() {
        this.duration = DEFAULT_RESERVATION_DURATION;
    }

    public RestaurantConfiguration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

}
