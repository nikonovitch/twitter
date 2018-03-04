Feature: Post a message
  User should be able to post a 140 character message.
  No prior authentication is required.
  User doesn't have to sign up in order to tweet.

  Scenario: Donald posts a valid tweet
    When "Donald" posts a tweet within the characters limit, ie. "Despite the constant negative press covfefe"
    Then the response has a status code of 200

  Scenario: Donald posts too long tweet
    When "Donald" posts a tweet that exceeds the characters limit
    Then the response has a status code of 403

  Scenario Outline: Donald posts a tweet with no characters
    When <user> posts a <tweet> with no characters
    Then the response has a status code of 403

    Examples:
      | user     | tweet           |
      | "Donald" | ""              |
      | "Donald" | "             " |