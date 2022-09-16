package rahulshettyacademy.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class StepDefinitionImpl extends BaseTest{
	
	//Use chrome extension "Tidy Gherkin" in chrome browser. This will automatically generate all the below step definitions for us
	
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;
	String confirmMessage;
	
	@Given("I landed on ECommerce Page")
	public void I_landed_on_ECommerce_Page() throws IOException
	{
		landingPage = launchApplication();
	}
	
	@Given("^I am logged in with username (.+) and password (.+)$") //(.+) A regular expression which means accept all values here and then compare. And ^ and $ are regular expressions (RegEx) because we are excepting values in this sentence
	public void I_am_logged_in_with_username_and_password(String username, String password) //Cucumber automatically catches values from (.+) and stores it in string
	{
		productCatalogue = landingPage.loginApplication(username, password);
	}
	
	@When("^I add product (.+) to Cart$")
	public void I_add_product_to_Cart(String productName)
	{
		List<WebElement> products = productCatalogue.getProductsList();
		productCatalogue.addProductToCart(productName);
		
	}
	
	@And("^Checkout (.+) and submit the order$")
	public void Checkout_and_submit_the_order(String productName)
	{
		CartPage cartPage = productCatalogue.goToCart();
		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.checkOut();
		checkoutPage.selectCountry("India");
		confirmationPage = checkoutPage.submitOrder();
	}
	
	@Then("{string} should be displayed on ConfirmationPage")
	public void message_to_be_displayed_on_ConfirmationPage(String string)
	{
		confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		driver.close();
	}

}
