package com.nxn.exercise.twitter.service;

import com.nxn.exercise.twitter.domain.Message;
import org.springframework.stereotype.Service;

@Service
public class TwitterService {

    private static final int CHARACTERS_LIMIT = 140;

    public void tweet(String username, Message message) {
        if (message.getLength() > CHARACTERS_LIMIT) {
            throw new IllegalArgumentException("Message exceeds 140 characters limit.");
        }
        if (message.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Message should contain at least 1 non-whitespace character.");
        }
    }
}
