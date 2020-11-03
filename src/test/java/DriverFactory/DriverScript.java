package DriverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunLibrary.FunctionLibrary;
import Utilities.ExcelFileUtil;

public class DriverScript {
	WebDriver driver;
	ExtentReports reports;
	ExtentTest test;
	String inputpath= "C:\\Live Selenium Project\\ERP_Maven\\TestInput\\InputSheet.xlsx";
	String outputpath= "C:\\Live Selenium Project\\ERP_Maven\\TestOutPut\\hybridframework.xlsx";
	
	public void startTest() throws Throwable {
		
	//creating excel object to access excel utilities
		
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		
		//iterate all rows in Mastertestcases sheet
		for(int i=1;i<=xl.rowCount("MasterTestCases"); i++) {
			String moduleStatus = "";
			if(xl.getCelldata("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
			{
				//store Testcase into TCModule
				String TCModule = xl.getCelldata("MasterTestCases", i, 1);
				//generate report under project
				reports = new ExtentReports("./ExtentReports/"+TCModule+" "+FunctionLibrary.generateDate()+".html");
				//iterate all rows in TCModule
				for(int j = 1;j<=xl.rowCount(TCModule);j++) {
					//testcase executes here
					test=reports.startTest(TCModule);
					//read all cells from TCModule
					String Description = xl.getCelldata(TCModule, j, 0);
					String Function_Name = xl.getCelldata(TCModule, j, 1);
					String Locator_Type = xl.getCelldata(TCModule, j, 2);
					String Locator_value= xl.getCelldata(TCModule, j, 3);
					String Test_Data = xl.getCelldata(TCModule, j, 4);
					//Calling functions
					try {
						if(Function_Name.equalsIgnoreCase("startBrowser"))
						{
							driver = FunctionLibrary.startBrowser();
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("openApplication")) {
							FunctionLibrary.openApplication(driver);
							test.log(LogStatus.INFO, Description);
							
						}
						else if(Function_Name.equalsIgnoreCase("waitForElement")){
							FunctionLibrary.waitForElement(driver, Locator_Type,Locator_value,Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("typeAction")) {
							FunctionLibrary.typeAction(driver, Locator_Type, Locator_value, Test_Data);
							test.log(LogStatus.INFO, Description);
							
						}
						else if(Function_Name.equalsIgnoreCase("clickAction")) {
							FunctionLibrary.clickAction(driver, Locator_Type, Locator_value);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("closeBrowser")) {
							FunctionLibrary.closeBrowser(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("captureData")) {
							FunctionLibrary.captureData(driver, Locator_Type, Locator_value);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("tableValidation")) {
							FunctionLibrary.tableValidation(driver, Test_Data);
						}
						else if(Function_Name.equalsIgnoreCase("stockCategories")) {
							FunctionLibrary.stockCategories(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("stockValidation")) {
							FunctionLibrary.stockValidation(driver, Test_Data);
							test.log(LogStatus.INFO, Description);
				
						}
						//write as pass into status cell in TCModule
						xl.setCellData(TCModule, j, 5, "Pass", outputpath);
						test.log(LogStatus.PASS, Description);
						xl.setCellData("MasterTestCases", i, 3, "Pass", outputpath);
						moduleStatus="true";
						
					}catch(Exception t) {
											
						//write as fail into status cell in TCModule
						xl.setCellData(TCModule, j, 5, "Fail", outputpath);
						test.log(LogStatus.FAIL, Description);
						//write as fail into MasterTestCases sheet
						xl.setCellData("MasterTestCases", i, 3, "Fail", outputpath);
						//take screenshot and store in one variable
						File screen=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						//copy screenshot into local file system
						FileUtils.copyFile(screen, new File("./Screens/"+Description+FunctionLibrary.generateDate()+".png"));
						System.out.println(t.getMessage());
							
							reports.flush();
							reports.endTest(test);
						}
						
					}
									
			}
			else {
				//write as blocked into MasterTestCases sheet
				xl.setCellData("MasterTestCases", i, 3, "Blocked", outputpath);
			}
		}
	}

}
