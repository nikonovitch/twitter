package com.nxn.exercise.twitter.service;

import com.nxn.exercise.twitter.domain.Tweet;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.*;

@Component
public class TweetNest {

    private Map<String, LinkedList<Tweet>> usersToTweets = new HashMap<>();

    public synchronized void persist(String username, String message) {
        Tweet tweet = createTweet(username, message);
        usersToTweets.computeIfAbsent(username, k -> new LinkedList<>()).push(tweet);
    }

    private Tweet createTweet(String username, String message) {
        return new Tweet(username, message, ZonedDateTime.now());
    }

    public synchronized List<Tweet> getTweetsFor(String username) {
        return usersToTweets.getOrDefault(username, new LinkedList<>());
    }

    public synchronized void clear(){
        usersToTweets.clear();
    }
}
