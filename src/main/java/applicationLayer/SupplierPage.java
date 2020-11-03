package applicationLayer;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
public class SupplierPage {
WebDriver driver;
WebDriverWait wait;
public SupplierPage(WebDriver driver)
{
	this.driver=driver;
}
//maintain Repository
@FindBy(xpath="(//a[contains(text(),'Suppliers')])[2]")
WebElement clicksupplier;
@FindBy(xpath="/html/body/div[2]/div[3]/div/div/div[3]/div[1]/div[1]/div[1]/div/a/span")
WebElement clickAddicon;
@FindBy(name="x_Supplier_Number")
WebElement suppliernum;
@FindBy(name="x_Supplier_Name")
WebElement entersname;
@FindBy(name="x_Address")
WebElement enteraddress;
@FindBy(name="x_City")
WebElement entercity;
@FindBy(name="x_Country")
WebElement entercountry;
@FindBy(name="x_Contact_Person")
WebElement entercperson;
@FindBy(name="x_Phone_Number")
WebElement enterpnumber;
@FindBy(name="x__Email")
WebElement enteremail;
@FindBy(name="x_Mobile_Number")
WebElement entermnumber;
@FindBy(name="x_Notes")
WebElement enternotes;
@FindBy(name="btnAction")
WebElement clickadd;
@FindBy(xpath="//button[text()='OK!']")
WebElement clickOk;
@FindBy(xpath="(//button[text()='OK'])[6]")
WebElement clickOkbtn;
@FindBy(xpath="//body/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/button[1]")
WebElement clickspanel;
@FindBy(name="psearch")
WebElement Enterserach;
@FindBy(name="btnsubmit")
WebElement clicksearch;
public String verifySupplier(String sname,String address,String city,String country,
	String cperson,String pnumber,String email,String mnumber,String notes)
{
	String res="";
	wait= new WebDriverWait(driver, 20);
	wait.until(ExpectedConditions.elementToBeClickable(clicksupplier));
	this.clicksupplier.click();
	wait.until(ExpectedConditions.elementToBeClickable(clickAddicon));
	this.clickAddicon.click();
	wait.until(ExpectedConditions.visibilityOf(suppliernum));
	//capture supplier number
	String expectedsnumber=this.suppliernum.getAttribute("value");
	this.entersname.sendKeys(sname);
	this.enteraddress.sendKeys(address);
	this.entercity.sendKeys(city);
	this.entercountry.sendKeys(country);
	this.entercperson.sendKeys(cperson);
	this.enterpnumber.sendKeys(pnumber);
	this.enteremail.sendKeys(email);
	this.entermnumber.sendKeys(mnumber);
	this.enternotes.sendKeys(notes);
	this.clickadd.sendKeys(Keys.ENTER);
	wait.until(ExpectedConditions.elementToBeClickable(clickOk));
	this.clickOk.click();
	wait.until(ExpectedConditions.elementToBeClickable(clickOkbtn));
	this.clickOkbtn.click();
	if(!this.Enterserach.isDisplayed())
		//click on search panel button
	this.clickspanel.click();
	wait.until(ExpectedConditions.visibilityOf(Enterserach));
	this.Enterserach.clear();
	this.Enterserach.sendKeys(expectedsnumber);
	this.clicksearch.click();
	String actualsnumber=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr/td[6]/div/span/span")).getText();
	if(actualsnumber.equals(expectedsnumber))
	{
		res="Pass";
		Reporter.log(expectedsnumber+"  "+actualsnumber,true);
	}
	else
	{
		res="Fail";
		Reporter.log(expectedsnumber+"  "+actualsnumber,true);
	}
return res;
}
}
