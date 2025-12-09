package pl.mechanicsystem.dto;

public class ApiError {

    private int status;
    private String code;
    private String message;
    private String details;

    public ApiError(int status, String code, String message, String details) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.details = details;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
