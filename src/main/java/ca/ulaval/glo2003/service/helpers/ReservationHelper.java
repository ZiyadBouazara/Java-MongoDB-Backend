package ca.ulaval.glo2003.service.helpers;

public class ReservationHelper {
    public static Boolean isMatchingCustomerName(String reservationCustomerName, String searchCustomerName) {
        if (searchCustomerName != null) {
            String cleanedSearchingElement = searchCustomerName.replaceAll("\\s", "").toLowerCase();
            String cleanedReservationCustomerName = reservationCustomerName.replaceAll("\\s", "").toLowerCase();

            return cleanedReservationCustomerName.startsWith(cleanedSearchingElement);
        }
        return true;
    }

    public static Boolean isMatchingDate(String reservationDate, String searchDate) {
        if (searchDate != null) {
            return reservationDate.equals(searchDate);
        }
        return true;
    }

}
