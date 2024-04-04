package ca.ulaval.glo2003.domain.utils;


public class Hours {
    private String open;
    private String close;

    public Hours() {
    }

    public Hours(String open, String close) {
        this.open = open;
        this.close = close;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }
}
