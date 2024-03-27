@post
Feature: Post messages

  @happy @test @ui @profile @regression
  Scenario: Post a simple text
    Given user is logged in
    And user navigates to [Post] page
    When user enters "Go! Re:boot!" in the textbox
    And user clicks on [Post] button
    Then user sees success "Post created." message
