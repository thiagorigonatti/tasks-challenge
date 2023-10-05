package me.thiagorigonatti.tasksspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

@ControllerAdvice
public class ValidatorExceptionHandler {

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<CustomException> handleApiException(MethodArgumentNotValidException e) {

    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    CustomException exception = new CustomException(Objects.requireNonNull(e.getFieldError("cost"))
      .getDefaultMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
    return new ResponseEntity<>(exception, httpStatus);

  }

}
