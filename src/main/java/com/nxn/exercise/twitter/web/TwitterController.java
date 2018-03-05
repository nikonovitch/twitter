package com.nxn.exercise.twitter.web;

import com.nxn.exercise.twitter.domain.Tweet;
import com.nxn.exercise.twitter.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TwitterController {

    private final TwitterService service;

    @Autowired
    public TwitterController(TwitterService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/users/{username}/tweet")
    public ResponseEntity<?> tweet(@PathVariable String username, @RequestBody Message message){
        service.tweet(username, message.getContent());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/users/{username}/follow")
    public ResponseEntity<?> follow(@PathVariable("username") String follower, @RequestParam("followee_username") String followee){
        service.follow(follower, followee);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/users/{username}/wall")
    public List<Tweet> getWall(@PathVariable String username){
        return service.getWall(username);
    }
}
