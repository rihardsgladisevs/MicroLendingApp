Feature: Successful loan application
  Scenario: Client make an application for a loan with valid data
    Given client need a loan with amount EUR 200 for term of 20 days
    When client applied for a loan with status 200
    Then client has loan in history