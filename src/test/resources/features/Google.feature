@Google
Feature: Google Feature
  I want to test the Google Website

  @HomePage
  Scenario: Google Home Page
    Given I connect to https://www.google.com
    Then I should see Google Home page

  @Search
  Scenario Outline: Google Search
    Given I connect to https://www.google.com
    And I search for "<SEARCH>"
    Then I should see the search results for "<SEARCH>"

    Examples: 
      | SEARCH |
      | Apple  |
      | Orange |
