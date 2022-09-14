package rahulshettyacademy.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest {

		//This complete end to end e-commerce automation has now become a test case with TestNG Framework
		@Test(groups = {"ErrorHandling"}, retryAnalyzer = rahulshettyacademy.TestComponents.Retry.class)
		public void LoginErrorValidation() throws IOException, InterruptedException
		{
		String productName = "ZARA COAT 3";
		landingPage.loginApplication("dummyemail@rsa.comm","Dummypassword@123");
		//Now, let's say we gave wrong email address/password
		Assert.assertEquals("Incorrect email password.", landingPage.getErrorMessage() ); // Compares the actual error message with obtained error message
		
		}
		
		@Test
		public void productErrorValidation() throws IOException, InterruptedException
		{
		String productName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("dummyemail@rsa.com","Dummypassword@123"); //calling the method to perform all the 3 operations using the object defined in the same method for next page- entering email, pass and licking login.
		List<WebElement> products = productCatalogue.getProductsList(); //using object of ProductCatalogue class, calling the method "getProductsList"
		productCatalogue.addProductToCart(productName); //Adding fetched product to cart
		CartPage cartPage = productCatalogue.goToCart(); /*Though "goToCart()" method is defined in "AbstractComponent" class,
		we can access the method through the object of "ProductCatalogue" class, as here "Inheritance" comes into play
		and as per Inheritance, child classes (ProductCatalogue, LandingPage) have access to parent class "AbstractComponent" methods. */
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33"); //Will return boolean result
		Assert.assertFalse(match); //If match is false, this will pass. And it can be in test classes because in page object classes, only code that performs some actions are written

		}
		
		/*
		 * Using the testng.xml file, below code lets us run all the 3 tests (2 error validations and 1 submitOrder()) together
		 * <?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Suite">
  <test thread-count="5" name="Submit Order Test">
    <classes>
      <class name="rahulshettyacademy.tests.SubmitOrderTest"/>
    </classes>
  </test> <!-- Test -->
  
  <test thread-count="5" name="Error Validations Test">
    <classes>
      <class name="rahulshettyacademy.tests.ErrorValidationsTest"/>
    </classes>
  </test> <!-- Test -->
  
</suite> <!-- Suite -->

		 */
		
		
		
		
}
