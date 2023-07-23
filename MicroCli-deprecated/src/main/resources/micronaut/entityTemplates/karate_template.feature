Feature: Testing ${Entity}
  Background:
    * def rootURL = 'http://localhost:8080/api/${entityName}s/'
    * def user = {id: 'hashimatiRest', password:'helloworld'}


  Scenario: Saving ${Entity} Object
    Given url rootURL.concat('save')
    And request {
    When method post
    Then status 200
    And match


  Scenario: Reading ${Entity} Object
    Given url rootURL.concat('save')
    And request {
    When method post
    Then status 200