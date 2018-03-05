Feature: Posting
  User should be able to post a 140 character message.
  No prior authentication is required.
  User doesn't have to sign up in order to tweet.

  Scenario: Donald tweets a valid message
    When "Donald" posts a tweet within the characters limit, ie. "Despite the constant negative press covfefe"
    Then the response has a status code of 200

  Scenario: Donald tweets too long message
    When "Donald" posts a tweet that exceeds the characters limit
    Then the response has a status code of 400
    And it should contain the following:
      """
      {
	     "error" : "Message exceeds 140 characters limit."
      }
      """

  Scenario Outline: Donald tweets a message with no characters
    When <user> posts a <tweet> with no characters
    Then the response has a status code of 400
    And it should contain the following:
      """
      {
	     "error" : "Message should contain at least 1 non-whitespace character."
      }
      """

    Examples:
      | user     | tweet           |
      | "Donald" | ""              |
      | "Donald" | "             " |