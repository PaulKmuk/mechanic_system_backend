package pl.mechanicsystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.mechanicsystem.dto.TestInfo;

import java.time.LocalDateTime;

@Service
public class TestService {

    private final String appVersion;

    public TestService(@Value("${app.version}") String appVersion) {
        this.appVersion = appVersion;
    }

    public TestInfo getTestInfo() {
        return new TestInfo(
                "API działa poprawnie",
                appVersion,
                LocalDateTime.now()
        );
    }
}
