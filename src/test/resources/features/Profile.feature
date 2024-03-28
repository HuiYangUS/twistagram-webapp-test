@profile
Feature: [Profile] page functionalities

  @happy @test @ui @regression
  Scenario: [Happy] - Editing profile
    Given user is logged in
    And user navigates to [Profile] page
    When user clicks on [Edit Profile] button
    And user enters new name, username and bio information
      | name     | QA Tester     |
      | username | qatest        |
      | bio      | This is test- |
    And user clicks on [Save] button
    Then user sees new updated profile page
