package com.choem_vannin.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseHelper<T> {

    private Boolean success;
    private Integer code;
    private String message;
    private T data;
    private Object errors;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    // Success Helper
    public static <T> ApiResponseHelper<T> ok(T data, String message){
        return ApiResponseHelper.<T>builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponseHelper<T> create(T data, String message){
        return ApiResponseHelper.<T>builder()
                .success(true)
                .code(HttpStatus.CREATED.value())
                .message(message)
                .data(data)
                .build();
    }

    // Error Helper
    public static <T> ApiResponseHelper<T> error(HttpStatus status, String message, Object errors){
        return ApiResponseHelper.<T>builder()
                .success(false)
                .code(status.value())
                .message(message)
                .errors(errors)
                .build();
    }
}
