Feature: Testing different requests on the best buy categories application

  Scenario: Verify if the best buy categories application can be accessed by users
    When User sends a GET request to categories endpoint, they must get back a valid status code 200

  Scenario:  Create a new categories and verify if categories is added
    When I create new categories by providing the information name and id
    Then I verify the categories is created

  Scenario: Getting categories information by Id
    When I send GET request to categories endpoint with Id "id" , I should received the categories information

  Scenario: Update the created categories record and verify if the categories is updated
    When I update a created categories providing the new name
    Then I verify the categories is updated

  Scenario: Delete a created categories and verify the categories is deleted
    When I delete a created categories , they must get back a valid status code is 200
    Then I verify the categories is deleted
