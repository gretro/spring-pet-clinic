package com.gretro.petclinic.web;

import com.gretro.petclinic.web.errors.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.time.Clock;

@RestController
@ApiIgnore
@Slf4j
public class AppErrorController implements ErrorController {
    private final Clock clock;

    public AppErrorController(Clock clock) {
        this.clock = clock;
    }

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(path = "error")
    public ResponseEntity<ApiError> handleError(HttpServletResponse servletResponse) {
        HttpStatus httpStatus = HttpStatus.resolve(servletResponse.getStatus());
        String message = "An unexpected error occurred";

        var apiError = new ApiError(this.clock.instant(), httpStatus, message);
        return ResponseEntity.status(httpStatus).body(apiError);
    }
}
