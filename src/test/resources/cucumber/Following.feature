Feature: Following
  A user should be able to follow another user.
  Following doesn't have to be reciprocal: Alice can follow Bob without Bob having to follow Alice.

  Scenario: Donald follows Hillary
    Given "Hilary" has tweeted in the past
    When "Donald" follows "Hilary"
    Then the response has a status code of 200

  Scenario: Donald follows Angela
    Given Angela has never tweeted before
    When "Donald" follows "Angela"
    Then the response has a status code of 404