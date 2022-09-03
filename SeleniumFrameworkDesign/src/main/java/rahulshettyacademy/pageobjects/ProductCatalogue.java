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
	
	By productsBy = By.cssSelector(".mb-3");
	
	//Below is an action method, which will get the products list
	public List<WebElement> getProductsList()
	{
		waitForElementToAppear(productsBy); //We are waiting for products to appear on page, after that we are calling webelement "products"
		return products;
	}

	
	
	
	
	
}
