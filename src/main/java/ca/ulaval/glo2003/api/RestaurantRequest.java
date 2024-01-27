package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.domain.Hours;
import ca.ulaval.glo2003.domain.InvalidParameterException;
import ca.ulaval.glo2003.domain.MissingParameterException;

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
        if (name.isEmpty()){
            throw new InvalidParameterException("Invalid parameter 'name', cant be blank");
        }
    }

    private boolean doesNotOpenBeforeMidnight() {
        if (hours != null && hours.getOpen() != null){
            String openTime = hours.getOpen();
            return openTime.compareTo("00:00:00") >= 0;
        }
        return false; //TODO
    private void verifyValidCapacity() throws InvalidParameterException{
        if (capacity <= 1){
            throw new InvalidParameterException("Invalid parameter 'capacity', minimum capacity of 1 person");
        }
    }

    private boolean closesBeforeMidnight() {
        if (hours != null && hours.getOpen() != null){
            String openTime = hours.getOpen();
            return openTime.compareTo("23:59:59") <= 0;
        }
        return false; //TODO
    private void verifyValidHours() throws InvalidParameterException{
        doesNotOpenBeforeMidnight();
        closesBeforeMidnight();
    }

    private void doesNotOpenBeforeMidnight() throws InvalidParameterException{
        //TODO
        throw new InvalidParameterException("Invalid parameter 'hours.open', cant open before midnight");
    }

    private void closesBeforeMidnight() throws InvalidParameterException{
        //TODO
        throw new InvalidParameterException("Invalid parameter 'hours.close', must close before midnight");
    }
}
