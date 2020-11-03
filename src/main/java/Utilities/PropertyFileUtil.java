package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class PropertyFileUtil {
	
	public static String getValueForKey(String Key) throws Throwable {
		Properties configProperties = new Properties();
		//FileInputStream fi = new FileInputStream("");
		//configProperties.load(fi);
		configProperties.load(new FileInputStream("C:\\Live Selenium Project\\ERP_Maven\\PropertyFile\\Environment.properties"));
		return configProperties.getProperty(Key);
		
			}
	

}
