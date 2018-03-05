package com.nxn.exercise.twitter.service;

import com.nxn.exercise.twitter.domain.Tweet;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.*;

@Component
public class TweetNest {

    private Map<String, LinkedList<Tweet>> usersToTweets = new HashMap<>();

    private Map<String, List<String>> followersToFollowees = new HashMap<>();

    public synchronized void save(String username, String message) {
        saveTweet(username, message);
        saveUserIfNecessary(username);
    }

    private void saveTweet(String username, String message) {
        Tweet tweet = createTweet(username, message);
        usersToTweets.computeIfAbsent(username, k -> new LinkedList<>()).push(tweet);
    }

    private Tweet createTweet(String username, String message) {
        return new Tweet(username, message, ZonedDateTime.now());
    }

    private void saveUserIfNecessary(String username) {
        followersToFollowees.computeIfAbsent(username, k -> new ArrayList<>());
    }

    public synchronized List<Tweet> getTweetsFor(String username) {
        if (!usersToTweets.containsKey(username)){
            return throwNoSuchUserException(username);
        }
        return usersToTweets.get(username);
    }

    private List<Tweet> throwNoSuchUserException(String username) {
        throw new NoSuchUserException("User \"" + username + "\" does not exist.");
    }

    public synchronized void clear(){
        usersToTweets.clear();
    }

    public synchronized void follow(String follower, String followee) {
        assertUsersExist(follower, followee);
        followersToFollowees.get(follower).add(followee);
    }

    private void assertUsersExist(String follower, String followee) {
        if (!followersToFollowees.containsKey(follower)){
            throwNoSuchUserException(follower);

        }
        if (!followersToFollowees.containsKey(followee)){
            throwNoSuchUserException(followee);

        }
    }
}
