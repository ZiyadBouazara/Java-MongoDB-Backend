package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.domain.Hours;
import ca.ulaval.glo2003.domain.InvalidParameterException;
import ca.ulaval.glo2003.domain.MissingParameterException;

import java.time.Duration;
import java.time.LocalTime;

public class RestaurantRequest {
    private String name;
    private int capacity;
    private Hours hours;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Hours getHours() {
        return hours;
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }

    public void verifyMissingParameters() throws MissingParameterException {
        verifyMissingName();
        verifyMissingHours();
    }

    private void verifyMissingName() throws MissingParameterException {
        if (name == null) {
            throw new MissingParameterException("Missing parameter 'name'");
        }
    }

    private void verifyMissingHours() throws MissingParameterException {
        if (hours == null || hours.getOpen() == null || hours.getClose() == null) {
            throw new MissingParameterException("Missing parameter 'hours'");
        }
    }

    public void verifyValidParameters() throws InvalidParameterException {
        verifyValidName();
        verifyValidCapacity();
        verifyValidHours();
    }

    private void verifyValidName() throws InvalidParameterException {
        if (name.isEmpty()) {
            throw new InvalidParameterException("Invalid parameter 'name', cant be blank");
        }
    }

    private void verifyValidCapacity() throws InvalidParameterException {
        if (capacity < 1) {
            throw new InvalidParameterException("Invalid parameter 'capacity', minimum capacity of 1 person");
        }
    }

    private void verifyValidHours() throws InvalidParameterException {
        openForMinimumDuration();
        doesNotOpenBeforeMidnight();
        closesBeforeMidnight();
    }

    private void openForMinimumDuration() throws InvalidParameterException {
        LocalTime openingTime = LocalTime.parse(hours.getOpen());
        LocalTime closingTime = LocalTime.parse(hours.getClose());

        Duration duration = Duration.between(openingTime, closingTime);

        if (duration.toMinutes() < 60) {
            throw new InvalidParameterException("Invalid parameter 'hours', must be open for at least 1 hour");
        }
    }

    private void doesNotOpenBeforeMidnight() throws InvalidParameterException {
        if (!hours.getOpen().equals("00:00:00") && hours.getOpen().compareTo("00:00:00") < 0) {
            throw new InvalidParameterException("Invalid parameter 'hours.open', can't open before midnight");
        }
    }

    private void closesBeforeMidnight() throws InvalidParameterException {
        if (!hours.getClose().equals("23:59:59") && hours.getClose().compareTo("23:59:59") > 0) {
            throw new InvalidParameterException("Invalid parameter 'hours.close', must close before midnight");
        }
    }
}
