package com.nxn.exercise.twitter.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public @Data class Message {
    @JsonProperty("message") private String content;
}
