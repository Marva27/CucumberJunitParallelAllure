@Bing
Feature: Bing Website
  I want to test the Bing Website

  @BingSearch
  Scenario Outline: Verify Bing Search Functionality
    Given I connect to https://www.bing.com
    Then I should see Bing website

    Examples: 
      | name  | value | status  |
      | name1 |     5 | success |
      | name2 |     7 | Fail    |
