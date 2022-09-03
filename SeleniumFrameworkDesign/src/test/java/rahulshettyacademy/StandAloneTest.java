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

public class StandAloneTest {

	public static void main(String[] args) {
		//Quick note:
		//Parent-child traverse - .classname (of parent) tagname (of child):
		//If classname of parent is totalRow, and tagname of child is button, then cssSelector becomes - .totalRow button 
		
		
		String productName = "ZARA COAT 3";
		
		
		//We dont need selenium webdriver in local, instead we can download webdriver manager from pom.xml file.
		//Download - WebDriverManager from maven repository and add the dependency.
		
		//So, instead of giving System.setProperty.. just give below:
		WebDriverManager.chromedriver().setup();
		
		//Creating object of chrome driver:
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //Max timeout
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client");
		
		/*We have created a class called "LandingPage" in main folder for Page Object Concept.
		 * So defining object of "LandingPage" class, and then passing the driver which has life to that class.
		 * And in the "LandingPage" class, we are retrieving the value of this driver and storing it to local driver there:
		 */
		LandingPage landingpage = new LandingPage(driver);
		
		driver.findElement(By.id("userEmail")).sendKeys("dummyemail@rsa.com");
		driver.findElement(By.id("userPassword")).sendKeys("Dummypassword@123");
		driver.findElement(By.id("login")).click();
		
		//Using Explicit wait, to wait until the products are displayed before iterating through them:
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		/*Now, after clicking the login button, we need to grab the complete list of items displaying and iterate through each of them
		*to check which one we need to pick.
		*/
		
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		//Now, instead of using for loop, we will be using Java Streams to iterate over each product and add "ZARA COAT 3" product. 
		//Basically, "ZARA COAT 3" is not written on the ".mb-3" path. so we have to find where it is written, so its written deeper on tag "b":
		//And, we are iterating over each product to find the required text (zara coat 3):
		WebElement product = products.stream().filter(s->
		s.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		//Above basically means, if the element is found, then return thay element only which we found first, or else if element is not found, then return null.
		
		//Now, "product" has stored the "ZARA COAT 3" product in it, and by using this, we will click on its "Add to Cart" button:
		//Using parent-child traverse, we will reach to "Add to Cart" button:
		product.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		/*Now, after we have added the item to cart, we will see a loader (spinner) for few seconds, and we have to wait until the
		 * spinner desappears and until the toast message appears that product was added successfully.
		 * So we have to capture that toast message before moving to cart
		 */
		
		//Waiting till 5 seconds till the toast message appears:
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		
		//Waiting for spinner to become invisible:
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
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
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		
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
