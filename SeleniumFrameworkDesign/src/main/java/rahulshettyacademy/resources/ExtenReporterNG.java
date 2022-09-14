package rahulshettyacademy.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtenReporterNG {
	
	public static ExtentReports getReportObject() //Static means we can use this method everywhere without creating its object
	{
		String path = System.getProperty("user.dir")+"\\Reports\\index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");

		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Automation Tester", "Nikhil Kukrety");
		return extent; //We are returning the object so that we can use this method in different places
	}

}
