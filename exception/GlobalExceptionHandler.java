package org.example.ex4.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.ex4.dto.ErrorResponse;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Business Exception
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(
            BusinessException ex
    ) {

        log.warn("Business Exception: {}",
                ex.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .status(400)
                .errorCode("PROMO_INVALID")
                .message(ex.getMessage())
                .build();

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    // Validation Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex
    ) {

        String message = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        log.warn("Validation Error: {}",
                message);

        ErrorResponse response = ErrorResponse.builder()
                .status(400)
                .errorCode("VALIDATION_ERROR")
                .message(message)
                .build();

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    // System Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleSystem(
            Exception ex
    ) {

        log.error("System Error", ex);

        ErrorResponse response = ErrorResponse.builder()
                .status(500)
                .message(
                        "Lỗi máy chủ nội bộ. Vui lòng liên hệ quản trị viên"
                )
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}