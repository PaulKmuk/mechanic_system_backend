package pl.mechanicsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mechanicsystem.dto.ApiResponse;
import pl.mechanicsystem.dto.TestInfo;
import pl.mechanicsystem.service.TestService;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/test")
    public ApiResponse<?> test() {
        return ApiResponse.ok(testService.getTestInfo());
    }
}
