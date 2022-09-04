package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent{
	
	WebDriver driver;
	
	/*Below we are initializing a constructor, in which we will be calling the driver from test class.
	 * Constructors always execute first in a class. Syntax: public "class name": 
	 */
	
	public ProductCatalogue(WebDriver driver) //We are catching the value of driver from "StandAloneTest" class to bring bring life to local "driver" of this class 
	{
		super(driver);
		//Giving life to local "driver" of this class:
		this.driver = driver;
		
		//Below code says, that use this local driver now to initialize the web elements:
		PageFactory.initElements(driver, this);
	}

	//List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
	
	@FindBy(css=".mb-3")
	List<WebElement> products; //"List" because we are finding multiple elements
	
	@FindBy(css=".ng-animating")
	WebElement spinner; //Storing the WebElement spinner with above cssSelector
	
	By productsBy = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".card-body button:last-of-type"); //Storing the "Add to Cart" WebElement in "addToCart" variable so we can use it anywhere
	By toastMessage = By.cssSelector("#toast-container"); //Storing the toast message WebElement so we can use it to wait for it	
	
	
	//Below is an action method, which will get the products list
	public List<WebElement> getProductsList() 
	{
		waitForElementToAppear(productsBy); //We are waiting for products to appear on page, after that we are calling WebElement "products"
		return products;
	}

	//Action Method - To get products by name:
	public WebElement getProductByName(String productName)
	{
		WebElement prod = getProductsList().stream().filter(s->
		s.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		return prod;
	}
	
	//Action Method - To add to cart:
	public void addProductToCart(String productName) //Fetching "ZARA COAT 3" from main class
	{	
		WebElement prod = getProductByName(productName); //Storing "ZARA COAT 3" in prod
		prod.findElement(addToCart).click(); //Within "prod"'s scope, we are finding the add to cart button and clicking it
		waitForElementToAppear(toastMessage); //Waiting for toast message to appear
		waitForElementToDisappear(spinner); //Waiting for spinner to disappear
	
	
	}
	
	
	
	
	
}
