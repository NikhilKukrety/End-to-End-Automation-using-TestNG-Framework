package rahulshettyacademy.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractComponent {
	
	
	WebDriver driver;
	
	//Below constructor will catch the life of the driver from child class (LandingPage). 
	public AbstractComponent(WebDriver driver) {

		this.driver=driver;
		
	}

	public void waitForElementToAppear(By findBy)
	{
		//Using Explicit wait, to wait until the products are displayed before iterating through them:
				WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
				wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	public void waitForElementToDisappear(WebElement ele)
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(ele));
		
	}
	
	
	
	

}
