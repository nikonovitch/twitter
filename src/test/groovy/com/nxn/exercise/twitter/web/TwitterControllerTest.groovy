package com.nxn.exercise.twitter.web

import com.nxn.exercise.twitter.service.TweetNest
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static com.nxn.exercise.twitter.step_definitions.ApplicationE2EStepDefs.POST_URL
import static com.nxn.exercise.twitter.step_definitions.ApplicationE2EStepDefs.WALL_URL
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class TwitterControllerTest extends Specification {

    @Autowired
    MockMvc mvc

    @Autowired
    TweetNest nest

    def setup(){
        nest.clear()
    }

    def "Should return OK when tweeting a valid message"() {
        when:
        def result = tweet(validTweetMessage)

        then:
        result.andExpect(status().is2xxSuccessful())
    }

    def "Should return error when the message is too long"() {
        when:
        def response = tweet(tooLongTweet).andReturn().response

        then:
        response.status == 400
        and:
        response.contentAsString == '{"error":"Message exceeds 140 characters limit."}'
    }

    def "Should return error when the message has no characters"() {
        when:
        def response = tweet(emptyTweet).andReturn().response

        then:
        response.status == 400
        and:
        response.contentAsString == '{"error":"Message should contain at least 1 non-whitespace character."}'
    }

    def tweet(String message) {
        mvc.perform(post(POST_URL, "Donald")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"message":"${message}"}"""))
    }

    def "Should return collection of all previously posted tweets upon request"(){
        given: "Donald has already posted some tweets"
        tweet("I have never seen a thin person drinking  Diet Coke.")
        tweet("Today I have finally seen a thin person drinking Diet Coke.")
        tweet("It was me. ")

        when:
        def response = mvc.perform(get(WALL_URL, "Donald")).andReturn().response

        then:
        response.status == 200
        and:
        JSONAssert.assertEquals(expectedWall, response.contentAsString, JSONCompareMode.LENIENT)
    }

    def validTweetMessage = "Despite the constant negative press covfefe"

    def tooLongTweet =
            """\
              The United States has an \$800 Billion Dollar Yearly Trade Deficit \
              because of our “very stupid” trade deals and policies. \
              Our jobs and wealth are being given to other countries that have taken advantage of us for years. \
              They laugh at what fools our leaders have been. No more!\
            """

    def emptyTweet = "       "

    def expectedWall = """\
      [
        {
          "message": "It was me. ",
          "author": "Donald"
        },
        {
          "message": "Today I have finally seen a thin person drinking Diet Coke.",
          "author": "Donald"
        },
        {
          "message": "I have never seen a thin person drinking  Diet Coke.",
          "author": "Donald"
        }
      ]
      """
}
