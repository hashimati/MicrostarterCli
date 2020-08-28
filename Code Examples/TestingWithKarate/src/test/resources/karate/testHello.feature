Feature: karate 'hello world' example
  Scenario: create and retrieve a cat

    Given url 'http://localhost:9090/hello'
    When method get
    Then status 200