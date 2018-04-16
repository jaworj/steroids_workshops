Feature: Job alert
  As a candidate,
  I want to create job alerts,
  So that I can receive notifications about new open positions.

  @automated @desktop @jobAlert
  Scenario: Create and confirm JobAgent
    Given  I am on the home page
    When   I perform search
    Then   JobAgent Popover is shown
    Then   I type random email into JobAgent Popover
    And    I save JobAgent from JobAgent Popover
    Then   Open JobAgent confirmation email
    Then   I check layout on "mobile"
    When   I click confirmation button
    Then   I am on Password Set page
    #Then  I check performance

