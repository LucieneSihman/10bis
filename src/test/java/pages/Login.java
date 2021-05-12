package pages;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class Login extends Base {

	public Login(WebDriver driver) {
		super(driver);
	}
	
	
	// start login
	public boolean doLoginFacebook(String email, String password, String user, String adress) throws InterruptedException {

		String baseHandle = driver.getWindowHandle();
		
		//Click facebook image
		click(By.xpath("//img[@type='facebook']"));
		
		Thread.sleep(2000);
		
		//switch to Facebook login window
		//Thread.sleep(1000);
		Set<String> handels = driver.getWindowHandles();

		for (String h : handels) {
			if (!h.equals(baseHandle))
				driver.switchTo().window(h);
		}

		//type user/password
		driver.findElement(By.id("email")).sendKeys(email);
		Thread.sleep(1000);

		driver.findElement(By.id("pass")).sendKeys(password);
		Thread.sleep(2000);
		click(By.name("login"));

		Thread.sleep(2000);
		driver.switchTo().window(baseHandle);

		//click(By.xpath("//img[@type='facebook']"));
		Thread.sleep(3000);
		
		
		click(By.id("homePage_SelectAddress"));
		driver.findElement(By.id("homePage_SelectAddress")).sendKeys(adress);
		driver.findElement(By.id("homePage_SelectAddress")).sendKeys(Keys.ENTER);

		
		click(By.xpath("//*[@id=\"root\"]//section[1]//button"));
			
		Thread.sleep(1000);
			

		String personalMsg = getText(By.cssSelector(".styled__PrimaryText-zzhidz-4.cfoTPh"));
		
		if (personalMsg.contains(user))
			return true;
		else
			return false;
		
		

	}
	
	public boolean verifyErrMsg() throws InterruptedException {
		click(By.xpath("//button[@data-test='login-submit']"));
		
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ENTER).build().perform();
		
		
		
		Thread.sleep(1000);
		
		String err = driver.findElement(By.xpath("//div[@role='alert']")).getText();
		
		if (err.equals("שדה חובה"))
			return true;
		else
			return false;
	}

}
