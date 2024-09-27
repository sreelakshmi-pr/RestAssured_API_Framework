Feature: Validate add object & get object & put object & delete object end points

  Scenario: Verify user can add object and get object
    Given user wants to call "/objects" endpoint
    And set header "Content-Type" to "application/json"
    And set request body from the file "add_object.json"
    When user performs post call
    Then verify status code is 200
    And verify id is not empty
    And store created id into "object.id"
    When user wants to call "/objects/@id" endpoint
    And user performs get call
    Then verify status code is 200
    And verify response is same as the request passed in post call

  Scenario: Verify user can update object
    Given user wants to call "/objects/@id" endpoint
    And set header "Content-Type" to "application/json"
    And set request body from the file "update_object.json"
    When user performs put call
    Then verify status code is 200

  Scenario: Verify user can delete object
    Given user wants to call "/objects/@id" endpoint
    And set header "Content-Type" to "application/json"
    When user performs delete call
    Then verify status code is 200
    And verify response message is "Object with id = @id has been deleted."










