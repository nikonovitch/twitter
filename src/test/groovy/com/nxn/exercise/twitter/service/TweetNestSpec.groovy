package com.nxn.exercise.twitter.service

import spock.lang.Specification

class TweetNestSpec extends Specification {

    def nest = new TweetNest()

    def "Should persist the incoming tweet"(){
        given:
        def username = "Donald"
        def message = "IF YOU DON’T HAVE STEEL, YOU DON’T HAVE A COUNTRY!"

        when:
        nest.persist(username, message)

        then:
        def persistedTweets = nest.getTweetsFor(username)
        persistedTweets != null
        and:
        with(persistedTweets[0]){ tweet ->
            tweet.author == username
            tweet.message == message
        }
    }
}
