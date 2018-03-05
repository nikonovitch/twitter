package com.nxn.exercise.twitter.service

import spock.lang.Specification

class TweetNestSpec extends Specification {

    def nest = new TweetNest()

    def "Should persist the incoming tweet"(){
        given:
        def username = "Donald"
        def message = "IF YOU DON’T HAVE STEEL, YOU DON’T HAVE A COUNTRY!"

        when:
        nest.save(username, message)

        then:
        def persistedTweets = nest.getTweetsFor(username)
        persistedTweets != null
        and:
        with(persistedTweets[0]){ tweet ->
            tweet.author == username
            tweet.message == message
        }
    }

    def "Should throw exception when getting tweets for non-existing user"(){
        given:
        def nonExistingUser = "nonExistingUser"

        when:
        nest.getTweetsFor(nonExistingUser)

        then:
        def e = thrown(NoSuchUserException)
        e.message == "User \"" + nonExistingUser + "\" does not exist."
    }

    def "Should throw exception when following if follower is a non-existing user"(){
        given:
        def nonExistingUser = "nonExistingUser"

        when:
        nest.follow(nonExistingUser, "anyOtherUser")

        then:
        def e = thrown(NoSuchUserException)
        e.message == "User \"" + nonExistingUser + "\" does not exist."
    }

    def "Should throw exception when following if followee is a non-existing user"(){
        given:
        def existingUser = "existingUser"
        nest.save(existingUser, "tweet")

        and:
        def nonExistingUser = "nonExistingUser"

        when:
        nest.follow(existingUser, nonExistingUser)

        then:
        def e = thrown(NoSuchUserException)
        e.message == "User \"" + nonExistingUser + "\" does not exist."
    }

    def "Should return followees for a given follower"(){
        given: "follower exists"
        def follower = "Donald"
        nest.save(follower, "tweet")

        and: "followee exists too"
        def followee = "Hillary"
        nest.save(followee, "another tweet")

        and: "follower follows followee"
        nest.follow(follower, followee)

        when:
        def followees = nest.getFolloweesFor(follower)

        then:
        followees.size() == 1
        and:
        followees[0] == followee
    }

    def "Should throw exception when getting followees if follower is a non-existing user"(){
        given:
        def nonExistingUser = "nonExistingUser"

        when:
        nest.getFolloweesFor(nonExistingUser)

        then:
        def e = thrown(NoSuchUserException)
        e.message == "User \"" + nonExistingUser + "\" does not exist."
    }
}
