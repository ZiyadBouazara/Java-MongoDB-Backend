package ca.ulaval.glo2003.models;

import ca.ulaval.glo2003.domain.Hours;
import ca.ulaval.glo2003.domain.InvalidParameterException;
import ca.ulaval.glo2003.domain.MissingParameterException;
import ca.ulaval.glo2003.domain.ReservationConfiguration;

import java.time.Duration;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestaurantRequest {
    private String name;
    private Integer capacity;
    private Hours hours;
    private ReservationConfiguration reservations;
        // Do not change this variable's name, the createRestaurant Body uses the name for assignation
    private static final String TIME_FORMAT_REGEX = "^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
    private static final Pattern TIME_PATTERN = Pattern.compile(TIME_FORMAT_REGEX);


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

    public ReservationConfiguration getRestaurantConfiguration() {
        return reservations;
    }

    public void setReservations(ReservationConfiguration reservations) {
        this.reservations = reservations;
    }

    public void verifyParameters()
        throws InvalidParameterException, MissingParameterException {
        verifyMissingParameters();
        verifyValidParameters();
    }

    private void verifyMissingParameters() throws MissingParameterException {
        verifyMissingCapacity();
        verifyMissingName();
        verifyMissingHours();
    }

    public void verifyValidParameters() throws InvalidParameterException {
        verifyValidName();
        verifyValidCapacity();
        verifyValidHours();
    }

    private void verifyMissingCapacity() throws MissingParameterException {
        if (capacity == null) {
            throw new MissingParameterException("Missing parameter 'capacity'");
        }
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
        isValidTimeFormat(hours.getOpen());
        isValidTimeFormat(hours.getClose());
        openForMinimumDuration();
        doesNotOpenBeforeMidnight();
        closesBeforeMidnight();
    }

    private void isValidTimeFormat(String time) throws InvalidParameterException {
        Matcher matcher = TIME_PATTERN.matcher(time);
        if (!matcher.matches()) {
            throw new InvalidParameterException("Invalid time format: " + time + ". Use the 'HH:MM:SS' format");
        }
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
