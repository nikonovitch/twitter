package com.nxn.exercise.twitter.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data class Message {
    @JsonProperty("message") private String content;
}
