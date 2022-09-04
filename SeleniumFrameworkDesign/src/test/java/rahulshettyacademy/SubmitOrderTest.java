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
		landingpage.loginApplication(); //calling the method to perform all the 3 operations - entering email, pass and licking login.
		ProductCatalogue productCatalogue = new ProductCatalogue(driver); //Creating object of ProductCatalogue class
		List<WebElement> products = productCatalogue.getProductsList(); //using object of ProductCatalogue class, calling the method "getProductsList"
		productCatalogue.addProductToCart(productName); //Adding fetched product to cart
		
		
		
		//Now, once above two expected conditions are met, we will click on "Cart" to move on:
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		//Now, we want to validate that among the selected items, "ZARA COAT 3" is present or not. So capture the elements, store them in list and iterate:
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		
		//Using stream to iterate over the cartProducts, and capturing the text "ZARA COAT 3":
		//Also, we need to check if grabbed text gets us true or false, so "anymatch" is used instead of "filter" for boolean result:
		Boolean match = cartProducts.stream().anyMatch(s->s.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match); //If match is true, this will pass
		
		//Now, clicking on "Checkout" button:
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		//Now, on the last page, we have a dropdown. So to handle it, we will use "Actions" class:
		Actions a = new Actions(driver);
		//Now moving to the country dropdown and entering "india" there:
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
		
		//Now, after we have entered "india", list of countires will display and we have to select the country "India":
		//Waiting explicitly until the complete block of suggested list appears:
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		
		//Once the block appears, click on "India" which is the second option from the list:
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click(); // (2) because "India" is second item in the list
		//.ta-item:nth-of-type(2) - above as cssSelector
		//Clicking on "Place Order" button using JavaScript Executor:
		WebElement placeOrder = driver.findElement(By.xpath("//a[@class='btnn action__submit ng-star-inserted']"));
		JavascriptExecutor js= (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", placeOrder);
		
		//After placing the order, we need to validate if "ThANK YOU FOR THE ORDER" is displayed or not:
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
		//Closing the browser:
		driver.close();
		
	}

}
