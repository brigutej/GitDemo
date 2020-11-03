package CommonFunLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import Utilities.PropertyFileUtil;

public class FunctionLibrary {

	public static WebDriver driver;
	//method for launching url
	public static WebDriver startBrowser() throws Throwable {
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome")) 
		{
			System.out.println("Executing on Chrome Browser");
			System.setProperty("webdriver.chrome.driver", "C:\\Live Selenium Project\\ERP_Maven\\CommonDriver\\chromedriver.exe");
			driver= new ChromeDriver();
		}
		else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox")) {
			System.out.println("Executing on Firefox Browser");
			System.setProperty("webdriver.gecko.driver", "C:\\Live Selenium Project\\ERP_Maven\\CommonDriver\\geckodriver.exe");
			driver= new ChromeDriver();
	}
		else {
			System.out.println("Browser is not matching");
			
		}
		return driver;
	}
	//method for launching url
	
	public static void openApplication(WebDriver driver) throws Throwable {
		driver.get(PropertyFileUtil.getValueForKey("Url"));
		driver.manage().window().maximize();
		System.out.println("Launching the url");
	}
	
	public static void waitForElement(WebDriver driver,String locatortype, String locatorvalue, String waittime) {
		
		WebDriverWait mywait = new WebDriverWait(driver, Integer.parseInt(waittime));
		
		if(locatortype.equalsIgnoreCase("name")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
				
			}
		else if(locatortype.equalsIgnoreCase("id")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
					}
		else if(locatortype.equalsIgnoreCase("xpath")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
		}
		else {
			System.out.println("Unable to execute wait for element method");
		}
			}
	//method for type action
	public static void typeAction(WebDriver driver,String locatortype,String locatorvalue,String testdata) {
		
		System.out.println("Executing the typAction method");
		if(locatortype.equalsIgnoreCase("name")) {
			driver.findElement(By.name(locatorvalue)).clear();
			driver.findElement(By.name(locatorvalue)).sendKeys(testdata);
			
		}
		else if(locatortype.equalsIgnoreCase("id")) {
			driver.findElement(By.id(locatorvalue)).clear();
			driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
			
		}
		else if(locatortype.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(locatorvalue)).clear();
			driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);
		}
		else {
			System.out.println("Unable to execute typAction method");
		}
			
	}
	//method for click action
	
	public static void clickAction(WebDriver driver,String locatortype, String locatorvalue) {
		Reporter.log("Executing clickAction method");
		if(locatortype.equalsIgnoreCase("name")) {
			driver.findElement(By.name(locatorvalue)).click();
		}
	
		else if(locatortype.equalsIgnoreCase("id")) {
				driver.findElement(By.id(locatorvalue)).click();
			
			}
		else if(locatortype.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(locatorvalue)).click();
		
		}
		else {
			Reporter.log("Unable to execute clickAction method");
		}
	}
	
	// method for closeBrowser
	
	public static void closeBrowser(WebDriver driver) {
		driver.close();
	}
	// method for dategenenrate
	public static String generateDate() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY_MM_dd_ss");
		return sdf.format(d);
		
	}
	//method to capture supplier number into notepad
	public static void captureData(WebDriver driver,String locatortype,String locatorvalue) throws Throwable {
	String snumber = "";
	
		if(locatortype.equalsIgnoreCase("id")) {
			
			snumber = driver.findElement(By.id(locatorvalue)).getAttribute("value");
			
		}
		else if(locatortype.equalsIgnoreCase("name")) {
			snumber = driver.findElement(By.id(locatorvalue)).getAttribute("value");
		}
		else if(locatortype.equalsIgnoreCase("xpath")) {
			snumber = driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
		}
		//write snumber in notepad
		
		File f = new File("C:\\Live Selenium Project\\ERP_Maven\\CaptureData\\suppliernum.txt");
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(snumber);
		bw.flush();
	}
	
	// method for supplier table validation
	public static void tableValidation(WebDriver driver, String testdata) throws Throwable {
		FileReader fr = new FileReader("C:\\Live Selenium Project\\ERP_Maven\\CaptureData\\suppliernum.txt");
		BufferedReader br = new BufferedReader(fr);
		String expected= br.readLine();
		//convert testdata into integer
		int column = Integer.parseInt(testdata);
		
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchtextbox"))).isDisplayed()) {
			//click on search panel
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchpanel"))).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchtextbox"))).clear();
			Thread.sleep(5000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchtextbox"))).sendKeys(expected);
			Thread.sleep(5000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchbutton"))).click();
			Thread.sleep(5000);
			WebElement table= driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("stable")));
			//count the number of rows in a table
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			for(int i = 1; i<rows.size(); i++) {
			String actual=	driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column+"]/div/span/span")).getText();
			Assert.assertEquals(actual,expected);
			Reporter.log(actual+" "+expected, true);
			break;
			}
		}
		
	}
	
	//method for Mouse click
	public static void stockCategories(WebDriver driver) throws Throwable {
	Actions ac = new Actions(driver);
	WebElement stockitem = driver.findElement(By.xpath("(//a[@href='a_stock_itemslist.php?cmd=resetall'])[3]"));
	ac.moveToElement(stockitem).perform();
	Thread.sleep(3000);
	WebElement stockcategory= driver.findElement(By.xpath("(//a[@href='a_stock_categorieslist.php'])[2]"));
	ac.moveToElement(stockcategory).click().perform();
	Thread.sleep(3000);
	}
	
	//method for Table validation/stock validation
	
	public static void stockValidation(WebDriver driver, String Exp_data) throws Throwable {
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchtextbox1"))).isDisplayed()) {
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchpanel1"))).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchtextbox1"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchtextbox1"))).sendKeys(Exp_data);
			Thread.sleep(5000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchbutton1"))).click();
			Thread.sleep(5000);
					}
		WebElement table1= driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("sctable")));
		List <WebElement> rows = table1.findElements(By.tagName("tr"));
		for(int i = 1; i<rows.size(); i++) {
			String act_data= driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr["+i+"]/td[4]/div/span/span")).getText();
			System.out.println("No of rows are::"+act_data+"  "+Exp_data);
			Assert.assertEquals(act_data, Exp_data, "Table data is not matching");
			break;
		}
		
		
	}
	//method for customer table validtion
	
	public static void tableValidationCust(WebDriver driver, String testdata) throws Throwable {
		FileReader fr = new FileReader("C:\\Live Selenium Project\\ERP_Maven\\CaptureData\\suppliernum.txt");
		BufferedReader br = new BufferedReader(fr);
		String expected= br.readLine();
		//convert testdata into integer
		int column = Integer.parseInt(testdata);
		
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchtextbox2"))).isDisplayed()) {
			//click on search panel
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchpanel2"))).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchtextbox2"))).clear();
			Thread.sleep(5000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchtextbox2"))).sendKeys(expected);
			Thread.sleep(5000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchbutton2"))).click();
			Thread.sleep(5000);
			WebElement table= driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("ctable")));
			//count the number of rows in a table
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			for(int i = 1; i<rows.size(); i++) {
			String actual=	driver.findElement(By.xpath("//table[@id='tbl_a_customerslist']/tbody/tr[1]/td[5]/div/span/span")).getText();
			Assert.assertEquals(actual,expected);
			Reporter.log(actual+" "+expected, true);
			break;
			}
		}
}
}
