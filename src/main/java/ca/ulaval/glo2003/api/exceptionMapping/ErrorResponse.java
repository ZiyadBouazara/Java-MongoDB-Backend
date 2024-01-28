package ca.ulaval.glo2003.api.exceptionMapping;

public class ErrorResponse {
    private ErrorCode errorCode;
    private String description;

    public ErrorResponse(ErrorCode errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

    public ErrorCode getError(){
        return errorCode;
    }

    public String getDescription(){
        return description;
    }

    public void setError(ErrorCode error) {
        this.errorCode = error;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
