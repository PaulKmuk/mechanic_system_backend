package pl.mechanicsystem.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import pl.mechanicsystem.dto.ApiError;
import pl.mechanicsystem.dto.ApiResponse;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;


/*
 * Filter HTTP:
 * - przechwytuje każdy request HTTP,
 * - sprawdza czy trzeba wymagać accessToken
 * - Token TAK wymagany - weryfikuje JWT z nagłówka Authorization
 * - jeśli tokken nieporpawny lub brak - zwraca status 401 z JSON-en z informacją
 */
@Component
public class JwtAuthFilter implements Filter {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        HttpServletResponse httpRes = (HttpServletResponse) servletResponse;
        String path = httpReq.getRequestURI();

        // 1. Deklarujemy sciezki w których pomijamy filter - nie sprawdzamy jwtToken
        // -  /api/v1/auth/**
        // -  /api/v1/test
        if(!path.startsWith("/api/v1/") || path.startsWith("/api/v1/auth/")) {
            // nic nie sprawdzamy i idziemy dalej
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 2. Dla pozostałych ścieżek pobieramy nagłówek: Bearer ......
        String authHeader = httpReq.getHeader("Authorization");
        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            // Brak nagłówka lub zły format -> 401
            writeAuthError(
                    httpRes,
                    401,
                    "AUTH_HEADER_MISSING",
                    "Brak nagłówka Authorization lub niepoprawny format",
                    "Oczekiwano: Authorization: Beare <token>"
            );
            return;
        }

        String token = authHeader.substring("Bearer ".length()).trim();

        if (token.isEmpty()) {
            writeAuthError(httpRes,
                    401,
                    "ACCESS_TOKEN_EMPTY",
                    "Access token jest pusty",
                    "Nagłówek Authorization zawiera pusty token");
            return;
        }

        try {
            // 3. Walidacja tokena
            jwtService.parseAndValidate(token);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (RuntimeException exception) {
            // Token błedny / wygasł -> 401
            writeAuthError(httpRes,
                    401,
                    "ACCESS_TOKEN_INVALID_OR_EXPIRED",
                    "Access token jest nieprawidłowy lub wygasł",
                    exception.getMessage());
        }
    }

    /*
        * Ppmocnicza metoda do wysyłąnai błedu authoryzacji
        * zgodne z strukturą naszych błedów
     */
    private void writeAuthError(
            HttpServletResponse response,
            int httpStatus,
            String code,
            String message,
            String details
    ) throws IOException {
        response.setStatus(httpStatus);
        response.setContentType("application/json;charset=UTF-8");

        ApiError error = new ApiError(
                httpStatus,
                code,
                message,
                details
        );

        ApiResponse<Void> body = ApiResponse.error(error);
        String json = objectMapper.writeValueAsString(body);
        response.getWriter().write(json);
    }
}
