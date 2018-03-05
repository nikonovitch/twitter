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

    private static final int TWEET_CHARACTERS_LIMIT = 140
    private static final String FOLLOW_URL = "/users/{username}/follow?username={followee_username}"
    private static final String WALL_URL = "/users/{username}/wall"
    private static final String TWEET_URL = "/users/{username}/tweet"
    private static final String TIMELINE_URL = "/users/{username}/timeline"

    @Autowired
    protected TestRestTemplate template

    private ResponseEntity<String> response

    @Given("^\"([^\"]*)\" has already posted some tweets:\$")
    def hasAlreadyPostedSomeTweets(String username, DataTable table) throws Throwable {
        table.asList(String).each { tweet -> postTweet(username, tweet)}
    }

    @Given("^\"([^\"]*)\" has tweeted in the past\$")
    def hasTweetedInThePast(String username) throws Throwable {
        hasTweetedInThePast(username, "irrelevant tweet")
    }

    @Given("^\"([^\"]*)\" has tweeted \"([^\"]*)\" in the past\$")
    def hasTweetedInThePast(String username, String tweet) throws Throwable {
        postTweet(username, tweet)
    }

    @Given("^KimJong has never tweeted before\$")
    def kimJongHasNeverTweetedBefore() throws Throwable {
        // maybe one day, Kim...
    }

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

    @When("^\"([^\"]*)\" follows \"([^\"]*)\"\$")
    def follows(String follower, String followee) throws Throwable {
        def httpEntity = new HttpEntity<Object>(new HttpHeaders())
        response = template.exchange(FOLLOW_URL, POST, httpEntity, String, follower, followee)
    }

    @When("^\"([^\"]*)\" requests the contents of his wall\$")
    def requestsTheContentsOfHisWall(String username) throws Throwable {
        response = template.getForEntity(WALL_URL, String, username)
    }

    @When("^\"([^\"]*)\" requests the contents of his timeline\$")
    def requestsTheContentsOfHisTimeline(String username) throws Throwable {
        response = template.getForEntity(TIMELINE_URL, String, username)
    }

    @Then("^the response has a status code of (\\d+)\$")
    def theResponseHasAStatusCodeOf(int expectedStatusCode) throws Throwable {
        assertThat(response.getStatusCode().value(), is(expectedStatusCode))
    }

    @And("^it contains the tweets in the reversed chronological order:\$")
    def itContainsTheTweetsInTheReversedChronologicalOrder(String expectedResponse) throws Throwable {
        JSONAssert.assertEquals(expectedResponse, response.getBody(), JSONCompareMode.LENIENT)
    }

    def postTweet(String username, String tweet) {
        template.exchange(TWEET_URL, POST, constructTweetRequest(tweet), String, username)
    }

    static def constructTweetRequest(String tweet) {
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        def jsonBody = '{"message":"' + tweet + '"}'
        new HttpEntity(jsonBody, headers)
    }
}
