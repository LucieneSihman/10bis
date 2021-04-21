package pages;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Career extends Base {

	public Career(WebDriver driver) {
		super(driver);
	}

	// start login
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