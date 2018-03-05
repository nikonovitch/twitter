Feature: Wall
  A user should be able to see a list of the messages they've posted, in reverse chronological order.

  Scenario: Donald requests his wall
    Given "Donald" has already posted some tweets:
      | "I have never seen a thin person drinking  Diet Coke."        |
      | "Today I have finally seen a thin person drinking Diet Coke." |
      | "It was me. "                                                 |

    When "Donald" requests the contents of his wall

    Then the response has a status code of 200
    And it should contain the following:
      """
      [
        {
          "message": "It was me. ",
          "username": "Donald"
        },
        {
          "message": "Today I have finally seen a thin person drinking Diet Coke.",
          "username": "Donald"
        },
        {
          "message": "I have never seen a thin person drinking  Diet Coke.",
          "username": "Donald"
        }
      ]
      """