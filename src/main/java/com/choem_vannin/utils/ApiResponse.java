package com.choem_vannin.utils;


import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor
public class ApiResponse {

    // SUCCESS RESPONSES
    public static <T> ResponseEntity<ApiResponseHelper<T>> ok(T data, String message) {
        return ResponseEntity.ok(ApiResponseHelper.ok(data, message));
    }

    public static <T> ResponseEntity<ApiResponseHelper<T>> create(T data, String message) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseHelper.create(data, message));
    }

    // 400 BAD REQUEST
    public static <T> ResponseEntity<ApiResponseHelper<T>> badRequest(String message, Object errors) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseHelper.error(HttpStatus.BAD_REQUEST, message, errors));
    }

    // 404 NOT FOUND
    public static <T> ResponseEntity<ApiResponseHelper<T>> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponseHelper.error(HttpStatus.NOT_FOUND, message, null));
    }

    // 500 INTERNAL SERVER ERROR
    public static <T> ResponseEntity<ApiResponseHelper<T>> internalError(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseHelper.error(HttpStatus.INTERNAL_SERVER_ERROR, message, null));
    }
}
