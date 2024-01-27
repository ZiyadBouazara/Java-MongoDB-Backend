package ca.ulaval.glo2003.domain;

public class Restaurant {
    private String id;
    private String name;
    private int capacity;
    private Hours hours;

    public Restaurant(String id, String name, int capacity, Hours hours) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.hours = hours;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public Hours getHours() {
        return hours;
    }
}