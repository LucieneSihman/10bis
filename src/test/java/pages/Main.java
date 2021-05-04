package pages;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Main extends Base {

	public Main(WebDriver driver) {
		super(driver);
	}

	// start registration
	public boolean register() throws InterruptedException {

		click(By.xpath("//button[text()='הרשמה']"));
		if (isExist(By.id("modal-title")))
			return true;
		else
			return false;

	}
	
	// start login
		public boolean login() throws InterruptedException {
			
			//Click connection
			click(By.xpath("//button[text()='התחברות']"));
			
			if (isExist(By.xpath("//*[@id=\"modal-title\"][text()='איזה כיף שחזרת אלינו!']")))
				return true;
			else
				return false;

		}

		// Click on Career link
				public boolean verifyCarrerWindow() throws InterruptedException {

					String baseHandle = driver.getWindowHandle();
					
					 
					//Click in the career option
					click(By.xpath("//div[text()='דרושים']"));
					
					Thread.sleep(3000);
					
					//switch to career window
					Thread.sleep(1000);
					Set<String> handels = driver.getWindowHandles();

					for (String h : handels) {
						if (!h.equals(baseHandle))
							driver.switchTo().window(h);
					}
					

					String WindowUrl ="https://careers.takeaway.com/global/en";
					
					if (WindowUrl.equals(driver.getCurrentUrl()))
						return true;
					else
						return false;

					
					

		}	

}