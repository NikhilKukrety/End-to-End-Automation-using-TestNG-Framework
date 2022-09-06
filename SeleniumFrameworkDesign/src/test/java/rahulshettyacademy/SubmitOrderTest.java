package rahulshettyacademy;

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

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class SubmitOrderTest {

	public static void main(String[] args) {

		String productName = "ZARA COAT 3";
		/*We don't need Selenium WebDriver in local, instead we can download webDriver manager from pom.xml file.
		*Download - WebDriverManager from Maven repository and add the dependency.
		*So, instead of giving System.setProperty.. just give below: */	
		WebDriverManager.chromedriver().setup();
		//Creating object of ChromeDriver:
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //Max timeout
		driver.manage().window().maximize();
		/*We have created a class called "LandingPage" in main folder for Page Object Concept.
		 * So defining object of "LandingPage" class, and then passing the driver which has life to that class.
		 * And in the "LandingPage" class, we are retrieving the value of this driver and storing it to local driver there: */
		LandingPage landingpage = new LandingPage(driver);
		landingpage.goTo(); //Navigating to application URL.
		ProductCatalogue productCatalogue = landingpage.loginApplication(); //calling the method to perform all the 3 operations using the object defined in the same method for next page- entering email, pass and licking login.
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
		//Closing the browser:
		driver.close();
		
	}

}
