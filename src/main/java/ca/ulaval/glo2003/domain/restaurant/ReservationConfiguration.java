package ca.ulaval.glo2003.domain.restaurant;


public class ReservationConfiguration {
    private static final Integer DEFAULT_RESERVATION_DURATION = 60;
    private Integer duration;

    public ReservationConfiguration() {
        this.duration = DEFAULT_RESERVATION_DURATION;
    }

    public ReservationConfiguration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

}
