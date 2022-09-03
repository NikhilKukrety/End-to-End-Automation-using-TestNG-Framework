package rahulshettyacademy.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage {
	
	WebDriver driver;
	
	/*Below we are initializing a constructor, in which we will be calling the driver from test class.
	 * Constructors always execute first in a class. Syntac: public "class name": 
	 */
	
	public LandingPage(WebDriver driver) //We are catching the value of driver from "StandAloneTest" class to bring bring life to local "driver" of this class 
	{
		//Giving life to local "driver" of this class:
		this.driver = driver;
		
		//Below code says, that use this local driver now to initialize the web elements:
		PageFactory.initElements(driver, this);
	}
	
	//Instead of writing WebElement username = driver.findElem...By.id("username").. etc, do below:
	
	//FAMOUS INTERVIEW QUESTION:
	//Below code works using PageFactory.init... as states in above constructor:
	@FindBy(id="username")
	WebElement username;
	
	@FindBy(id="userPassword")
	WebElement password;
	
	@FindBy(id="login")
	WebElement submit;
	
	
	
}
