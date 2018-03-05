package com.nxn.exercise.twitter.domain;

import lombok.Data;

import java.time.ZonedDateTime;

public @Data class Tweet {
    private final String author;
    private final String message;
    private final ZonedDateTime createdAt;
}
