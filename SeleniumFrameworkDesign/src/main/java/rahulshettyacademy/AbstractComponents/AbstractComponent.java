package rahulshettyacademy.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import rahulshettyacademy.pageobjects.CartPage;

public class AbstractComponent {
	
	
	WebDriver driver;
	
	//Below constructor will catch the life of the driver from child class (LandingPage). 
	public AbstractComponent(WebDriver driver) {

		this.driver=driver;
		//Below code says, that use this local driver now to initialize the web elements:
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(css="[routerlink*='cart']")
	WebElement cartHeader;

	public void waitForElementToAppear(By findBy)
	{
		//Using Explicit wait, to wait until the products are displayed before iterating through them:
				WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
				wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	
	public void waitForWebElementToAppear(WebElement findBy)
	{
		//Using Explicit wait, to wait until the products are displayed before iterating through them:
				WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
				wait.until(ExpectedConditions.visibilityOf(findBy));
	}
	
	public void waitForElementToDisappear(WebElement ele)
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(ele));
		
	}
	
	//We are defining the clicking on add button in abstract class because "Cart" link is present on every page. So, we can use it any time:
	public CartPage goToCart()
	{
		cartHeader.click();
		CartPage cartPage = new CartPage(driver); //Creating object for the next page "CartPage"
		return cartPage;
	}
	
	
	
	

}
