package applicationLayer;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class CustomerPage {
	WebDriver driver;
	WebDriverWait wait;
	public CustomerPage(WebDriver driver)
	{
		this.driver=driver;
	}
@FindBy(xpath="(//a[text()='Customers'])[2]")
WebElement clickcustomers;
@FindBy(xpath="/html/body/div[2]/div[3]/div/div/div[3]/div[1]/div[1]/div[1]/div/a/span")
WebElement clickaddicon;
@FindBy(name="x_Customer_Number")
WebElement custnumber;
@FindBy(name="x_Customer_Name")
WebElement custname;
@FindBy(name = "x_Address")
WebElement custaddress;
@FindBy(name="x_City")
WebElement custcity;
@FindBy(name="x_Country")
WebElement custcountry;
@FindBy(name = "x_Contact_Person")
WebElement custcperson;
@FindBy(name="x_Phone_Number")
WebElement custpnumber;
@FindBy(name="x__Email")
WebElement custemail;
@FindBy(name="x_Mobile_Number")
WebElement custmnumber;
@FindBy(name="x_Notes")
WebElement custnotes;
@FindBy(name="btnAction")
WebElement custaddbtn;
@FindBy(xpath="//button[text()='OK!']")
WebElement custok;
@FindBy(xpath="(//button[text()='OK'])[6]")
WebElement custokbtn;
@FindBy(xpath="(//button[@class='btn btn-default ewSearchToggle'])")
WebElement custclickspanel;
@FindBy(name="psearch")
WebElement custsearch;
@FindBy(name="btnsubmit")
WebElement custsearchbtn;
@FindBy(xpath="//table[@id=\"tbl_a_customerslist\"]/tbody/tr/td[5]/div/span/span")
WebElement table;

public String verifyCustomer(String custname,String custaddress,String custcity,String custcountry,String custcperson,
		String custpnumber,String custemail,String custmnumber,String custnotes) {
	String res="";
	wait= new WebDriverWait(driver, 20);
	wait.until(ExpectedConditions.elementToBeClickable(clickcustomers));
	this.clickcustomers.click();
	wait.until(ExpectedConditions.elementToBeClickable(clickaddicon));
	this.clickaddicon.click();
	wait.until(ExpectedConditions.visibilityOf(custnumber));
	//capture supplier number
	String expectedcnumber=this.custnumber.getAttribute("value");
	this.custname.sendKeys(custname);
	this.custaddress.sendKeys(custaddress);
	this.custcity.sendKeys(custcity);
	this.custcountry.sendKeys(custcountry);
	this.custcperson.sendKeys(custcperson);
	this.custpnumber.sendKeys(custpnumber);
	this.custemail.sendKeys(custemail);
	this.custmnumber.sendKeys(custmnumber);
	this.custnotes.sendKeys(custnotes);
	this.custaddbtn.sendKeys(Keys.ENTER);
	wait.until(ExpectedConditions.elementToBeClickable(custok));
	this.custok.click();
	wait.until(ExpectedConditions.elementToBeClickable(custokbtn));
	this.custokbtn.click();
	if(!this.custsearch.isDisplayed())
		//click on search panel button
	this.custclickspanel.click();
	wait.until(ExpectedConditions.visibilityOf(custsearch));
	this.custsearch.clear();
	this.custsearch.sendKeys(expectedcnumber);
	this.custsearchbtn.click();
	String actualcnumber=driver.findElement(By.xpath("//table[@id='tbl_a_customerslist']/tbody/tr/td[5]/div/span/span")).getText();
	if(actualcnumber.equals(expectedcnumber))
	{
		res="Pass";
		Reporter.log(expectedcnumber+"  "+actualcnumber,true);
	}
	else
	{
		res="Fail";
		Reporter.log(expectedcnumber+"  "+actualcnumber,true);
	}
return res;
}


}





