@login
Feature: Login

  @happy @google @smoke @test
  Scenario: Log in [Twistagram] app using [Google] credentials
    Given user navigates to [Twistagram] application
    When user clicks on [Log in with Google] button
    And some other action
    Then user is on the home page
