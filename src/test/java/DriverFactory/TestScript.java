package DriverFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Utilities.ExcelFileUtil;
import applicationLayer.CustomerPage;
import applicationLayer.LoginPage;
import applicationLayer.Logout;
import applicationLayer.SupplierPage;

public class TestScript {
WebDriver driver;
Properties p;
FileInputStream fi;
String inputpath="C:\\Live Selenium Project\\ERP_Maven\\TestInput\\TestData.xlsx";
String outputpath="C:\\Live Selenium Project\\ERP_Maven\\TestOutPut\\datadriven.xlsx";
@BeforeTest
public void setUp() throws Throwable {
	p= new Properties();
	fi= new FileInputStream("C:\\Live Selenium Project\\ERP_Maven\\PropertyFile\\Project.properties");
	p.load(fi);
	
	if(p.getProperty("Browser").equalsIgnoreCase("chrome")){
		System.out.println("Executing on Chrome");
		System.setProperty("webdriver.chrome.driver", "C:\\Live Selenium Project\\ERP_Maven\\CommonDriver\\chromedriver.exe");
		driver= new ChromeDriver();
		driver.get(p.getProperty("Url"));
		driver.manage().window().maximize();
		LoginPage login = PageFactory.initElements(driver,LoginPage.class);
		String results = login.verifyLogin("admin", "master");
		Reporter.log(results,true);
		}
		else if(p.getProperty("Browser").equalsIgnoreCase("firefox")){
			System.out.println("Executing on Firefox");
			System.setProperty("webdriver.gecko.driver", "C:\\Live Selenium Project\\ERP_Maven\\CommonDriver\\geckodriver.exe");
			driver= new FirefoxDriver();
			driver.get(p.getProperty("Url"));
			LoginPage login = PageFactory.initElements(driver,LoginPage.class);
			String results = login.verifyLogin("admin", "master");
			Reporter.log(results,true);
			}
		else {
			System.out.println("Browser Value is not matching");
		}
	}
@Test
public void supplierCreation() throws Throwable {
	SupplierPage supplier = PageFactory.initElements(driver, SupplierPage.class);
	//create object for excelfile util class
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	int rc= xl.rowCount("Supplier");
	int cc=xl.cellCount("Supplier");
	Reporter.log(rc + " " + cc,true);
	for(int i=0; i<=rc; i++) {
		String sname= xl.getCelldata("Supplier", i, 0);
		String address=xl.getCelldata("Supplier", i, 1);
		String city=xl.getCelldata("Supplier", i, 2);
		String Country = xl.getCelldata("Supplier", i, 3);
		String cperson=xl.getCelldata("Supplier", i, 4);
		String pnumber = xl.getCelldata("Supplier", i, 5);
		String email = xl.getCelldata("Supplier", i, 6);
		String mnumber = xl.getCelldata("Supplier", i, 7);
		String notes = xl.getCelldata("Supplier", i, 8);
		String results= supplier.verifySupplier(sname, address, city, Country, cperson, pnumber, email, mnumber, notes);
		Reporter.log(results,true);
		xl.setCellData("Supplier", i, 9, results, outputpath);
	}
}
@Test
public void customerCreation() throws Throwable {
	CustomerPage customer = PageFactory.initElements(driver, CustomerPage.class);
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	int rc= xl.rowCount("Customers");
	int cc=xl.cellCount("Customers");
	Reporter.log(rc + " " + cc,true);
	for(int i=0; i<=rc; i++) {
		String custname= xl.getCelldata("Customers", i, 0);
		String custaddress=xl.getCelldata("Customers", i, 1);
		String custcity=xl.getCelldata("Customers", i, 2);
		String custcountry = xl.getCelldata("Customers", i, 3);
		String custcperson=xl.getCelldata("Customers", i, 4);
		String custpnumber = xl.getCelldata("Customers", i, 5);
		String custemail = xl.getCelldata("Customers", i, 6);
		String custmnumber = xl.getCelldata("Customers", i, 7);
		String custnotes = xl.getCelldata("Customers", i, 8);
		String results= customer.verifyCustomer(custname, custaddress, custcity, custcountry, custcperson, custpnumber, custemail, custmnumber, custnotes);
		Reporter.log(results,true);
		xl.setCellData("Customers", i, 9, results, outputpath);
	}
}
@AfterTest
public void teardown() {
	Logout logout=PageFactory.initElements(driver, Logout.class);
	String results=logout.veriyLogout();
	Reporter.log(results,true);
	driver.close();
	
}
	
}


