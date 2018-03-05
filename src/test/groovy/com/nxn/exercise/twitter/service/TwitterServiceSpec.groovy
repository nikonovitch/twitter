package com.nxn.exercise.twitter.service

import com.nxn.exercise.twitter.domain.Tweet
import spock.lang.Specification

import static java.time.ZonedDateTime.now

class TwitterServiceSpec extends Specification {

    def nest = Mock(TweetNest)
    def service = new TwitterService(nest)

    def "Should persist the incoming valid tweet"(){
        given:
        def username = "Donald"
        def message = "And the FAKE NEWS winners are..."

        when:
        service.tweet(username, message)

        then:
        1 * nest.save(username, message)
    }

    def "Should throw an exception for an empty tweet"(){
        when:
        service.tweet("Donald", "   ")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Message should contain at least 1 non-whitespace character."
    }

    def "Should throw an exception for a too long tweet"(){
        when:
        service.tweet("Donald", superLongTweet)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Message exceeds 140 characters limit."
    }

    def "Should return an existing tweet collection upon request"(){
        given:
        def username = "Donald"
        def tweets = donaldsTweets()
        when:
        def wall = service.getWall(username)

        then:
        1 * nest.getTweetsFor(username) >> tweets.reverse()
        and: "the tweets are in a reversed chronological order"
        wall == tweets.reverse()
    }

    def "Should follow the followee"(){
        given:
        def follower = "Donald"
        def followee = "Hilary"

        when:
        service.follow(follower, followee)

        then:
        1 * nest.follow(follower, followee)
    }

    def superLongTweet = """\
            If the E.U. wants to further increase their already massive tariffs and barriers on U.S. companies \
            doing business there, we will simply apply a Tax on their Cars which freely pour into the U.S. \
            They make it impossible for our cars (and more) to sell there. Big trade imbalance!"""

    def donaldsTweets(){
        [new Tweet("Donald", "message1", now()),
         new Tweet("Donald", "message2", now()),
         new Tweet("Donald", "message3", now())]
    }
}
