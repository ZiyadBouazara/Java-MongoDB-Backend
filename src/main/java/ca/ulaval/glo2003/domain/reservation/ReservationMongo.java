package ca.ulaval.glo2003.domain.reservation;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("reservations")
public class ReservationMongo {
    @Id
    private String id;
    private String restaurantId;
    private String date;
    private String startTime;
    private int groupSize;
    private String customerName;
    private String customerEmail;
    private String customerPhone;

    public ReservationMongo() {
    }

    public ReservationMongo(String id, String restaurantId, String date, String startTime, int groupSize, String customerName,
                            String customerEmail, String customerPhone) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.date = date;
        this.startTime = startTime;
        this.groupSize = groupSize;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
    }

    public String getId() {
        return id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }
}
