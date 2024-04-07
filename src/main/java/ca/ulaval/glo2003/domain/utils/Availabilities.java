package ca.ulaval.glo2003.domain.utils;

import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Availabilities {
    private String date;
    private Integer remainingPlace;
    List<Availabilities> availabilities = new ArrayList<>();


    public Availabilities(String date, Integer remainingPlace) {
        this.date = date;
        this.remainingPlace = remainingPlace;
    }

    public List<Availabilities> getAvailabilitiesForRestaurant(Restaurant restaurant, List<Reservation> reservationList) {
        LocalDateTime openingTime = getOpeningTime(restaurant);
        LocalDateTime closingTime = getClosingHour(restaurant);
        int reservationDuration = restaurant.getRestaurantConfiguration().getDuration();

        LocalDateTime currentTime = openingTime;

        while (currentTime.plusMinutes(reservationDuration).isBefore(closingTime)) {
            int remainingPlaces = calculateRemainingPlace(restaurant, currentTime, reservationList);
            availabilities.add(new Availabilities(currentTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                                                remainingPlaces));
            currentTime = currentTime.plusMinutes(15);
        }
//        else throw hour validation exception
        return availabilities;
    }

    private int calculateRemainingPlace(Restaurant restaurant, LocalDateTime currentTime, List<Reservation> reservationList) {
        return getExistingReservation(restaurant, currentTime, reservationList)
                ? calculateRemainingPlacesWithReservation(restaurant,currentTime, reservationList)
                : restaurant.getCapacity();
    }

    private boolean getExistingReservation(Restaurant restaurant, LocalDateTime currentTime,
                                           List<Reservation> reservationList) {
        return reservationList.stream().anyMatch(reservation -> isReservationActive(currentTime,
                reservation.getStartTime(),
                restaurant.getRestaurantConfiguration().getDuration()));
    }

    private boolean isReservationActive(LocalDateTime currentTime, String reservationStartTimeString, int reservationDuration) {
        LocalDate currentDate = LocalDate.now();
        String fullStartTimeString = currentDate + "T" + reservationStartTimeString;
        LocalDateTime reservationStartTime = LocalDateTime.parse(fullStartTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        LocalDateTime reservationEndTime = reservationStartTime.plusMinutes(reservationDuration);

        return !currentTime.isBefore(reservationStartTime) && !currentTime.isAfter(reservationEndTime);
    }

    private int calculateRemainingPlacesWithReservation(Restaurant restaurant, LocalDateTime currentTime,
                                                        List<Reservation> reservationList) {
        return reservationList.stream()
                .filter(reservation -> isReservationActive(currentTime, reservation.getStartTime(), restaurant.getRestaurantConfiguration().getDuration()))
                .findFirst()
                .map(reservation -> restaurant.getCapacity() - reservation.getGroupSize())
                .orElse(0);
    }

    private LocalDateTime getOpeningTime(Restaurant restaurant) {
        return LocalDateTime.parse(date + "T" + restaurant.getHours().getOpen(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    private LocalDateTime getClosingHour(Restaurant restaurant) {
        return LocalDateTime.parse(date + "T" + restaurant.getHours().getClose(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public String getDate() {
        return this.date;
    }

    public Integer getRemainingPlace() {
        return this.remainingPlace;
    }
}
