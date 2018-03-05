Feature: Following
  A user should be able to follow another user.
  Following doesn't have to be reciprocal: Alice can follow Bob without Bob having to follow Alice.
  User cannot follow another one if either of them has not tweeted before (has not been registered).

  Scenario: Donald follows Hillary
    Given "Donald" has tweeted in the past
    And "Hilary" has tweeted in the past
    When "Donald" follows "Hilary"
    Then the response has a status code of 200

  Scenario: Donald follows KimJong
    Given "Donald" has tweeted in the past
    But KimJong has never tweeted before
    When "Donald" follows "KimJong"
    Then the response has a status code of 404
    And it should contain the following:
      """
      {
	     "error" : "User \"KimJong\" does not exist."
      }
      """