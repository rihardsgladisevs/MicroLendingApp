Feature: Successful loan extension
  Scenario: Client extends valid loan, expecting increase of interest and end date
    Given client has successful loan
    When client extends the loan expecting status code 200
    Then client has registered extension for given loan
    And loan's interest is increased by a factor of 1.50
    And loan's end date is increased by 1 week