
@tag
Feature: Purchase the order from ECommerce Website
  I want to use this template for my feature file
  
  Background:
  Given I landed on ECommerce Page

  @tag2
  Scenario Outline: Positive test of submitting the order
    Given I am logged in with username <name> and password <password>
    When I add product <productName> to Cart
    And Checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." should be displayed on ConfirmationPage

    Examples: 
      | name                 | password    			 | productName |
      |dummyemail@rsa.com		 | Dummypassword@123 | ZARA COAT 3 |
