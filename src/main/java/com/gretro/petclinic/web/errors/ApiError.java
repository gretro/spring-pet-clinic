package com.gretro.petclinic.web.errors;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ApiError {
    private HttpStatus httpStatus;
    private Instant timestamp;
    private String message;
    private String debugMessage;
    private List<String> stackTrace;

    public ApiError(Instant timestamp, HttpStatus httpStatus, String message) {
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public ApiError(Instant timestamp, HttpStatus httpStatus, String message, Throwable throwable) {
        this(timestamp, httpStatus, message);

        this.debugMessage = throwable.getLocalizedMessage();
        this.stackTrace = Arrays.stream(throwable.getStackTrace())
            .map(Object::toString)
            .collect(Collectors.toList());
    }
}
