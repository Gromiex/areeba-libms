package com.example.libms.exception;

import com.example.libms.author.exceptions.AuthorHasBooksException;
import com.example.libms.author.exceptions.AuthorNotFoundException;
import com.example.libms.book.exceptions.BookNotFoundException;
import com.example.libms.borrower.exceptions.BorrowerHasActiveBorrowingsException;
import com.example.libms.borrower.exceptions.BorrowerNotFoundException;
import com.example.libms.borrowing.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            AuthorNotFoundException.class,
            BookNotFoundException.class,
            BorrowerNotFoundException.class,
            BorrowingNotFoundException.class
    })
    public ResponseEntity<Map<String, Object>> handleNotFound(RuntimeException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            BookUnavailableException.class,
            BorrowerHasActiveBorrowingsException.class,
            AuthorHasBooksException.class
    })
    public ResponseEntity<Map<String, Object>> handleBookUnavailable(RuntimeException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({BookAlreadyReturnedException.class})
    public ResponseEntity<Map<String, Object>> handleBookAlreadyReturned(RuntimeException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", 400);
        body.put("errors", errors);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(BookingLimitReachedException.class)
    public ResponseEntity<Map<String, Object>> handleBookingLimitReached(RuntimeException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "too many requests");
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.TOO_MANY_REQUESTS.value());
        return new ResponseEntity<>(body, HttpStatus.TOO_MANY_REQUESTS);
    }

    // Handles path variable/request param validations
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, Object> body = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
                body.put("error", "bad request");
                body.put("timestamp", LocalDateTime.now());
                body.put(violation.getPropertyPath().toString(), violation.getMessage());
                body.put("status", HttpStatus.BAD_REQUEST.value());
        }
        );
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {

        String message = "Invalid request body";

        Throwable cause = ex.getCause();

        if (cause instanceof tools.jackson.databind.exc.InvalidFormatException ife) {

            Class<?> targetType = ife.getTargetType();

            if (targetType != null && targetType.isEnum()) {
                message = "Invalid value '" + ife.getValue()
                        + "' for enum " + targetType.getSimpleName();
            }
        }

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", message);
        body.put("timestamp", java.time.Instant.now());

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        String message = "Constraint Violation";

        Throwable cause = ex.getCause();

        if (cause instanceof org.hibernate.exception.ConstraintViolationException cve) {
            if (cve.getKind() != null && !cve.getMessage().isEmpty()) {
                message = cve.getKind().name()
                        + " constraint violated: " + cve.getMessage();
            }
        }

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", message);
        body.put("timestamp", java.time.Instant.now());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOtherExceptions(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}