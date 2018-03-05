package com.nxn.exercise.twitter.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static com.nxn.exercise.twitter.step_definitions.ApplicationE2EStepDefs.POST_URL
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class TwitterControllerTest extends Specification {

    @Autowired
    MockMvc mvc

    def "Should return OK when tweeting a valid message"() {
        when:
        def result = mvc.perform(post(POST_URL, "Donald")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validTweet))

        then:
        result.andExpect(status().is2xxSuccessful())
    }

    def "Should return error when the message is too long"() {
        when:
        def response = mvc.perform(post(POST_URL, "Donald")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tooLongTweet)).andReturn().response

        then:
        response.status == 400
        and:
        response.contentAsString == '{"error":"Message exceeds 140 characters limit."}'
    }

    def "Should return error when the message has no characters"() {
        when:
        def response = mvc.perform(post(POST_URL, "Donald")
                .contentType(MediaType.APPLICATION_JSON)
                .content(emptyTweet)).andReturn().response

        then:
        response.status == 400
        and:
        response.contentAsString == '{"error":"Message should contain at least 1 non-whitespace character."}'
    }

    def validTweet =
            """
            {
              "message": "Despite the constant negative press covfefe"
            }
            """

    def tooLongTweet =
            """
            {
              "message": "The United States has an \$800 Billion Dollar Yearly Trade Deficit \
              because of our “very stupid” trade deals and policies. \
              Our jobs and wealth are being given to other countries that have taken advantage of us for years. \
              They laugh at what fools our leaders have been. No more!"
            }
            """

    def emptyTweet =
            """
            {
              "message": "       "
            }
            """
}
