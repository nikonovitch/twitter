package com.nxn.exercise.twitter.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public @Data class Message {
    @JsonProperty("message") private String content;

    public String getContent(){
        return (content != null ? content : "");
    }

    public int getLength() {
        return getContent().length();
    }
}
