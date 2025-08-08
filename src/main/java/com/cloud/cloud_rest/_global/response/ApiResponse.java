package com.cloud.cloud_rest._global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    private final boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL) // null이 아닐 때만 JSON에 포함
    private final T response;
    @JsonInclude(JsonInclude.Include.NON_NULL) // null이 아닐 때만 JSON에 포함
    private final ApiError error;

    private ApiResponse(boolean success, T response, ApiError error) {
        this.success = success;
        this.response = response;
        this.error = error;
    }

    public static <T> ApiResponse<T> success(T response) {
        return new ApiResponse<>(true, response, null);
    }

    public static ApiResponse<Void> success() {
        return new ApiResponse<>(true, null, null);
    }

    public static ApiResponse<ApiError> error(String message, HttpStatus status) {
        return new ApiResponse<>(false, null, new ApiError(message, status.value()));
    }

    @Getter
    public static class ApiError {
        private final String message;
        private final int status;

        ApiError(String message, int status) {
            this.message = message;
            this.status = status;
        }
    }
}