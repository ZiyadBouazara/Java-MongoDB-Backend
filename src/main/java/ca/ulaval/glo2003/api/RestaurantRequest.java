package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.domain.Hours;

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

    public boolean hasMissingParameters() {
        return name != null && hours != null && hours.getOpen() != null && hours.getClose() != null;
    }

    public boolean hasValidParameters() {
        return isNameValid() &&
                isCapacityValid() &&
                areHoursValid();
    }

    private boolean isNameValid() {
        return !name.trim().isEmpty();
    }

    private boolean isCapacityValid() {
        return capacity >= 1;
    }

    private boolean isUniqueIdValid() {
        return true; //TODO
    }
    private boolean areHoursValid() {
        return doesNotOpenBeforeMidnight() && closesBeforeMidnight();
    }

    private boolean doesNotOpenBeforeMidnight() {
        return true; //TODO
    }

    private boolean closesBeforeMidnight() {
        return true; //TODO
    }
}
