package com.choem_vannin.execption;

import com.choem_vannin.utils.ApiResponseHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // HTTP 400 - Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseHelper<Object>> handleValidationException(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(item ->
                errors.put(item.getField(), item.getDefaultMessage())
                );

        ApiResponseHelper<Object> responseHelper = ApiResponseHelper.error(
                HttpStatus.BAD_REQUEST, "Validation failed!", errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseHelper);
    }

    // HTTP 400 - Custom Bad Request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponseHelper<Object>> handleBadRequestException(BadRequestException exception) {
        ApiResponseHelper<Object> responseHelper = ApiResponseHelper.error(
                HttpStatus.BAD_REQUEST, exception.getMessage(), null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseHelper);
    }


    // HTTP 404 - Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseHelper<Object>> handleNotFoundException(ResourceNotFoundException exception){
        ApiResponseHelper<Object> responseHelper = ApiResponseHelper.error(
                HttpStatus.NOT_FOUND, exception.getMessage(), null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseHelper);
    }

    // HTTP 500 - Internal Server Error
    @ExceptionHandler(Exception.class)
    private ResponseEntity<ApiResponseHelper<Object>> handleServerException(Exception exception){
        ApiResponseHelper<Object> responseHelper = ApiResponseHelper.error(
                HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong on the server!", exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseHelper);
    }

    // HTTP 403 - Access Denied (role/permission check failed)
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponseHelper<Object>> handleAccessDenied(AuthorizationDeniedException exception) {
        ApiResponseHelper<Object> responseHelper = ApiResponseHelper.error(
                HttpStatus.FORBIDDEN, "You do not have permission to access this resource.", null
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseHelper);
    }

    // HTTP 409 - Duplicate Resource / Conflict (Room availability conflict)
    @ExceptionHandler(DuplicateSkuException.class)
    public ResponseEntity<ApiResponseHelper<Object>> handleDuplicateSkuException(DuplicateSkuException exception) {
        ApiResponseHelper<Object> responseHelper = ApiResponseHelper.error(
                HttpStatus.CONFLICT, exception.getMessage(), null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseHelper);
    }

    // HTTP 401/403 - Custom Security/Forbidden Exceptions from Service Layer
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponseHelper<Object>> handleForbiddenException(ForbiddenException exception) {
        ApiResponseHelper<Object> responseHelper = ApiResponseHelper.error(
                HttpStatus.FORBIDDEN, exception.getMessage(), null
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseHelper);
    }
}
