Feature: Job alert
  As a candidate,
  I want to create job alerts,
  So that I can recieve notifications about new open positions.

  @automated @desktop @jobAlert
  Scenario: Create Job alert
    Given I am on the home page
    When I perform search
    Then Japu is shown
    Then I check layout on "tablet"
    Then check email "email"
    Then I check performance