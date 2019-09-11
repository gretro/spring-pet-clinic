package com.gretro.petclinic.web;

import com.gretro.petclinic.exceptions.EntityNotFoundException;
import com.gretro.petclinic.web.errors.ApiError;
import com.gretro.petclinic.web.errors.ApiValidationError;
import com.gretro.petclinic.web.errors.FieldValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Clock;
import java.util.stream.Collectors;

@RestControllerAdvice()
@Slf4j
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    private final Clock clock;
    private final boolean isDebugMode;

    public AppExceptionHandler(Clock clock, @Value("${petclinic.debug}") boolean isDebugMode) {
        this.clock = clock;
        this.isDebugMode = isDebugMode;
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        log.info(ex.getMessage(), ex);
        var message = "Could not find the requested entity";

        var apiError = this.isDebugMode
                ? new ApiError(this.clock.instant(), HttpStatus.NOT_FOUND, message, ex)
                : new ApiError(this.clock.instant(), HttpStatus.NOT_FOUND, message);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("Unreadable JSON", ex);

        var message = "Could not process message. Make sure you are sending valid JSON";

        var apiError = this.isDebugMode
                ? new ApiError(this.clock.instant(), status, message, ex)
                : new ApiError(this.clock.instant(), status, message);

        return ResponseEntity.status(status).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (this.isDebugMode) {
            log.info("Validation error", ex);
        }

        var fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> FieldValidationError.builder()
                    .fieldName(fieldError.getField())
                    .code(fieldError.getCode())
                    .message(fieldError.getDefaultMessage())
                    .build())
                .collect(Collectors.toMap(FieldValidationError::getFieldName, fe -> fe));

        var error = ApiValidationError.builder()
                .timestamp(this.clock.instant())
                .fieldErrors(fieldErrors)
                .build();

        return ResponseEntity.status(status).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn(String.format("An unhandled error occurred and produces a HTTP %d response", status.value()), ex);

        var message = "An unhandled error occurred";

        var apiError = this.isDebugMode
            ? new ApiError(this.clock.instant(), status, message, ex)
            : new ApiError(this.clock.instant(), status, message);

        return ResponseEntity.status(status).body(apiError);
    }
}
