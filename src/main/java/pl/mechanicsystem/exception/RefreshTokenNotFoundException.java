package pl.mechanicsystem.exception;

public class RefreshTokenNotFoundException extends RuntimeException{

    private final String code;
    private final String details;


    public RefreshTokenNotFoundException(String message, String code, String details) {
        super(message);
        this.code = code;
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public String getDetails() {
        return details;
    }
}
