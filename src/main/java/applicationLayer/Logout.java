package applicationLayer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Logout {
WebDriver driver;
WebDriverWait wait;

public Logout(WebDriver driver) {
	this.driver = driver;
}
@FindBy(id="mi_logout")	
WebElement clicklogout;
@FindBy(name= "btnsubmit")
WebElement loginbtn;
public String veriyLogout() {
	String res = "";
	wait=new WebDriverWait(driver, 20);
	wait.until(ExpectedConditions.elementToBeClickable(clicklogout));
	this.clicklogout.click();
	wait.until(ExpectedConditions.visibilityOf(loginbtn));
	if(this.loginbtn.isDisplayed()) {
		res = "Sucess";
	}
	else {
		res = "Logout Fail";
	}
	return res;
}


}
