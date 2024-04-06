package ca.ulaval.glo2003.domain.utils;

public class PrefixSearch {

    private String date;
    private String customerName;

    public PrefixSearch(){
    }

    public Boolean isMatchingCustomerName(String reservationCustomerName, String searchCustomerName){
        if (searchCustomerName != null) {
            String cleanedSearchingElement = searchCustomerName.replaceAll("\\s", "").toLowerCase();
            String cleanedReservationCustomerName = reservationCustomerName.replaceAll("\\s", "").toLowerCase();

            return cleanedReservationCustomerName.startsWith(cleanedSearchingElement);
        }
        return true;
    }

    public Boolean isMatchingDate(String reservationDate, String searchDate){
        if(searchDate != null){
            return reservationDate.equals(searchDate);
        }
        return true;
    }
}
