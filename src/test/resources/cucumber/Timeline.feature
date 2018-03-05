Feature: Timeline
  A user should be able to see a list of the messages posted by all the people they follow, in reverse chronological order.

  Scenario: Donald requests his timeline
    Given "Donald" has tweeted "Crooked Hillary is the worst (and biggest) loser of all time." in the past
    And "Hillary" has tweeted "Delete your account." in the past
    And "Angela" has tweeted "Sehenswürdigkeiten" in the past

    And "Donald" follows "Hillary"
    And "Donald" follows "Angela"

    When "Donald" requests the contents of his timeline

    Then the response has a status code of 200
    And it should contain the following:
      """
      [
        {
          "message": "Sehenswürdigkeiten",
          "author": "Angela"
        },
        {
          "message": "Delete your account.",
          "author": "Hillary"
        }
      ]
      """
