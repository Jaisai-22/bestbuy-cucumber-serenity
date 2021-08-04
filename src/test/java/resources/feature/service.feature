Feature: Testing different requests on the best buy service application

  Scenario: Verify if the best buy services application can be accessed by users
    When Users sends a GET request to the services endpoint, they must get back a valid status code 200


    Scenario: Create a new services and verify if the service is added
      When I create a new services by providing the information name
      Then I verify the services is created

      Scenario: Getting services information by Id
        When I send GET request to services endpoint Id "Id",I should received the services information

        Scenario: Update a created services and verify if the service is updated
          When I update a created services providing the new name
          Then I verify the services is updated

          Scenario: Delete a created services and verify the services is deleted
            When I delete a created services, they must get back a valid status code is 200
            Then I verify the services is created is deleted