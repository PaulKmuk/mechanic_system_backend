package pl.mechanicsystem.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mechanicsystem.dto.ApiResponse;
import pl.mechanicsystem.dto.auth.LoginRequest;
import pl.mechanicsystem.dto.auth.LoginResponse;
import pl.mechanicsystem.service.AuthService;
import pl.mechanicsystem.service.auth.LoginResult;
//import pl.mechanicsystem.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    public AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ===========================================
    // LOGOWANIE
    // ===========================================
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        // 1. Logowanie z service
        LoginResult loginResult = authService.login(
                loginRequest.getLogin(),
                loginRequest.getPassword()
        );
        // 2. Tworzymy body do response
        LoginResponse loginResponse = new LoginResponse(
                loginResult.getAccessToken(),
                loginResult.getUser()
        );
        // 3. Tworzymy HttPOnly cookie (refreshtoken)
        ResponseCookie refreshCookie = ResponseCookie
                .from("refreshToken", loginResult.getRefreshToken())
                .httpOnly(true)                           // JS nie ma dostępu
                .secure(false)                            // docelowo na prod true
                .path("/")                                // gdzie cookie bedzie wysyłane - "/" = całe API
                .maxAge(60 * 60)           // 60 minut (w sekundach)
                .sameSite("Strict")                       // "LAX"  /  "Strict"
                .build();

        // 4. Zwracamy response + cookie
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(ApiResponse.ok(loginResponse));
    }
}
