package pl.mechanicsystem.dto;

import java.time.LocalDateTime;

public class TestInfo {

    private String message;
    private String version;
    private LocalDateTime timestamp;

    public TestInfo(String message, String version, LocalDateTime timestamp) {
        this.message = message;
        this.version = version;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getVersion() {
        return version;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
