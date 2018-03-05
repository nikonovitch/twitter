package com.nxn.exercise.twitter.service;

import com.nxn.exercise.twitter.domain.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TwitterService {

    private static final int CHARACTERS_LIMIT = 140;

    private final TweetNest nest;

    @Autowired
    public TwitterService(TweetNest nest) {
        this.nest = nest;
    }

    public void tweet(String username, String message) {
        assertMessageIsValid(message);
        nest.save(username, message);
    }

    public List<Tweet> getWall(String username) {
        return nest.getTweetsFor(username);
    }

    public void follow(String follower, String followee) {
        nest.follow(follower, followee);
    }

    public List<Tweet> getTimeline(String username) {
        return nest.getFolloweesFor(username).stream()
                                             .flatMap(f -> nest.getTweetsFor(f).stream())
                                             .sorted(Comparator.comparing(Tweet::getCreatedAt).reversed())
                                             .collect(Collectors.toList());

    }

    private void assertMessageIsValid(String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message should contain at least 1 non-whitespace character.");
        }
        if (message.length() > CHARACTERS_LIMIT) {
            throw new IllegalArgumentException("Message exceeds 140 characters limit.");
        }
    }
}
