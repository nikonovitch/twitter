package com.nxn.exercise.twitter.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<TwitterErrorResponse> illegalArgumentException(IllegalArgumentException ex){
        return new ResponseEntity<>(new TwitterErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
