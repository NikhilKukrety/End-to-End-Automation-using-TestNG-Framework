package rahulshettyacademy.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import rahulshettyacademy.resources.ExtenReporterNG;

public class Listeners extends BaseTest implements ITestListener{ //Listeners help us to manage execution of Tests as per our convenience

	ExtentReports extent = ExtenReporterNG.getReportObject(); //Calling the method "getReportObject", since its return type is "extent" object, storing it in object "extent" here
	ExtentTest test;
	
	//ThreadLocal class helps us to assign unique IDs to objects so that if tests are run in parallel, they do not override each other and perform tests on different methods resulting in incorrect reports generation:
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal();
	
	@Override
	public void onTestStart(ITestResult result) { //This method will run first, before every test ececution. So we are saying that for each test, generate extent report
		test = extent.createTest(result.getMethod().getMethodName()); //Get the method and method name which is being executed
		extentTest.set(test); //This will assign unique IDs to the object "test" 
		ITestListener.super.onTestStart(result);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.get().log(Status.PASS, "Test Passed!"); //Print this when status is passed and we are using "extentTest" object to prevent object overriding
		ITestListener.super.onTestSuccess(result);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		//test.log(Status.FAIL, "Test Failed!"); //Print this when status is failed
		extentTest.get().fail(result.getThrowable());//This will print the error log and we are using "extentTest" object to prevent object overriding
		ITestListener.super.onTestSuccess(result);
		
		//Giving life to driver here:
		try {
		driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
				.get(result.getInstance());
		}	
			catch(Exception el) { //Basically, it means if the above single line of code under "try" fails because of any exception, print the error message
				el.printStackTrace();
		}
			
		
		String filePath = null;
		try {
			filePath = getScreenshot(result.getMethod().getMethodName(),driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
		
		//Now if we want to take the screenshot:
		
		
		ITestListener.super.onTestFailure(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush(); //Now, execution is finished and report will be generated
		ITestListener.super.onFinish(context);
	}
	
	

}
