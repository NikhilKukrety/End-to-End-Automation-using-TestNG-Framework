package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class OrderPage extends AbstractComponent{
	
	WebDriver driver;
	
	
	public OrderPage(WebDriver driver) //We are catching the value of driver from "StandAloneTest" class to bring bring life to local "driver" of this class 
	{
		
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//Below we are grabbing all the elements (product names) and storing them in WebElement "productNames". Then we will iterate them and see if "ZARA COAT 3" is present or not
	@FindBy(css="tr td:nth-child(3)")
	List<WebElement> productNames;

	//Verify if product added is displayed:
	public boolean VerifyOrderDisplay(String productName)
	{
		Boolean match = productNames.stream().anyMatch(s->s.getText().equalsIgnoreCase(productName));
		return match; //Will return true if item matches
	}
}
