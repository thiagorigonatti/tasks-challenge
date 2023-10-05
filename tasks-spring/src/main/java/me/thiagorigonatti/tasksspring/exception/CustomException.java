package me.thiagorigonatti.tasksspring.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record CustomException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {

}
