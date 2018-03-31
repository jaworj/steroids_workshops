Feature: Job alert
  As a candidate,
  I want to create job alerts,
  So that I can recieve notifications about new open positions.

  @automated @desktop @jobAlert
  Scenario: Create Job alert
    Given I am on the home
    When the search phrase "panda" is entered
    Then results for "panda" are shown
    Then I check layout on "tablet"
#    Then check email "email"
    Then I check performance