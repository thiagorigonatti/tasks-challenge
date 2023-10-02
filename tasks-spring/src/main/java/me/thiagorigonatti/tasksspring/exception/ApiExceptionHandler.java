package me.thiagorigonatti.tasksspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException e) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        CustomException exception = new CustomException(e.getMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(exception, httpStatus);

    }

}
