package ca.ulaval.glo2003.domain.utils;

public class VisitTime {
    private String from;
    private String to;

    public VisitTime(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
