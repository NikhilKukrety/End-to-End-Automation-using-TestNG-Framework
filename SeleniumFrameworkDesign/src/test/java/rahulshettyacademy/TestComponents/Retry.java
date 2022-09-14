package rahulshettyacademy.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer { //This means the class "Retry" has to implement methods exposed by interface "IRetryAnalyzer"

	//This class defines if any test fails because of environment issues or any reason, which is not valid, it will re-run the test
	//It will come to the below method and will ask if the method/test needs to be re-run again or not, and if yes, then how many times
	
	int count = 0;
	int maxTry = 1; //No of times we need to run the test
	
	@Override
	public boolean retry(ITestResult result) {

		if(count<maxTry) //If the re-run has not happened a single time,
		{
			count++; //then increase the count by 1,
			return true; //and re-run the method. Basically it will be re-run when this methods returns true
		}
		
		return false;
	}

}
