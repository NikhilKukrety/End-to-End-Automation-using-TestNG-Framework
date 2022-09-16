package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/cucumber",glue="rahulshettyacademy.stepDefinitions", monochrome=true,
plugin={"html:target/cucumber.html"})

/*Above says, the cucumber feature file is present here (location), glue means we are giving the location where are stepDefinition file
 * is located and stick to the feature file, then "monochrome = true" means generate the report in a readable format,
 * and finally, create the html report in target folder with name "cucumber.html"
 */

public class TestNGTestRunner extends AbstractTestNGCucumberTests{ //This class provides our class to run the cucumber tests. In-built cucumber does not have power to run TestNG cucumber tests on its own, but can run JUnit tests on its own (means without extending to this class)

	
	
}
