package ca.ulaval.glo2003.domain.review;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("reviews")
public class ReviewMongo {
    @Id
    private String id;
    private String restaurantId;
    private String date;
    private double rating;
    private String comment;
    private String customerName;
    private String customerEmail;
    private String customerPhone;

    public ReviewMongo() {
    }

    public ReviewMongo(String id, String restaurantId, String date, double rating, String comment, String customerName,
                            String customerEmail, String customerPhone) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.date = date;
        this.rating = rating;
        this.comment = comment;
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
