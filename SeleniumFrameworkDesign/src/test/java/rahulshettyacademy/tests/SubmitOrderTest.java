package rahulshettyacademy.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.OrderPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest{

		String productName = "ZARA COAT 3";
		
		//This complete end to end e-commerce automation has now become a test case with TestNG Framework
		@Test(dataProvider = "getData", groups = {"Purchase"}) //Means this method will pick up the test data given in getData method
		public void submitOrder(String email, String password, String productName) throws IOException, InterruptedException
		{
			
		ProductCatalogue productCatalogue = landingPage.loginApplication(email,password); //calling the method to perform all the operations using the object defined in the same method for next page - clicking login. Also, the test data is getting picked up from JSON format defined test data in getData method
		List<WebElement> products = productCatalogue.getProductsList(); //using object of ProductCatalogue class, calling the method "getProductsList"
		productCatalogue.addProductToCart(productName); //Adding fetched product to cart
		CartPage cartPage = productCatalogue.goToCart(); /*Though "goToCart()" method is defined in "AbstractComponent" class,
		we can access the method through the object of "ProductCatalogue" class, as here "Inheritance" comes into play
		and as per Inheritance, child classes (ProductCatalogue, LandingPage) have access to parent class "AbstractComponent" methods. */
		Boolean match = cartPage.VerifyProductDisplay(productName); //Will return boolean result
		Assert.assertTrue(match); //If match is true, this will pass. And it can be in test classes because in page object classes, only code that performs some actions are written
		//Clicking on "Checkout" button:
		CheckoutPage checkoutPage = cartPage.checkOut();
		checkoutPage.selectCountry("India"); //Giving the country name
		ConfirmationPage confirmationPage = checkoutPage.submitOrder(); //Submitting the order
		String confirmMessage = confirmationPage.getConfirmationMessage(); //Getting the confirmation message
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
		}
		
		@Test(dependsOnMethods = {"submitOrder"}) //The below test will run after submitOrder is run because in below method, we are verifying if the product is added to the orders list
		public void orderHistoryTest()
		{
			//So, in order to check the product added, atleast we should be logged in:
			ProductCatalogue productCatalogue = landingPage.loginApplication("dummyemail@rsa.com","Dummypassword@123");
			//Clicking on "Orders" link (this link is common to all the pages)
			OrderPage ordersPage = productCatalogue.goToOrdersPage(); //This method clicks the link and then returns the object "ordersPage". So, its return type is object, so storing it in object "ordersPage".
			Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName)); //This method returns true if match is found. So applying Assert of true on this method to check if step will be passed if match is found
			
		}
		
		//To run tests in parallel, modify below line like this in "testng.xml" file:
		// <suite parallel = "tests" name="Suite">
		//Methods will run in parallel  - <suite parallel = "methods" name="Suite">
		
		//By doing below, we can provide test data to our tests from JSON format organized test data:
		@DataProvider
		public Object[][] getData()
		{
			return new Object[][] {{"dummyemail@rsa.com","Dummypassword@123","ZARA COAT 3"},{"kukrety@gmail.com","Dummypassword@123","ADIDAS ORIGINAL"}};
		}
		
		
		
		
		
		

}
