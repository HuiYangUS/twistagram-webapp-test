@login
Feature: Login

  @happy @google @smoke @test @ui
  Scenario: Log in [TwistaGram] app using [Google] credentials
    Given user navigates to [TwistaGram] application
    When user clicks on [Log in with Google] button
    And user enters gmail
    And user enters gmail password
    And user confirms to login by clicking [Continue] button
    Then user is logged in and is on the [Home] page
