package take_home_webdriver_test.Webdriver_Tests.pages;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Utilities {	

	FileReader reader = null;
	public static String browser;
	public static String url;
	
	public Utilities() {
		String dir = System.getProperty("user.dir");
		try {
			reader = new FileReader(dir+"\\src\\test\\resources\\application.properties");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Properties p=new Properties();  
	    try {
			p.load(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			browser=p.getProperty("browser"); 
			url=p.getProperty("url"); 
			
	}
	
	
}
