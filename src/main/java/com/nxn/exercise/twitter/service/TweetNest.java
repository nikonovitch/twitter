package com.nxn.exercise.twitter.service;

import com.nxn.exercise.twitter.domain.Tweet;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.*;

@Component
public class TweetNest {

    private Map<String, LinkedList<Tweet>> usersToTweets = new HashMap<>();

    private Map<String, Set<String>> followersToFollowees = new HashMap<>();

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
        followersToFollowees.computeIfAbsent(username, k -> new HashSet<>());
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

    public synchronized void follow(String follower, String followee) {
        assertUserExist(follower);
        assertUserExist(followee);
        followersToFollowees.get(follower).add(followee);
    }

    private void assertUserExist(String username) {
        if (!followersToFollowees.containsKey(username)){
            throwNoSuchUserException(username);
        }
    }

    public Set<String> getFolloweesFor(String follower) {
        assertUserExist(follower);
        return followersToFollowees.get(follower);
    }

    public synchronized void clear(){
        usersToTweets.clear();
    }
}
