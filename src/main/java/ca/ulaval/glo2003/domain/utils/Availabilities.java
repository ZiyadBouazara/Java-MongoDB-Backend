package ca.ulaval.glo2003.domain.utils;

import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.ReservationConfiguration;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Availabilities {
    private String date;
    private Integer remainingPlace;
    List<Availabilities> availabilities = new ArrayList<>();


    public Availabilities(String date, Integer remainingPlace) {
        this.date = date;
        this.remainingPlace = remainingPlace;
    }

    public List<Availabilities> getAvailabilitiesForRestaurant(Restaurant restaurant) {

        Map<String, Reservation> reservations = restaurant.getReservationsById();
        System.out.println(reservations);

        String ouverture = restaurant.getHours().getOpen();
        String fermeture = restaurant.getHours().getClose();

        ReservationConfiguration reservationConfiguration = restaurant.getRestaurantConfiguration();


        LocalDateTime openingTime = LocalDateTime.parse(date + "T" + ouverture, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime closingTime = LocalDateTime.parse(date + "T" + fermeture, DateTimeFormatter.ISO_LOCAL_DATE_TIME);


        LocalDateTime currentTime = openingTime;

        while (currentTime.plusMinutes(reservationConfiguration.getDuration()).isBefore(closingTime)) {
            LocalDateTime finalCurrentTime = currentTime;
            boolean reservationExist = reservations.values().stream()
                    .anyMatch(reservation -> isReservationActive(finalCurrentTime, reservation.getStartTime(), restaurant.getRestaurantConfiguration().getDuration()));
            int remainingPlaces;
            if (reservationExist) {
                remainingPlaces = calculateRemainingPlacesWithReservation(currentTime, restaurant.getCapacity());
            } else {
                remainingPlaces = restaurant.getCapacity();
            }

            String formattedTime = currentTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            availabilities.add(new Availabilities(formattedTime, remainingPlaces));
            currentTime = currentTime.plusMinutes(15);
        }
        return availabilities;
    }

    private boolean isReservationActive(LocalDateTime currentTime, String reservationStartTimeString, int reservationDuration) {
        LocalDate currentDate = LocalDate.now();
        String fullStartTimeString = currentDate + "T" + reservationStartTimeString;
        LocalDateTime reservationStartTime = LocalDateTime.parse(fullStartTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        LocalDateTime reservationEndTime = reservationStartTime.plusMinutes(reservationDuration);

        return !currentTime.isBefore(reservationStartTime) && !currentTime.isAfter(reservationEndTime);
    }



    private int calculateRemainingPlacesWithReservation(LocalDateTime currentTime, Integer capacity) {
        return (int) (Math.random() * 10);
    }

    public String getDate() {
        return this.date;
    }

    public Integer getRemainingPlace() {
        return this.remainingPlace;
    }
}
