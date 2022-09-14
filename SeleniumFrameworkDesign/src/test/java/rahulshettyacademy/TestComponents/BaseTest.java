package rahulshettyacademy.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;

public class BaseTest {
	
	//Defining globally the WebDriver:
	public WebDriver driver;
	public LandingPage landingPage; //"public" means all classes can access this object "landingpage"
	
	
	public WebDriver initializeDriver() throws IOException
	{
		//Properties class"
		Properties prop = new Properties(); //We have created a "properties" file named "GlobalData", where we will be initializing all our global properties (key-value pair).
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src//main//java//rahulshettyacademy//resources//GlobalData.properties"); //System.getProperty("user.dir") - This will automatically get us the path of our project, because we do not want to hard code our personal project path.
		prop.load(fis); //To load the global properties, we need to use "prop.load();" and load the file (GlobalData.properties) file and will extract all key value pair from it to here. So, this method is expecting argument as input stream, so above line will convert the file to InputStream Object.
		//Also, it mean the properties object is reading the GlobalData.peroperties file, and above method "load" is loading it in form of FileInputStream
		
		String browserName = prop.getProperty("browser"); //This will return "chrome" value from "GlobalData.properties" file and store it in string "browserName"
		
		//Now, if browserName is "chrome", then invoke the chrome browser:
		if(browserName.equalsIgnoreCase("chrome"))
		{
		/*We don't need Selenium WebDriver in local, instead we can download webDriver manager from pom.xml file.
		*Download - WebDriverManager from Maven repository and add the dependency.
		*So, instead of giving System.setProperty.. just give below: */
		WebDriverManager.chromedriver().setup();
		//Creating object of chrome driver:
		driver = new ChromeDriver();
		}
		
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			//Run Firefox browser
		}
		
		else if(browserName.equalsIgnoreCase("edge"))
		{
			//Run Edge browser
		}
		
		//After running a particular browser, do below:
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //Max timeout
		driver.manage().window().maximize();
		return driver;
	}
	
	
	//Writing this DataReader code here because SubmitOrder test is inheriting data from this baseclass as well:
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
		//Now, below line of code will read the JSON file, and convert it into a string. So we are storing the obtained string in a varibale "jsonContent"
		String jsonContent = FileUtils.readFileToString(new File(filePath), 
				StandardCharsets.UTF_8); //Basically, this is an encoding method, which tells in which format we want to convert the string
		
		
		//Now, we need to convert the above string to HashMap - To do this, we need a dependency called "JACKSON DATABIND"
		//There is a method called "readValue" in "ObjectMapper" class, which reads the JSON file and convert it into string. As we have two data sets each converted into a hashmap,a list of two hashmaps are created, hence we are storing them in a string
		
		ObjectMapper mapper = new ObjectMapper();
		//Also, readValue method takes two arguments, first one is the JSON file, and other one is the way how we want to convert the file:
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){});
		return data;
	}
	
	//Take screenshot in case of failure:
			public String getScreenshot(String testCaseName, WebDriver driver) throws IOException
			{
				//Creating object "ts" of class "TakesScreenshot":
				TakesScreenshot ts = (TakesScreenshot)driver;
				//Taking the screenshot as oytput type as "File" and storing it in File type "source":
				File source = ts.getScreenshotAs(OutputType.FILE);
				//Giving file path name:
				File file = new File(System.getProperty("user.dir")+"//Reports//"+testCaseName+".png");
				//Copies the file in the destination:
				FileUtils.copyFile(source, file);
				return System.getProperty("user.dir")+"//Reports//"+testCaseName+".png"; //So, it is returning the path where the file is stored
				
			}
	
	
	@BeforeMethod(alwaysRun=true) //This method will run first whenever this class is called.  Writing "always..." because if we use groups in any class tied to this class, this class also treats all the methods here as groups.
	public LandingPage launchApplication() throws IOException
	{
		driver = initializeDriver(); //which ever driver is returned, chrome, firefox or edge, store it in a variable "driver"
		//Using this "driver", go to the landing page:
		/*We have created a class called "LandingPage" in main folder for Page Object Concept.
		* So defining object of "LandingPage" class, and then passing the driver which has life to that class.
		* And in the "LandingPage" class, we are retrieving the value of this driver and storing it to local driver there: */
		landingPage = new LandingPage(driver);
		landingPage.goTo(); //Navigating to application URL.
		return landingPage; //returning the object to use (or catch) it in the test (SubmitOrder) class.
	}
	
	
	@AfterMethod() //This method will run at the end
	public void tearDown()
	{
		driver.close();
	}

}
