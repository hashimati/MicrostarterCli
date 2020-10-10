Feature: Testing Restaurant User
  Background:
    * def rootURL = 'http://localhost:8080/api/'
    * def saveRoute = 'save/'
    * def findByIdRoute= 'get/'
    * def findAllRoute= 'findAll/'
    * def updateRoute= 'update/'
    * def deleteRoute= 'delete/'
    * def ${entityName} = ${entityObject}
    * def id = 0

  Scenario: Saving ${className}
    Given url rootURL.concat(saveRoute)
    And request ${entityName}
    And headers {Content-Type : 'application/json', Accept : 'application/json'}
    When method post
    Then status 200
    And print response
    And assert (response.id != Null)
    And id = response.id

  Scenario: Find By Id
    Given url rootURL.concat(findByIdRoute)
    And request customer
    When method get
    Then status 200
    And assert (response == 'Success' || response == 'Failed')

  Scenario: Find All ${className} objects
    Given url rootURL.concat(findAllRoute)
    When method get
    Then status 200
    And assert (response != null)
    And print response

