package pl.mechanicsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

// jeśli obiekt będzie NULL - to nie wrzuci w odpowiedzi
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private String status;
    private T data;
    private ApiError error;

    public ApiResponse(String status, T data, ApiError error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public ApiResponse(String status, ApiError error) {
        this.status = status;
        this.error = error;
    }

    public ApiResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>("OK", data);
    }

    public static <T> ApiResponse<T> error(ApiError error) {
        return new ApiResponse<>("ERROR", error);
    }

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public ApiError getError() {
        return error;
    }
}
