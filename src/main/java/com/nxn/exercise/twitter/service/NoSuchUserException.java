package com.nxn.exercise.twitter.service;

public class NoSuchUserException extends RuntimeException {
    NoSuchUserException(String message) {
        super(message);
    }
}
