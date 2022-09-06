package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent{
	
	WebDriver driver;

	public CartPage(WebDriver driver) {
		
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//For getting products list:
	@FindBy(css=".cartSection h3")
	List<WebElement> cartProducts;
	
	//For "Checkout" button:
	@FindBy(css=".totalRow button")
	WebElement checkOut;
	
	public boolean VerifyProductDisplay(String productName)
	{
		Boolean match = cartProducts.stream().anyMatch(s->s.getText().equalsIgnoreCase(productName));
		return match; //Will return true if item matches
	}
	
	public CheckoutPage checkOut()
	{
		checkOut.click();
		return new CheckoutPage(driver); //Creating object here itself for next page "Checkout page". Basically, instead of creating so many objects separately, continue creating objects like this on the first page itself for the second page
	}
	
	
	
	
	

}
