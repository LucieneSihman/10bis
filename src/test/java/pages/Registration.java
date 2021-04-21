package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Registration extends Base {

	public Registration(WebDriver driver) {
		super(driver);
	}

	// fill registration fields
	public boolean fill_registration(String fullName, String email, String phone) throws InterruptedException {

		driver.findElement(By.id("fullName")).sendKeys(fullName);
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("cellPhone")).sendKeys(phone);

		driver.findElement(By.id("agreeToTerms")).click();

		// click(By.id("agreeToTerms"));

		if (driver.findElement(By.id("fullName")).getAttribute("value").equals(fullName) && driver.findElement(By.id("email")).getAttribute("value").equals(email) && driver.findElement(By.id("cellPhone")).getAttribute("value").equals(phone))
			return true;
		else
			return false;

	}

	
}