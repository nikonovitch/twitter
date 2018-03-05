package com.nxn.exercise.twitter.step_definitions

import com.nxn.exercise.twitter.TwitterApplication
import cucumber.api.DataTable
import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration

import static org.hamcrest.core.Is.is
import static org.junit.Assert.assertThat
import static org.springframework.http.HttpMethod.POST

@SpringBootTest(classes = TwitterApplication, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
class ApplicationE2EStepDefs {

    public static final int TWEET_CHARACTERS_LIMIT = 140

    @Autowired
    protected TestRestTemplate template

    private ResponseEntity<String> response

    @When("^\"([^\"]*)\" posts a tweet within the characters limit, ie\\. \"([^\"]*)\"\$")
    def postsATweetWithinTheCharactersLimitIe(String username, String tweet) throws Throwable {
        response = postTweet(username, tweet)
    }

    @When("^\"([^\"]*)\" posts a tweet that exceeds the characters limit\$")
    def postsATweetThatExceedsTheCharactersLimit(String username) throws Throwable {
        def tooLongTweet = String.join("", Collections.nCopies(TWEET_CHARACTERS_LIMIT + 1, "."))
        response = postTweet(username, tooLongTweet)
    }

    @When("^\"([^\"]*)\" posts a \"([^\"]*)\" with no characters\$")
    def userPostsATweetWithNoCharacters(String username, String tweet) throws Throwable {
        response = postTweet(username, tweet)
    }

    @Then("^the response has a status code of (\\d+)\$")
    def theResponseHasAStatusCodeOf(int expectedStatusCode) throws Throwable {
        assertThat(response.getStatusCode().value(), is(expectedStatusCode))
    }

    @Given("^\"([^\"]*)\" has already posted some tweets:\$")
    def hasAlreadyPostedSomeTweets(String username, DataTable table) throws Throwable {
        table.asList(String).each { tweet -> postTweet(username, tweet)}
    }

    @When("^\"([^\"]*)\" requests the contents of his wall\$")
    def requestsTheContentsOfHisWall(String username) throws Throwable {
        response = template.getForEntity("/users/" + username + "/wall", String)
    }

    @And("^it contains the tweets in the reversed chronological order:\$")
    def itContainsTheTweetsInTheReversedChronologicalOrder(String expectedResponse) throws Throwable {
        JSONAssert.assertEquals(expectedResponse, response.getBody(), JSONCompareMode.LENIENT)
    }

    def postTweet(String username, String tweet) {
        template.exchange("/users/" + username + "/tweet", POST, constructTweetRequest(tweet), String)
    }

    static def constructTweetRequest(String tweet) {
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        def jsonBody = '{"message":"' + tweet + '"}'
        new HttpEntity(jsonBody, headers)
    }
}
