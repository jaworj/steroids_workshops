Feature: Job alert
  As a candidate,
  I want to create job alerts,
  So that I can recieve notifications about new open positions.

  @automated @desktop @jobAlert
  Scenario: Create Job alert
    Given I am on the home page
    When I perform search
    Then Japo is shown
    Then I type random email into Japo
    And I save JobAgent from Japo
    Then Check JobAgent confirmation email
    #Then I check layout on "tablet"
    #Then I check performance
