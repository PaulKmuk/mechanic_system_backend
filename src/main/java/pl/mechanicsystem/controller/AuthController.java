package pl.mechanicsystem.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mechanicsystem.dto.ApiResponse;
import pl.mechanicsystem.dto.auth.LoginRequest;
import pl.mechanicsystem.dto.auth.LoginResponse;
import pl.mechanicsystem.exception.RefreshTokenNotFoundException;
import pl.mechanicsystem.service.AuthService;
import pl.mechanicsystem.service.RefreshTokenService;
import pl.mechanicsystem.service.auth.LoginResult;
//import pl.mechanicsystem.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    public AuthService authService;
    public RefreshTokenService refreshTokenService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
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

    // ===========================================
    // LOGOUT
    // ===========================================
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @CookieValue(name = "refreshToken", required = false) String refreshToken
    ) {
        if (refreshToken != null && !refreshToken.isBlank()){
            refreshTokenService.revokeToken(refreshToken);
        }
        // wyczyszczenie cookie po stronie przeglądarki
        ResponseCookie clearCookie = ResponseCookie
                .from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, clearCookie.toString())
                .body(ApiResponse.ok(null));
    }

    // ===========================================
    // REFRES_TOKEN
    // ===========================================
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refresh(
            @CookieValue(name = "refreshToken", required = false) String refreshToken
    ){
        // 1. sprawdzenie czy cookie istniej
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new RefreshTokenNotFoundException(
                    "Brak refresh tokena w cookie",
                    "REFRESH_TOKEN_NOT_FOUND",
                    "Cookie 'refresToken' nie zostało przesłane"
            );
        }
        // 2. Tworzymy odpowiedź = result jak przy logowaniu
        LoginResult loginResult = authService.refresh(refreshToken);

        // 3. Tworzymy repsone body
        LoginResponse loginResponse = new LoginResponse(
                loginResult.getAccessToken(),
                loginResult.getUser()
        );

        // 4. Nowe cookie z nowym refreshToken
        ResponseCookie refreshCookie = ResponseCookie
                .from("refreshToken", loginResult.getRefreshToken())
                .httpOnly(true)
                .secure(false)                     // na prod  true
                .path("/")
                .maxAge(60 * 60)    // 60 min
                .sameSite("Strict")                //  Strict  /  Lax
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(ApiResponse.ok(loginResponse));
    }
}
