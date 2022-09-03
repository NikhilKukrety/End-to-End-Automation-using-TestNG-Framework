package rahulshettyacademy.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

//By using the "extends", all the methods, variables or fields declared in the "AbstractComponent" class will be automatically applicable to "LandingPage" class also.
//And in the "AbstractComponent" class, we are storing all the reusable components.
public class LandingPage extends AbstractComponent {
	
	WebDriver driver;
	
	/*Below we are initializing a constructor, in which we will be calling the driver from test class.
	 * Constructors always execute first in a class. Syntac: public "class name": 
	 */
	
	public LandingPage(WebDriver driver) //We are catching the value of driver from "StandAloneTest" class to bring bring life to local "driver" of this class 
	{
		
		super(driver); //By doing this, we are sending the life of driver to the abstract class (from child to parent). And by this, we will have to create a constructor in the abstract class to catch its value
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
	WebElement passwordEle;
	
	@FindBy(id="login")
	WebElement submit;
	
	//Defining method to navigate to URL
	public void goTo()
	{
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	//Defining method to login through the first page
	public void loginApplication()
	{
		username.sendKeys("dummyemail@rsa.com");
		passwordEle.sendKeys("Dummypassword@123");
		submit.click();
	}
	
	
	
	
}
