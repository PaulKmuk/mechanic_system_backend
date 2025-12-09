package pl.mechanicsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.mechanicsystem.dto.ApiError;
import pl.mechanicsystem.dto.ApiResponse;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiError error = new ApiError(
                httpStatus.value(),
                "Error",
                "Wewnętrzny błąd serwera",
                ex.getMessage()
        );

        return ResponseEntity
                .status(httpStatus)
                .body(ApiResponse.error(error));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserNotFoundException ex) {

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ApiError error = new ApiError(
                httpStatus.value(),
                ex.getCode(),
                ex.getMessage(),
                ex.getDetails()
        );

        return ResponseEntity
                .status(httpStatus)
                .body(ApiResponse.error(error));
    }
}
