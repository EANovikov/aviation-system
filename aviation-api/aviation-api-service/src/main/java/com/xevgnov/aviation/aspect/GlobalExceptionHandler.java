package com.xevgnov.aviation.aspect;

import com.xevgnov.aviation.dto.ExceptionResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .message(exception.getMessage())
                        .details(exception.toString()).build());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .message(exception.getMessage())
                        .details(exception.toString()).build());
    }

}
