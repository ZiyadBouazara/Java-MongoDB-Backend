package ca.ulaval.glo2003.models;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReservationRequest {
    private String date;
    private String startTime;
    private Integer groupSize;
    private Customer customer;

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\d{10}$");

    private static final String EMAIL_LOCAL_PART_PATTERN =
            "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*";

    private static final String EMAIL_DOMAIN_PATTERN =
            "(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|" +
                    "\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
                    "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]" +
                    ":(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            EMAIL_LOCAL_PART_PATTERN + "@" + EMAIL_DOMAIN_PATTERN);

    private static final Pattern TIME_PATTERN = Pattern.compile("^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$");

    public void setDate(String date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void verifyParameters() throws InvalidParameterException, MissingParameterException {
        verifyMissingParameters();
        verifyValidParameters();
    }

    public void verifyMissingParameters() throws MissingParameterException {
        verifyMissing("date", date);
        verifyMissing("startTime", startTime);
        verifyMissing("groupSize", groupSize);
        verifyMissingCustomer();
    }

    public void adjustReservationStartTime() {
        LocalTime startTime = LocalTime.parse(this.startTime);
        int minutes = startTime.getMinute();

        if (minutes % 15 != 0) {
            int adjustmentMinutes = calculateAdjustment(minutes);
            LocalTime adjustedStartTime = startTime.plusMinutes(adjustmentMinutes).withSecond(0);
            String formattedAdjustedStartTime = formatAdjustedStartTime(adjustedStartTime);
            setStartTime(formattedAdjustedStartTime);
        }
    }

    private void verifyMissing(String parameterName, Object parameterValue) throws MissingParameterException {
        if (parameterValue == null) {
            throw new MissingParameterException("Missing parameter '" + parameterName + "'");
        }
    }

    private void verifyMissingCustomer() throws MissingParameterException {
        verifyMissing("customer", customer);
        verifyMissing("customer: name", customer.getName());
        verifyMissing("customer: email", customer.getEmail());
        verifyMissing("customer: phoneNumber", customer.getPhoneNumber());
    }

    public void verifyValidParameters() throws InvalidParameterException {
        verifyValidDate();
        verifyValidStartTime();
        verifyValidGroupSize();
        verifyValidCustomer();
    }

    private void verifyValidDate() throws InvalidParameterException {
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidParameterException("Invalid parameter 'date', it must be a valid date in the format YYYY-MM-DD");
        }
    }

    private void verifyValidStartTime() throws InvalidParameterException {
        if (!TIME_PATTERN.matcher(startTime).matches()) {
            throw new InvalidParameterException("Invalid parameter 'startTime', it must follow the following format: HH:MM:SS");
        }
    }

    private void verifyValidGroupSize() throws InvalidParameterException {
        if (groupSize < 1) {
            throw new InvalidParameterException("Invalid parameter 'groupSize', it must be a positive number");
        }
    }

    private void verifyValidCustomer() throws InvalidParameterException {
        if (!isValidEmail(customer.getEmail())) {
            throw new InvalidParameterException("Invalid parameter 'customer: email', it must follow the following format: x@y.z");
        }
        if (!PHONE_NUMBER_PATTERN.matcher(customer.getPhoneNumber()).matches()) {
            throw new InvalidParameterException("Invalid parameter 'customer: phoneNumber', it must contain 10 digits");
        }
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }



    private int calculateAdjustment(int minutes) {
        int remainder = minutes % 15;
        return 15 - remainder;
    }

    private String formatAdjustedStartTime(LocalTime adjustedStartTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return adjustedStartTime.format(formatter);
    }
}
