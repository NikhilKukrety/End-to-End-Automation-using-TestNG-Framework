package rahulshettyacademy.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {
	
	public List<HashMap<String, String>> getJsonDataToMap() throws IOException
	{
		//Now, below line of code will read the JSON file, and convert it into a string. So we are storing the obtained string in a varibale "jsonContent"
		String jsonContent = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//src//test//java//rahulshettyacademy//data//PurchaseOrder.json"), 
				StandardCharsets.UTF_8); //Basically, this is an encoding method, which tells in which format we want to convert the string
		
		
		//Now, we need to convert the above string to HashMap - To do this, we need a dependency called "JACKSON DATABIND"
		//There is a method called "readValue" in "ObjectMapper" class, which reads the JSON file and convert it into string. As we have two data sets each converted into a hashmap,a list of two hashmaps are created, hence we are storing them in a string
		
		ObjectMapper mapper = new ObjectMapper();
		//Also, readValue method takes two arguments, first one is the JSON file, and other one is the way how we want to convert the file:
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){});
		return data;
		
	}

}
