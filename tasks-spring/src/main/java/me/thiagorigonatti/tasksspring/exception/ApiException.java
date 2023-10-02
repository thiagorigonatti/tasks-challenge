package me.thiagorigonatti.tasksspring.exception;

public class ApiException extends RuntimeException{


    public ApiException(String message) {
        super(message);
    }
}
