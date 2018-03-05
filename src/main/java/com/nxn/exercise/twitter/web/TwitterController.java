package com.nxn.exercise.twitter.web;

import com.nxn.exercise.twitter.domain.Message;
import com.nxn.exercise.twitter.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
